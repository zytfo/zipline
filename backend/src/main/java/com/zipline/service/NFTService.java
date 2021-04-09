package com.zipline.service;

import com.zipline.exception.NoSuchWalletException;
import com.zipline.model.NFT;
import com.zipline.model.Wallet;
import com.zipline.repository.NFTRepository;
import com.zipline.repository.UserRepository;
import com.zipline.smartcontract.Web3Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import springfox.documentation.common.Either;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NFTService {
    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);
    private final NFTRepository nftRepository;
    private final UserRepository userRepository;

    /**
     * Instantiates a new NFT service.
     *
     * @param nftRepository  the NFT repository
     * @param userRepository the user repository
     */
    @Autowired
    public NFTService(final NFTRepository nftRepository, UserRepository userRepository) {
        this.nftRepository = nftRepository;
        this.userRepository = userRepository;
    }

    /**
     * Gets NFT by user.
     *
     * @param userId the user id
     * @return the NFTs by user id
     */
    public List<NFT> getNFTsOfUser(final Long userId) {
        return userRepository
                .getOne(userId).getWallets().stream()
                .map(Wallet::getNfts).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Create a NFT
     *
     * @param nft      the NFT
     * @param userId   the user ID
     * @param walletId the wallet ID on which NFT will be created
     * @return the created NFT
     */
    public NFT create(NFT nft, final Long userId, final Long walletId) throws Exception {
        Wallet wallet = userRepository.getOne(userId).getWallets().stream()
                .filter((Wallet w) -> w.getWalletId().equals(walletId)).findAny()
                .orElseThrow(() -> new NoSuchWalletException(walletId));

        // TODO: we need a special service here, which will save token's info and return us a persistent link, which
        // TODO: we will put into blockchain; for now, store the token's JSON into blockchain
        JSONObject tokenURI = new JSONObject();
        tokenURI.put("name", nft.getName());
        tokenURI.put("description", nft.getDescription());
        tokenURI.put("image_url", nft.getImageUrl());

        Either<Long, String> nftId = Web3Helpers.createNFT(
                wallet.getSecretValue(),
                Web3Helpers.getSecretForWallet(wallet.getSecretKey(), wallet.getSecretSalt()),
                tokenURI.toString());
        nft.setNftId(nftId.getLeft().orElseThrow(() -> new Exception("Could not create NFT: " + nftId.getRight().get())));
        return nftRepository.save(nft);
    }
}
