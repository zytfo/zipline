package com.zipline.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Demo metrics.
 */
@Component
public class DemoMetrics {
    private final Counter demoCounter;
    private final AtomicInteger demoGauge;

    /**
     * Instantiates a new Demo metrics.
     *
     * @param meterRegistry the meter registry
     */
    public DemoMetrics(MeterRegistry meterRegistry) {
        this.demoCounter = meterRegistry.counter("demo_counter");
        this.demoGauge = meterRegistry.gauge("demo_gauge", new AtomicInteger(0));
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Gets random metrics data.
     */
    public void getRandomMetricsData() {
        demoGauge.set(getRandomNumberInRange(0, 100));
        demoCounter.increment();
    }
}
