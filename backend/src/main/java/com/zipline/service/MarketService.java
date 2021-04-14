package com.zipline.service;

import com.zipline.Zipline;
import com.zipline.dto.MarketTradeDTO;
import com.zipline.dto.NFTDTO;
import com.zipline.exception.*;
import com.zipline.model.NFT;
import com.zipline.model.User;
import com.zipline.model.Wallet;
import com.zipline.repository.NFTRepository;
import com.zipline.repository.UserRepository;
import com.zipline.repository.WalletRepository;
import com.zipline.smartcontract.Trade;
import com.zipline.smartcontract.Web3Helpers;
import io.reactivex.functions.Consumer;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import springfox.documentation.common.Either;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarketService {
    static HashMap<BigInteger, MarketTradeDTO> allTrades = new HashMap<>();
    static HashMap<BigInteger, MarketTradeDTO> openTrades = new HashMap<>();

    UserRepository userRepository;
    WalletRepository walletRepository;
    NFTService nftService;

    /**
     * Instantiates a new Market service.
     */
    @Autowired
    public MarketService(final UserRepository userRepository, final WalletRepository walletRepository, final NFTService nftService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.nftService = nftService;

        // process the events of market change
        Web3Helpers.getTradesObservable().subscribe(new Consumer<Zipline.TradeStatusChangeEventResponse>() {
            @Override
            public void accept(Zipline.TradeStatusChangeEventResponse tradeStatusChangeEventResponse) throws Exception {
                // replay all events  (doesn't matter from the performance POV, as we need
                // to query them all in any case) and handle the future ones
                BigInteger tradeId = tradeStatusChangeEventResponse.id;
                if (!tradeStatusChangeEventResponse.status) {
                    // we must already remember this trade, as it's transitioned to closed
                    MarketTradeDTO trade = allTrades.get(tradeId);
                    trade.setOpen(false);
                    allTrades.put(tradeId, trade);
                    openTrades.remove(tradeId);
                    return;
                }

                // memorize the new trade
                Pair<String, String> readOnlyWallet = WalletService.getReadOnlyWallet();
                Trade trade = Web3Helpers.getTradeById(readOnlyWallet.getFirst(), readOnlyWallet.getSecond(), tradeId);

                MarketTradeDTO tradeDTO = new MarketTradeDTO();
                tradeDTO.setTradeId(tradeId);
                tradeDTO.setCreatorWalletAddress(trade.creatorAddress.substring(2)); // cut 0x from the address to be consistent
                tradeDTO.setNftId(trade.nftId);
                tradeDTO.setNft(nftService.getNftById(trade.nftId, false));
                tradeDTO.setWeiPrice(trade.price);
                tradeDTO.setOpen(trade.isOpen);

                // try to find user with the wallet from the trade; can be unsuccessful in case it wasn't created
                // from our platform, or the wallet was deleted
                Wallet example = new Wallet();
                example.setAddress(tradeDTO.getCreatorWalletAddress());
                walletRepository.findOne(Example.of(example)).ifPresent(wallet -> tradeDTO.setCreatorUserId(wallet.getOwner().getId()));

                allTrades.put(tradeId, tradeDTO);
                openTrades.put(tradeId, tradeDTO);
            }
        });
    }

    /**
     * Get all currently opened trades
     *
     * @return list of opened trades
     */
    public List<MarketTradeDTO> getAllOpenTrades() {
        return new LinkedList<>(openTrades.values());
    }

    /**
     * Get a trade by its ID
     *
     * @param tradeId of the trade to get
     * @return the trade
     */
    public MarketTradeDTO getTradeById(final BigInteger tradeId) {
        if (!allTrades.containsKey(tradeId)) throw new NoSuchTradeException(tradeId);
        return allTrades.get(tradeId);
    }

    /**
     * Get all trades of the user
     *
     * @param userId to get trades of
     * @return list of user's trades
     */
    public List<MarketTradeDTO> getAllTradesOfUser(final Long userId) {
        return allTrades.values().stream()
                .filter((MarketTradeDTO trade) -> trade.getCreatorUserId() != null && trade.getCreatorUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Open a new trade
     *
     * @param userId on behalf of whom to open the trade
     * @param nftId  to sell
     * @param price  for which to sell
     * @return the opened trade
     */
    public MarketTradeDTO openTrade(final Long userId, final BigInteger nftId, final BigInteger price) throws Exception {
        // find the NFT, which the user wants to sell, in their wallets
        Wallet fromWallet = null;
        for (Wallet wallet : walletRepository.findAllByOwnerId(userId)) {
            Optional<NFTDTO> nftOptional = nftService
                    .getNftsByWalletId(wallet.getWalletId()).stream()
                    .filter((NFTDTO nft_) -> nft_.getNftId().equals(nftId))
                    .findFirst();
            if (!nftOptional.isPresent()) continue;
            fromWallet = wallet;
        }
        if (fromWallet == null) throw new NoSuchNFTException(nftId);

        // open the trade in blockchain
        Either<BigInteger, String> tradeIdEither = Web3Helpers.openTrade(
                fromWallet.getSecretValue(),
                Web3Helpers.getSecretForWallet(fromWallet.getSecretKey(), fromWallet.getSecretSalt()),
                nftId,
                price);
        if (tradeIdEither.getRight().isPresent())
            throw new Exception("Could not open trade: " + tradeIdEither.getRight().get());

        // fill the trade object fields and give it back as a result
        MarketTradeDTO trade = new MarketTradeDTO();
        trade.setTradeId(tradeIdEither.getLeft().get());
        trade.setCreatorUserId(userId);
        trade.setCreatorWalletAddress(fromWallet.getAddress());
        trade.setNftId(nftId);
        trade.setWeiPrice(price);
        trade.setOpen(true);
        return trade;
    }

    /**
     * Execute the trade, buying the NFT
     *
     * @param userId   on behalf of whom to execute trade
     * @param tradeId  of trade to be executed
     * @param walletId of the user to use to execute the trade
     */
    public void executeTrade(final Long userId, final BigInteger tradeId, final Long walletId) throws Exception {
        MarketTradeDTO trade = allTrades.get(tradeId);
        if (trade == null) throw new NoSuchTradeException(tradeId);
        if (!trade.isOpen()) throw new TradeNotOpenException(tradeId);

        Wallet fromWallet = getWallet(userId, walletId);
        BigInteger walletBalance = Web3Helpers.getWalletBalance(fromWallet.getAddress());
        if (walletBalance.compareTo(trade.getWeiPrice()) < 0)
            throw new InsufficientBalanceException(walletBalance, trade.getWeiPrice());

        Either<Boolean, String> result = Web3Helpers.executeTrade(
                fromWallet.getSecretValue(),
                Web3Helpers.getSecretForWallet(fromWallet.getSecretKey(), fromWallet.getSecretSalt()),
                tradeId,
                trade.getWeiPrice());
        if (result.getRight().isPresent())
            throw new Exception("Could not execute trade: " + result.getRight().get());
    }

    /**
     * Cancel a trade
     *
     * @param userId  who posted this trade
     * @param tradeId to cancel
     */
    public void cancelTrade(final Long userId, final BigInteger tradeId) throws Exception {
        MarketTradeDTO trade = allTrades.get(tradeId);
        if (trade == null) throw new NoSuchTradeException(tradeId);
        if (!trade.isOpen()) throw new TradeNotOpenException(tradeId);

        Wallet fromWallet = null;
        for (Wallet wallet : walletRepository.findAllByOwnerId(userId)) {
            if (wallet.getAddress().equals(trade.getCreatorWalletAddress())) fromWallet = wallet;
        }
        if (fromWallet == null)
            throw new Exception("No wallet with trade's wallet address in user wallets; maybe, it was deleted");

        Either<Boolean, String> result = Web3Helpers.cancelTrade(
                fromWallet.getSecretValue(),
                Web3Helpers.getSecretForWallet(fromWallet.getSecretKey(), fromWallet.getSecretSalt()),
                tradeId);
        if (result.getRight().isPresent())
            throw new Exception("Could not cancel trade: " + result.getRight().get());
    }

    private User getUser(final Long userId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) throw new NotFoundException("No user with ID " + userId + " found");
        return userOptional.get();
    }

    private Wallet getWallet(final Long userId, final Long walletId) throws NotFoundException {
        return getUser(userId).getWallets().stream()
                .filter((Wallet w) -> w.getWalletId().equals(walletId)).findFirst()
                .orElseThrow(() -> new NoSuchWalletException(walletId));
    }
}
