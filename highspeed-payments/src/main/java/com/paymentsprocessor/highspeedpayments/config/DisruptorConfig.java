package com.paymentsprocessor.highspeedpayments.config;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.disruptor.factory.PaymentEventFactory;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.ClearingHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.JournalingHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.SettlementHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.ValidationHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.publisher.PaymentEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class DisruptorConfig {

    private final JournalingHandler journalingHandler;
    private final ValidationHandler validationHandler;
    private final SettlementHandler settlementHandler;
    private final ClearingHandler clearingHandler;

    public DisruptorConfig(JournalingHandler journalingHandler, ValidationHandler validationHandler, SettlementHandler settlementHandler, ClearingHandler clearingHandler) {
    this.journalingHandler = journalingHandler;
    this.validationHandler = validationHandler;
    this.settlementHandler = settlementHandler;
    this.clearingHandler = clearingHandler;
}

    @Bean
    public RingBuffer<PaymentEvent> disruptorRingBuffer() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        PaymentEventFactory eventFactory = new PaymentEventFactory();
        int bufferSize = 1024;

        Disruptor<PaymentEvent> disruptor = new Disruptor<>(
                eventFactory,
                bufferSize,
                threadFactory,
                ProducerType.SINGLE,
                new BlockingWaitStrategy()
        );

        disruptor
                .handleEventsWith(journalingHandler)
                .then(validationHandler)
                .then(settlementHandler)
                .then(clearingHandler);

        return disruptor.start();
    }

    @Bean
    public PaymentEventPublisher paymentEventPublisher(RingBuffer<PaymentEvent> ringBuffer) {
        return new PaymentEventPublisher(ringBuffer);
    }
}