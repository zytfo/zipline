package com.zipline.service;

import com.zipline.exception.NoSuchWalletException;
import com.zipline.model.User;
import com.zipline.model.Wallet;
import com.zipline.repository.UserRepository;
import com.zipline.repository.WalletRepository;
import com.zipline.smartcontract.Web3Helpers;
import javassist.NotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.utils.Files;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WalletService {
    private static Pair<String, String> readOnlyWalletContentPassword = Pair.of("", "");

    UserRepository userRepository;
    WalletRepository walletRepository;

    /**
     * Instantiates a new Wallet service.
     */
    @Autowired
    public WalletService(final UserRepository userRepository, final WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    /**
     * Get a special wallet for read-only operations of the application itself
     *
     * @return the wallet content + password
     */
    public static Pair<String, String> getReadOnlyWallet()
            throws IOException, InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, NoSuchProviderException {
        if (!readOnlyWalletContentPassword.getFirst().isEmpty()) return readOnlyWalletContentPassword;

        String password = Web3Helpers.getSecretForWallet(RandomString.make(64), RandomString.make(16));
        String wallet = Web3Helpers.createWallet(password);
        readOnlyWalletContentPassword = Pair.of(wallet, password);
        return readOnlyWalletContentPassword;
    }

    /**
     * Gets all wallets of the user
     *
     * @param userId the User id
     * @return the Wallets by user id
     */
    public List<Wallet> getWalletsOfUser(final Long userId) {
        return new ArrayList<>(walletRepository.findAllByOwnerId(userId));
    }

    /**
     * Create a new wallet for the user
     *
     * @param userId     ID of the user to create wallet for
     * @param walletName name of the wallet to be created
     * @return the created Wallet
     * @throws Exception in case something bad happens in blockchain
     */
    public Wallet createWallet(final Long userId, final String walletName) throws Exception {
        return createWallet(userId, walletName, null);
    }

    /**
     * Delete a wallet
     *
     * @param userId   owner of the wallet
     * @param walletId of the wallet to be deleted
     */
    public void deleteWallet(final Long userId, final Long walletId) throws NotFoundException {
        Wallet wallet = getWallet(userId, walletId);
        walletRepository.delete(wallet);
    }

    /**
     * Import a wallet for the user
     *
     * @param userId     ID of the user to import wallet for
     * @param walletName name of the wallet to be created
     * @param privateKey of the wallet to be imported
     * @return the imported Wallet
     */
    public Wallet importWallet(final Long userId, final String walletName, final String privateKey) throws Exception {
        return createWallet(userId, walletName, privateKey);
    }

    /**
     * Export a wallet of the user
     *
     * @param userId   to export wallet of
     * @param walletId of the wallet to be exported
     * @return private key of the exported wallet
     */
    public String exportWallet(final Long userId, final Long walletId) throws Exception {
        Wallet wallet = getUser(userId).getWallets().stream()
                .filter((Wallet w) -> w.getWalletId().equals(walletId)).findFirst()
                .orElseThrow(() -> new NoSuchWalletException(walletId));
        return Web3Helpers.getWalletPK(
                Web3Helpers.getSecretForWallet(wallet.getSecretKey(), wallet.getSecretSalt()), wallet.getSecretValue());
    }

    /**
     * Get a balance of the wallet
     *
     * @param userId   the wallet's owner
     * @param walletId of the wallet
     * @return balance of the requested wallet
     */
    public BigInteger getWalletBalance(final Long userId, final Long walletId) throws NotFoundException, IOException {
        Wallet wallet = getWallet(userId, walletId);
        return Web3Helpers.getWalletBalance(wallet.getAddress());
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

    private Wallet createWallet(final Long userId, final String walletName, final String privateKey) throws Exception {
        User user = getUser(userId);

        String key = RandomString.make(64);
        String salt = RandomString.make(16);
        String wallet;
        if (privateKey != null)
            wallet = Web3Helpers.createWallet(Web3Helpers.getSecretForWallet(key, salt), privateKey);
        else
            wallet = Web3Helpers.createWallet(Web3Helpers.getSecretForWallet(key, salt));
        JSONObject walletJson = new JSONObject(wallet);

        Wallet newWallet = new Wallet(walletName, walletJson.getString("address"), key, salt, wallet, user);
        Wallet savedWallet = walletRepository.save(newWallet);

        user.getWallets().add(savedWallet);
        userRepository.save(user);

        return savedWallet;
    }
}
