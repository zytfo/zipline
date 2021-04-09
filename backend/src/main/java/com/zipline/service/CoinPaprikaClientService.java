package com.zipline.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

/**
 * The type Coin paprika client service.
 */
@Service
@Transactional
public class CoinPaprikaClientService {
    private static final Logger logger = LoggerFactory.getLogger(CoinPaprikaClientService.class);
    private final RestTemplate restTemplate;

    @Value("${coin.paprika.endpoint}")
    private String coinPaprikaEndpoint;

    /**
     * Instantiates a new Coin paprika client service.
     *
     * @param restTemplate the rest template
     */
    @Autowired
    public CoinPaprikaClientService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Gets ticker by coin id.
     *
     * @param coinId the coin id
     * @return the ticker by coin id
     */
    public Object getTickerByCoinId(final String coinId) {
        String uri = coinPaprikaEndpoint + "tickers/" + coinId;
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets all tickers.
     *
     * @return the all tickers
     */
    public Object getAllTickers() {
        String uri = coinPaprikaEndpoint + "tickers/";
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets coin by id.
     *
     * @param coinId the coin id
     * @return the coin by id
     */
    public Object getCoinById(final String coinId) {
        String uri = coinPaprikaEndpoint + "coins/" + coinId;
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets exchanges by coin id.
     *
     * @param coinId the coin id
     * @return the exchanges by coin id
     */
    public Object getExchangesByCoinId(final String coinId) {
        String uri = coinPaprikaEndpoint + "coins/" + coinId + "/exchanges";
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets markets by coin id.
     *
     * @param coinId the coin id
     * @return the markets by coin id
     */
    public Object getMarketsByCoinId(final String coinId) {
        String uri = coinPaprikaEndpoint + "coins/" + coinId + "/markets";
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets ohlc by coin id.
     *
     * @param coinId the coin id
     * @return the ohlc by coin id
     */
    public Object getOHLCByCoinId(final String coinId) {
        String uri = coinPaprikaEndpoint + "coins/" + coinId + "/ohlcv/latest";
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets exchanges.
     *
     * @return the exchanges
     */
    public Object getExchanges() {
        String uri = coinPaprikaEndpoint + "exchanges/";
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets exchange by id.
     *
     * @param exchangeId the exchange id
     * @return the exchange by id
     */
    public Object getExchangeById(final String exchangeId) {
        String uri = coinPaprikaEndpoint + "exchanges/" + exchangeId;
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Gets markets by exchange id.
     *
     * @param exchangeId the exchange id
     * @return the markets by exchange id
     */
    public Object getMarketsByExchangeId(final String exchangeId) {
        String uri = coinPaprikaEndpoint + "exchanges/" + exchangeId + "/markets";
        return restTemplate.getForObject(uri, Object.class);
    }

}
