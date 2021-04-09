package com.zipline.service;

import com.sun.syndication.feed.module.DCModuleImpl;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.zipline.model.News;
import com.zipline.model.cryptopanic.CryptopanicCurrency;
import com.zipline.model.cryptopanic.CryptopanicResponse;
import com.zipline.model.cryptopanic.CryptopanicResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Cryptopanic service.
 */
@Service
@Transactional
public class CryptopanicService {
    private static final Logger logger = LoggerFactory.getLogger(CryptopanicService.class);

    private final RestTemplate restTemplate;
    private final NewsFeedService newsFeedService;
    private LocalDateTime lastTimeChecked = LocalDateTime.now();
    private LocalDateTime lastTimeCheckedRSS = LocalDateTime.now();


    @Value("${cryptopanic.apiKey}")
    private String cryptopanicApiKey;

    @Value("${cryptopanic.url}")
    private String url;

    @Value("${cointelegraph.urlRSS}")
    private String urlRSS;

    /**
     * Instantiates a new Coin paprika client service.
     *
     * @param restTemplate    the rest template
     * @param newsFeedService the news feed service
     */
    @Autowired
    public CryptopanicService(final RestTemplate restTemplate,
                              final NewsFeedService newsFeedService) {
        this.restTemplate = restTemplate;
        this.newsFeedService = newsFeedService;
    }

    /**
     * Gets news.
     *
     * @throws IOException the io exception
     */
//    @Scheduled(cron = "0 * */4 * * *")
//    @Scheduled(fixedDelay = 5000)
    public void getNews() throws IOException {
        final CryptopanicResponse response = restTemplate.getForObject(url + cryptopanicApiKey + "&regions=en,de", CryptopanicResponse.class);
        if (response != null) {
            for (CryptopanicResult result : response.getResults()) {
                if (result.getPublishedAt().isAfter(lastTimeChecked)) {
                    final News news = new News();
                    news.setTitle(Optional.ofNullable(result.getTitle()).orElse("Default title"));
                    news.setDescription(Optional.ofNullable(result.getDescription()).orElse("Default description"));
//                    final Document doc = Jsoup.connect(result.getUrl()).get();
                    // TODO: parse content
                    news.setContent(Optional.ofNullable(news.getTitle()).orElse("Default content"));
                    news.setSource(Optional.ofNullable(result.getSource().getTitle()).orElse("Default source"));
                    news.setCreated(Optional.ofNullable(result.getPublishedAt()).orElse(LocalDateTime.now()));
                    // TODO: streams
                    if (result.getCurrencies() != null) {
                        final List<String> tags = new ArrayList<>();
                        for (CryptopanicCurrency currency : result.getCurrencies()) {
                            tags.add(currency.getTitle().toLowerCase());
                        }
                        news.setTags(tags);
                    }
                    newsFeedService.save(news);
                }
            }
        }
        lastTimeChecked = LocalDateTime.now();
    }

    /**
     * Gets news rss.
     *
     * @throws FeedException the feed exception
     * @throws IOException   the io exception
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Scheduled(fixedDelay = 5000)
    public void getNewsRss() throws FeedException, IOException {
        final URL feedSource = new URL(urlRSS);
        final SyndFeedInput input = new SyndFeedInput();
        final SyndFeed feed = input.build(new XmlReader(feedSource));
        News news;
        LocalDateTime entryPublishedTime;
        SyndEntryImpl entry;
        SyndEnclosureImpl enclosure;
        Document document;
        String description;
        DCModuleImpl module;
        // TODO: optimize
        Collections.reverse(feed.getEntries());
        for (Object o : feed.getEntries()) {
            entry = (SyndEntryImpl) o;
            entryPublishedTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getPublishedDate().getTime()), ZoneOffset.UTC);
            if (entryPublishedTime.isAfter(lastTimeCheckedRSS)) {
                news = new News();
                news.setTitle(entry.getTitle());
                news.setDescription(entry.getDescription().getValue());
                enclosure = (SyndEnclosureImpl) entry.getEnclosures().get(0);
                news.setImageUrl(enclosure.getUrl());
                document = Jsoup.parse(entry.getDescription().getValue());
                try {
                    description = document.getElementsByTag("p").get(1).childNode(0).toString();
                } catch (IndexOutOfBoundsException e) {
                    description = "";
                }
                news.setContent(description);
                news.setDescription(description);
                module = (DCModuleImpl) entry.getModules().get(0);
                news.setSource(module.getCreator());
                news.setCreated(entryPublishedTime);
                final List<String> tags = new ArrayList<>();
                for (Object e : entry.getCategories()) {
                    SyndCategoryImpl category = (SyndCategoryImpl) e;
                    tags.add(category.getName().toLowerCase());
                }
                news.setTags(tags);
                newsFeedService.save(news);
                lastTimeCheckedRSS = entryPublishedTime;
            } else {
                break;
            }
        }
    }
}
