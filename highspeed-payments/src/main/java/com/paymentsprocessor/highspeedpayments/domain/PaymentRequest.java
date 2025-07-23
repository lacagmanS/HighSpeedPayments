package com.paymentsprocessor.highspeedpayments.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) that represents the structure of an
 * incoming payment request from an external client via the API.
 *
 * We use validation annotations (@NotNull, @Positive) to ensure that
 * the incoming data meets basic requirements before we even start processing it.
 * Spring Boot's validation starter will handle this automatically.
 */
public class PaymentRequest {

    @NotNull(message = "Source account ID cannot be null")
    private String sourceAccountId;

    @NotNull(message = "Destination account ID cannot be null")
    private String destinationAccountId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    // Standard Getters and Setters are required for JSON deserialization

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}