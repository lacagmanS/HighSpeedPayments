package com.paymentsprocessor.highspeedpayments.disruptor.event;

import java.math.BigDecimal;


public class PaymentEvent {

    private String transactionId;
    private BigDecimal amount;
    private String sourceAccountId;
    private String destinationAccountId;


    private String status;


    // --- Getters and Setters ---

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }

    public String getDestinationAccountId() { return destinationAccountId; }
    public void setDestinationAccountId(String destinationAccountId) { this.destinationAccountId = destinationAccountId; }

    // --- GETTER AND SETTER FOR NEW FIELD ---
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


    /**
     * CRITICAL: Update the clear method to reset the new status field.
     */
    public void clear() {
        this.transactionId = null;
        this.amount = null;
        this.sourceAccountId = null;
        this.destinationAccountId = null;
        // --- ADD THIS LINE ---
        this.status = null;
    }

    @Override
    public String toString() {
        // Let's add the status to our toString for better debugging.
        return "PaymentEvent{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", sourceAccountId='" + sourceAccountId + '\'' +
                ", destinationAccountId='" + destinationAccountId + '\'' +
                // --- ADD THIS LINE ---
                ", status='" + status + '\'' +
                '}';
    }
}