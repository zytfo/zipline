package com.zipline.smartcontract;

import com.zipline.Zipline;
import io.reactivex.Flowable;
import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
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
     * Get an observable to trades status changes
     *
     * @return the observable
     */
    public static Flowable<Zipline.TradeStatusChangeEventResponse> getTradesObservable() {
        Zipline contract = getContract(Credentials.create(new ECKeyPair(BigInteger.ZERO, BigInteger.ZERO)));
        return contract.tradeStatusChangeEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST);
    }

    /*
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
     * Get balance of the wallet
     *
     * @param walletAddress to get balance of
     * @return number of Wei on this wallet
     */
    public static BigInteger getWalletBalance(final String walletAddress) throws IOException {
        return web3.ethGetBalance("0x" + walletAddress, DefaultBlockParameterName.LATEST).send().getBalance();
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
    public static Either<BigInteger, String> createNFT(final String wallet, final String password, final String tokenURI) {
        File walletFile = null;
        try {
            walletFile = getWalletFile(wallet);
            Credentials credentials = WalletUtils.loadCredentials(password, walletFile);
            Zipline contract = getContract(credentials);

            TransactionReceipt tx_result = contract.mint(credentials.getAddress(), tokenURI).send();
            List<Zipline.TransferEventResponse> events = contract.getTransferEvents(tx_result);
            if (events.size() != 1)
                return new Either<>(null, "Transaction has returned bad output");
            return new Either<>(events.get(0).tokenId);
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            return new Either<>(null, e.getMessage());
        }
    }

    /**
     * Get a trade by its ID
     *
     * @param wallet   the wallet
     * @param password to the wallet
     * @param tradeId  to get
     * @return the trade
     */
    public static Trade getTradeById(final String wallet, final String password, BigInteger tradeId) throws Exception {
        File walletFile = null;
        try {
            walletFile = getWalletFile(wallet);
            Credentials credentials = WalletUtils.loadCredentials(password, walletFile);
            Zipline contract = getContract(credentials);

            Tuple4<String, BigInteger, BigInteger, Boolean> trade = contract.getTrade(tradeId).send();
            return new Trade(trade.component1(), trade.component2(), trade.component3(), trade.component4());
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            throw e;
        }
    }

    /**
     * Open a new trade
     *
     * @param wallet   the wallet
     * @param password to the wallet
     * @param nftId    to open a trade for
     * @param price    for the NFT
     * @return trade ID in case of success, error otherwise
     */
    public static Either<BigInteger, String> openTrade(final String wallet, final String password, final BigInteger nftId, final BigInteger price) {
        File walletFile = null;
        try {
            walletFile = getWalletFile(wallet);
            Credentials credentials = WalletUtils.loadCredentials(password, walletFile);
            Zipline contract = getContract(credentials);

            TransactionReceipt tx_result = contract.openTrade(nftId, price).send();
            List<Zipline.TradeStatusChangeEventResponse> events = contract.getTradeStatusChangeEvents(tx_result);
            if (events.size() != 1)
                return new Either<>(null, "Transaction has returned bad output");
            return new Either<>(events.get(0).id);
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            return new Either<>(null, e.getMessage());
        }
    }

    /**
     * Execute a trade
     *
     * @param wallet    the wallet
     * @param password  to the wallet
     * @param tradeId   to execute
     * @param weiAmount to transfer for this trade
     * @return true in case of success, error otherwise
     */
    public static Either<Boolean, String> executeTrade(final String wallet, final String password, final BigInteger tradeId, final BigInteger weiAmount) {
        File walletFile = null;
        try {
            walletFile = getWalletFile(wallet);
            Credentials credentials = WalletUtils.loadCredentials(password, walletFile);
            Zipline contract = getContract(credentials);

            TransactionReceipt tx_result = contract.executeTrade(tradeId, weiAmount).send();
            List<Zipline.TradeStatusChangeEventResponse> events = contract.getTradeStatusChangeEvents(tx_result);
            if (events.size() != 1)
                return new Either<>(null, "Transaction has returned bad output");
            if (events.get(0).status)
                return new Either<>(null, "Transaction has failed for some other reason");
            return new Either<>(true);
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            return new Either<>(null, e.getMessage());
        }
    }

    /**
     * Cancel the trade
     *
     * @param wallet   the wallet
     * @param password to the wallet
     * @param tradeId  to cancel
     * @return true in case of success, error otherwise
     */
    public static Either<Boolean, String> cancelTrade(final String wallet, final String password, final BigInteger tradeId) {
        File walletFile = null;
        try {
            walletFile = getWalletFile(wallet);
            Credentials credentials = WalletUtils.loadCredentials(password, walletFile);
            Zipline contract = getContract(credentials);

            TransactionReceipt tx_result = contract.cancelTrade(tradeId).send();
            List<Zipline.TradeStatusChangeEventResponse> events = contract.getTradeStatusChangeEvents(tx_result);
            if (events.size() != 1)
                return new Either<>(null, "Transaction has returned bad output");
            if (events.get(0).status)
                return new Either<>(null, "Transaction has failed for some other reason");
            return new Either<>(true);
        } catch (Exception e) {
            if (walletFile != null) walletFile.deleteOnExit();
            return new Either<>(null, e.getMessage());
        }
    }

    private static File getWalletFile(final String wallet) throws IOException {
        File walletFile = null;
        try {
            walletFile = File.createTempFile("wal", null, TEMP_WALLET_DIRECTORY);
            FileWriter writer = new FileWriter(walletFile);
            writer.write(wallet);
            writer.close();
            return walletFile;
        } catch (IOException e) {
            if (walletFile != null) walletFile.deleteOnExit();
            throw e;
        }
    }
}
