package com.paymentsprocessor.highspeedpayments.disruptor.event;

import java.math.BigDecimal;

/**
 * This is the data carrier object that will live in the RingBuffer.
 * It is a Plain Old Java Object (POJO) that is intentionally mutable.
 *
 * The Disruptor pre-allocates these event objects in the RingBuffer. Publishers and
 * event handlers will get a reference to an existing PaymentEvent object and update
 * its fields, rather than creating new objects. This avoids creating "garbage"
 * and significantly improves performance by reducing GC pressure.
 */
public class PaymentEvent {

    // A unique identifier for the transaction.
    private String transactionId;

    // The monetary value of the payment. Using BigDecimal is standard practice
    // for financial calculations to avoid floating-point inaccuracies.
    private BigDecimal amount;

    // The account from which the funds are being debited.
    private String sourceAccountId;

    // The account to which the funds are being credited.
    private String destinationAccountId;

    // --- Getters and Setters ---
    // These methods will be used by publishers to populate the event and by
    // handlers to read and process the data.

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    /**
     * This is a CRITICAL method for the Disruptor pattern.
     * At the end of the processing pipeline, the final event handler will call this
     * method to wipe the event object clean. This makes the slot in the RingBuffer
     * available for a new event to be published into it without needing to
     * allocate a new object.
     */
    public void clear() {
        this.transactionId = null;
        this.amount = null;
        this.sourceAccountId = null;
        this.destinationAccountId = null;
    }

    @Override
    public String toString() {
        return "PaymentEvent{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", sourceAccountId='" + sourceAccountId + '\'' +
                ", destinationAccountId='" + destinationAccountId + '\'' +
                '}';
    }
}