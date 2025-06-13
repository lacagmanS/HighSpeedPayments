package com.paymentsprocessor.highspeedpayments.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import com.paymentsprocessor.highspeedpayments.disruptor.event.PaymentEvent;

/**
 * The PaymentEventFactory is responsible for creating new PaymentEvent objects.
 *
 * This factory is called by the Disruptor framework once for every slot in the RingBuffer
 * when the application first starts up. Its purpose is to pre-allocate the memory
 * for all the event objects that will be reused throughout the application's lifecycle.
 *
 * This pre-allocation is a core tenet of the Disruptor pattern and is key to its
 * high-performance, garbage-free characteristics during runtime.
 */
public class PaymentEventFactory implements EventFactory<PaymentEvent> {

    /**
     * This method is called by the Disruptor to create a new event instance.
     * It will be called once per slot in the RingBuffer upon initialization.
     *
     * @return A new, empty PaymentEvent object.
     */
    @Override
    public PaymentEvent newInstance() {
        return new PaymentEvent();
    }
}