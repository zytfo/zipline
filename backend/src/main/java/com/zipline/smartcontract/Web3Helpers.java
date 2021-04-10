package com.zipline.smartcontract;

import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Files;
import springfox.documentation.common.Either;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.List;

/**
 * Helper utils to communicate with Web3j API
 */
public class Web3Helpers {
    private static final String ROPSTEN_NODE_ADDRESS = "https://ropsten.infura.io/v3/dfa2b7d4df384d47970010b94c8a9519";
    private static final String BINANCE_TEST_NODE_ADDRESS = "https://data-seed-prebsc-1-s1.binance.org:8545";
    private static final String CONTRACT_ADDRESS = "0xF4Ce23586D037cFE538c724FC1B08a130Fbc2571";
    private static final byte CHAIN_ID = 97; // Binance test chain ID; mainnet is 56, https://docs.binance.org/smart-chain/wallet/metamask.html

    private static final File TEMP_WALLET_DIRECTORY = new File("/tmp/");

    private static final Web3j web3 = Web3j.build(new HttpService(BINANCE_TEST_NODE_ADDRESS));
    // TODO: this must be calculated dynamically
    private static final ContractGasProvider gasProvider = new StaticGasProvider(Convert.toWei("20", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(500000));

    /**
     * Get a smart contract wrapper
     *
     * @param credentials to access the contract
     * @return the contract
     */
    public static Zipline getContract(Credentials credentials) {
        return Zipline.load(CONTRACT_ADDRESS, web3, new RawTransactionManager(web3, credentials, CHAIN_ID), gasProvider);
    }

    /**
     * Create a completely new wallet
     *
     * @param walletPassword to be used to encrypt the wallet data
     * @return contents of the wallet, for example, to be encrypted and stored to DB
     */
    public static String createWallet(final String walletPassword) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String walletFileName = WalletUtils.generateFullNewWalletFile(walletPassword, TEMP_WALLET_DIRECTORY);
        File walletFile = new File(TEMP_WALLET_DIRECTORY, walletFileName);
        String walletContent = Files.readString(walletFile);
        walletFile.delete();
        return walletContent;
    }

    /**
     * Create a new wallet from the existing private key
     *
     * @param walletPassword to be used to encrypt the wallet data
     * @param privateKey     from which the wallet is created
     * @return contents of the wallet, for example, to be encrypted and stored to DB
     */
    public static String createWallet(final String walletPassword, final String privateKey) throws CipherException, IOException {
        ECKeyPair keyPair = ECKeyPair.create(Hex.decode(privateKey));
        String walletFileName = WalletUtils.generateWalletFile(walletPassword, keyPair, TEMP_WALLET_DIRECTORY, true);
        File walletFile = new File(TEMP_WALLET_DIRECTORY, walletFileName);
        String walletContent = Files.readString(walletFile);
        walletFile.delete();
        return walletContent;
    }

    /**
     * Get a private key of the wallet
     *
     * @param wallet         to get private key of
     * @param walletPassword to the wallet
     * @return private key of the wallet
     */
    public static String getWalletPK(final String walletPassword, final String wallet) throws IOException, CipherException {
        File walletFile = null;
        try {
            walletFile = File.createTempFile("wal", null, TEMP_WALLET_DIRECTORY);
            FileWriter writer = new FileWriter(walletFile);
            writer.write(wallet);
            writer.close();

            Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFile);
            return credentials.getEcKeyPair().getPrivateKey().toString(16);
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            throw e;
        }
    }

    /**
     * Transform a secret, so that it becomes an encryption key; not a really good security by any sense, but OK for now
     *
     * @param rawSecret to be transformed
     * @param salt      the salt
     * @return transformed secret
     */
    public static String getSecretForWallet(String rawSecret, String salt) {
        return Arrays.toString(BCrypt.generate(rawSecret.getBytes(), salt.getBytes(), 10));
    }

    /**
     * Create a NFT token
     *
     * @param wallet   to create token on
     * @param password to the wallet
     * @param tokenURI to put into the new token
     * @return ID of the token in case of success, stringified error otherwise
     */
    public static Either<Long, String> createNFT(final String wallet, final String password, final String tokenURI) {
        File walletFile = null;
        try {
            walletFile = File.createTempFile("wal", null, TEMP_WALLET_DIRECTORY);
            FileWriter writer = new FileWriter(walletFile);
            writer.write(wallet);
            writer.close();

            // TODO: transaction send must be async
            Credentials credentials = WalletUtils.loadCredentials(password, walletFile);
            Zipline contract = getContract(credentials);
            TransactionReceipt tx_result = contract.mint(credentials.getAddress(), tokenURI).send();
            List<Zipline.TransferEventResponse> events = contract.getTransferEvents(tx_result);
            if (events.size() != 1)
                return new Either<>(null, "Transaction has returned bad output");
            return new Either<>(events.get(0).tokenId.longValue());
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            return new Either<>(null, e.getMessage());
        }
    }
}
