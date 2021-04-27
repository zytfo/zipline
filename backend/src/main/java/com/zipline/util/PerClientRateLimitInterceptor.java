package com.zipline.util;

import io.github.bucket4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * The type Per client rate limit interceptor.
 */
public class PerClientRateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final Bucket freeBucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(50, Refill.intervally(10, Duration.ofMinutes(1))))
            .build();

    private static Bucket standardBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(50, Refill.intervally(50, Duration.ofMinutes(1))))
                .build();
    }

    private static Bucket premiumBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(100, Refill.intervally(100, Duration.ofMinutes(1))))
                .build();
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        response.reset();
        final Bucket requestBucket;
        final String apiKey = request.getHeader("X-api-key");
        if (apiKey != null && !apiKey.isEmpty()) {
            if (apiKey.startsWith("1")) {
                requestBucket = this.buckets.computeIfAbsent(apiKey, key -> premiumBucket());
            } else {
                requestBucket = this.buckets.computeIfAbsent(apiKey, key -> standardBucket());
            }
        } else {
            requestBucket = this.freeBucket;
        }
        final ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return true;
        }
        response.reset();
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",
                Long.toString(TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill())));
        return false;
    }

}
