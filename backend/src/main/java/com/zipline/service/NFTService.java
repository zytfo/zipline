package com.zipline.service;

import com.zipline.Zipline;
import com.zipline.dto.NFTDTO;
import com.zipline.exception.NoSuchNFTException;
import com.zipline.exception.NoSuchWalletException;
import com.zipline.model.Wallet;
import com.zipline.repository.NFTRepository;
import com.zipline.repository.UserRepository;
import com.zipline.repository.WalletRepository;
import com.zipline.smartcontract.Web3Helpers;
import io.reactivex.functions.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
public class NFTService implements Consumer<Zipline.TransferEventResponse> {
    // we use this collection instead of repository, because we want to dynamically react to chain's events,
    // and implementing it with the persistent storage will require more efforts, like remembering the block ID, etc.
    private static final HashMap<BigInteger, NFTDTO> nfts = new HashMap<>();
    private static final String nullAddress = "0000000000000000000000000000000000000000";

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    /**
     * Instantiates a new NFT service.
     *
     * @param userRepository   the user repository
     * @param walletRepository the wallet repository
     */
    @Autowired
    public NFTService(final NFTRepository nftRepository_, UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;

        // process the events of NFT ownership transferred
        Web3Helpers.getNFTTransferObservable().subscribe(this);
    }

    @Transactional
    @Override
    public void accept(Zipline.TransferEventResponse transferEventResponse) throws Exception {
        // for now we just replay all events from the very beginning
        BigInteger nftId = transferEventResponse.tokenId;
        String source = transferEventResponse.from.substring(2);
        String destination = transferEventResponse.to.substring(2);

        // try to find the destination wallet among our users
        Optional<Wallet> destinationWallet = Optional.empty();
        Wallet exampleWallet = new Wallet();
        exampleWallet.setAddress(destination);
        destinationWallet = NFTService.this.walletRepository.findOne(Example.of(exampleWallet));

        // if we don't remember token with such ID, create it with data from the chain and memorize
        NFTDTO currentNft = nfts.get(nftId);
        if (currentNft == null) {
            currentNft = new NFTDTO();
            currentNft.setNftId(nftId);
            Pair<String, String> readonlyWallet = WalletService.getReadOnlyWallet();
            String tokenUri = Web3Helpers.getNftById(readonlyWallet.getFirst(), readonlyWallet.getSecond(), nftId);
            try {
                JSONObject jsonUri = new JSONObject(tokenUri);
                currentNft.setName(jsonUri.getString("name"));
                currentNft.setDescription(jsonUri.getString("description"));
                currentNft.setExternalLink(jsonUri.optString("external_link", "no external link"));
                currentNft.setImageUrl(jsonUri.getString("image_url"));
            } catch (Exception e) {
                logger.info("could not process URI of token " + nftId + ", setting it as a description");
                currentNft.setName("no name");
                currentNft.setDescription(tokenUri);
                currentNft.setExternalLink("no external link");
                currentNft.setImageUrl("no image url");
            }
        }

        // if we know about the wallet, which is the destination, set the mapping; nullify it otherwise
        if (destinationWallet.isPresent()) {
            currentNft.setWalletId(destinationWallet.get().getWalletId());
        } else {
            currentNft.setWalletId(null);
        }
        currentNft.setWalletAddress(destination);
        nfts.put(nftId, currentNft);
    }

    /**
     * Get NFT by its ID
     *
     * @param nftId ID of the NFT
     * @return NFT
     */
    public NFTDTO getNftById(final BigInteger nftId, boolean throwIfNotFound) {
        NFTDTO nft = nfts.get(nftId);
        if (nft == null && throwIfNotFound) throw new NoSuchNFTException(nftId);
        return nft;
    }

    /**
     * Get NFTs which belong to the wallet
     *
     * @param walletId to get NFTs for
     * @return collection of NFTs
     */
    public Set<NFTDTO> getNftsByWalletId(final Long walletId) {
        Set<NFTDTO> result = new HashSet<>();
        for (NFTDTO nft : nfts.values()) {
            if (nft.getWalletId() != null && nft.getWalletId().equals(walletId)) result.add(nft);
        }
        return result;
    }

    /**
     * Memorize that this wallet address has now this wallet ID
     *
     * @param walletId      to memorize
     * @param walletAddress of the wallet
     */
    public void updateNftToWalletIdMapping(final Long walletId, final String walletAddress) {
        for (NFTDTO nft : nfts.values()) {
            if (nft.getWalletAddress().equals(walletAddress)) nft.setWalletId(walletId);
        }
    }

    /**
     * Gets NFT by user.
     *
     * @param userId the user id
     * @return the NFTs by user id
     */
    public List<NFTDTO> getNFTsOfUser(final Long userId) {
        List<NFTDTO> result = new LinkedList<>();
        for (Wallet wallet : userRepository.getOne(userId).getWallets()) {
            for (NFTDTO nft : nfts.values()) {
                if (nft.getWalletId() != null && nft.getWalletId().equals(wallet.getWalletId())) result.add(nft);
            }
        }
        return result;
    }

    /**
     * Create a NFT
     *
     * @param nft      the NFT
     * @param userId   the user ID
     * @param walletId the wallet ID on which NFT will be created
     * @return the created NFT
     */
    public NFTDTO create(NFTDTO nft, final Long userId, final Long walletId) throws Exception {
        Wallet wallet = userRepository.getOne(userId).getWallets().stream()
                .filter((Wallet w) -> w.getWalletId().equals(walletId)).findAny()
                .orElseThrow(() -> new NoSuchWalletException(walletId));

        // TODO: we need a special service here, which will save token's info and return us a persistent link, which
        // TODO: we will put into blockchain; for now, store the token's JSON into blockchain
        JSONObject tokenURI = new JSONObject();
        tokenURI.put("name", nft.getName());
        tokenURI.put("description", nft.getDescription());
        tokenURI.put("external_link", nft.getExternalLink());
        tokenURI.put("image_url", nft.getImageUrl());

        Either<BigInteger, String> nftId = Web3Helpers.createNFT(
                wallet.getSecretValue(),
                Web3Helpers.getSecretForWallet(wallet.getSecretKey(), wallet.getSecretSalt()),
                tokenURI.toString());
        nft.setNftId(nftId.getLeft().orElseThrow(() -> new Exception("Could not create NFT: " + nftId.getRight().get())));
        return nft;
    }
}
