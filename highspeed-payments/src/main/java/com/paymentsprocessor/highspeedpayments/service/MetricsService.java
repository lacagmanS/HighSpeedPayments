package com.paymentsprocessor.highspeedpayments.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MetricsService {

    private final SimpMessagingTemplate messagingTemplate;
    private final AtomicLong transactionCount = new AtomicLong(0);
    private final AtomicLong totalLatency = new AtomicLong(0);

    public MetricsService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void recordTransaction(long latencyNanos) {
        transactionCount.incrementAndGet();
        totalLatency.addAndGet(latencyNanos);
    }

    @Scheduled(fixedRate = 1000)
    public void broadcastMetrics() {
        long count = transactionCount.getAndSet(0);
        long latencySum = totalLatency.getAndSet(0);

        long avgLatencyMicros = 0;
        if (count > 0) {
            avgLatencyMicros = (latencySum / count) / 1000;
        }

        Map<String, Long> metrics = Map.of(
            "tps", count,
            "avgLatencyMicros", avgLatencyMicros
        );

        messagingTemplate.convertAndSend("/topic/metrics", metrics);
    }
}