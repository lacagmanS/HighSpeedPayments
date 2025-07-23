package com.paymentsprocessor.highspeedpayments.disruptor.event;

import java.math.BigDecimal;

public class PaymentEvent {

    private String transactionId;
    private BigDecimal amount;
    private String sourceAccountId;
    private String destinationAccountId;
    private String status;
    private long startTime;

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }

    public String getDestinationAccountId() { return destinationAccountId; }
    public void setDestinationAccountId(String destinationAccountId) { this.destinationAccountId = destinationAccountId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public void clear() {
        this.transactionId = null;
        this.amount = null;
        this.sourceAccountId = null;
        this.destinationAccountId = null;
        this.status = null;
        this.startTime = 0L;
    }

    @Override
    public String toString() {
        return "PaymentEvent{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", sourceAccountId='" + sourceAccountId + '\'' +
                ", destinationAccountId='" + destinationAccountId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}