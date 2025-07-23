package com.paymentsprocessor.highspeedpayments.domain;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionRecord {

    private String transactionId;
    private String sourceAccountId;
    private String destinationAccountId;
    private BigDecimal amount;
    private String status;
    private Instant timestamp;
    private String signature;

    public TransactionRecord(String transactionId, String sourceAccountId, String destinationAccountId, BigDecimal amount, String status) {
        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.status = status;
        this.timestamp = Instant.now();
    }

    public String toSignatureString() {
        return String.join("|", transactionId, sourceAccountId, destinationAccountId, amount.toPlainString(), status, timestamp.toString());
    }
    
    // Add Getters and Setters for all fields
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }
    public String getDestinationAccountId() { return destinationAccountId; }
    public void setDestinationAccountId(String destinationAccountId) { this.destinationAccountId = destinationAccountId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }
}