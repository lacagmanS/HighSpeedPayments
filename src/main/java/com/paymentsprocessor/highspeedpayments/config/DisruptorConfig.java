package com.paymentsprocessor.highspeedpayments.config;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;
import com.paymentsprocessor.highspeedpayments.disruptor.factory.PaymentEventFactory;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.ClearingHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.JournalingHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.handler.ValidationHandler;
import com.paymentsprocessor.highspeedpayments.disruptor.publisher.PaymentEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * This is the central configuration class for the LMAX Disruptor.
 * It uses Spring's @Configuration to set up all the necessary beans
 * and wire them together when the application starts.
 */
@Configuration
public class DisruptorConfig {

    // --- Injected Handler Dependencies ---
    // Spring will automatically find our @Component-annotated handlers and inject them here.
    private final JournalingHandler journalingHandler;
    private final ValidationHandler validationHandler;
    private final ClearingHandler clearingHandler;

    public DisruptorConfig(JournalingHandler journalingHandler, ValidationHandler validationHandler, ClearingHandler clearingHandler) {
        this.journalingHandler = journalingHandler;
        this.validationHandler = validationHandler;
        this.clearingHandler = clearingHandler;
    }

    /**
     * This @Bean method creates, configures, and launches the entire Disruptor system.
     * The Disruptor instance itself is managed by Spring as a singleton bean.
     *
     * @return The configured RingBuffer<PaymentEvent> that other parts of the application can use to publish events.
     */
    @Bean
    public RingBuffer<PaymentEvent> disruptorRingBuffer() {

        ThreadFactory threadFactory = Executors.defaultThreadFactory();


        PaymentEventFactory eventFactory = new PaymentEventFactory();


        int bufferSize = 1024;


        Disruptor<PaymentEvent> disruptor = new Disruptor<>(
                eventFactory,
                bufferSize,
                threadFactory,
                ProducerType.SINGLE, // We have a single type of producer (our API).
                new BlockingWaitStrategy() // A simple wait strategy. Good for many use cases.
        );


        disruptor
                .handleEventsWith(journalingHandler)
                .then(validationHandler)
                .then(clearingHandler);

        RingBuffer<PaymentEvent> ringBuffer = disruptor.start();

        return ringBuffer;
    }


    @Bean
    public PaymentEventPublisher paymentEventPublisher(RingBuffer<PaymentEvent> ringBuffer) {
        return new PaymentEventPublisher(ringBuffer);
    }

}