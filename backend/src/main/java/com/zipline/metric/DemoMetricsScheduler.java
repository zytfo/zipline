package com.zipline.metric;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The type Demo metrics scheduler.
 */
@Component
public class DemoMetricsScheduler {

    private final DemoMetrics demoMetrics;

    /**
     * Instantiates a new Demo metrics scheduler.
     *
     * @param demoMetrics the demo metrics
     */
    public DemoMetricsScheduler(DemoMetrics demoMetrics) {
        this.demoMetrics = demoMetrics;
    }

    /**
     * Trigger custom metrics.
     */
    @Scheduled(fixedRate = 1000)
    public void triggerCustomMetrics() {
        demoMetrics.getRandomMetricsData();
    }
}
