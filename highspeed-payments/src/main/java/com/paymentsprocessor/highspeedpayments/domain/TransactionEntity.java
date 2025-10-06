package com.paymentsprocessor.highspeedpayments.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private AccountEntity sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private AccountEntity destinationAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String transactionType;

    private String merchantId;
    private String ipAddress;
    private String userAgent;
    private String geolocation;

    @Column(nullable = false)
    private String status;

    private Double v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17, v18, v19, v20, v21, v22, v23, v24, v25, v26, v27, v28;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public AccountEntity getSourceAccount() { return sourceAccount; }
    public void setSourceAccount(AccountEntity sourceAccount) { this.sourceAccount = sourceAccount; }
    public AccountEntity getDestinationAccount() { return destinationAccount; }
    public void setDestinationAccount(AccountEntity destinationAccount) { this.destinationAccount = destinationAccount; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public String getGeolocation() { return geolocation; }
    public void setGeolocation(String geolocation) { this.geolocation = geolocation; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getV1() { return v1; }
    public void setV1(Double v1) { this.v1 = v1; }
    public Double getV2() { return v2; }
    public void setV2(Double v2) { this.v2 = v2; }
    public Double getV3() { return v3; }
    public void setV3(Double v3) { this.v3 = v3; }
    public Double getV4() { return v4; }
    public void setV4(Double v4) { this.v4 = v4; }
    public Double getV5() { return v5; }
    public void setV5(Double v5) { this.v5 = v5; }
    public Double getV6() { return v6; }
    public void setV6(Double v6) { this.v6 = v6; }
    public Double getV7() { return v7; }
    public void setV7(Double v7) { this.v7 = v7; }
    public Double getV8() { return v8; }
    public void setV8(Double v8) { this.v8 = v8; }
    public Double getV9() { return v9; }
    public void setV9(Double v9) { this.v9 = v9; }
    public Double getV10() { return v10; }
    public void setV10(Double v10) { this.v10 = v10; }
    public Double getV11() { return v11; }
    public void setV11(Double v11) { this.v11 = v11; }
    public Double getV12() { return v12; }
    public void setV12(Double v12) { this.v12 = v12; }
    public Double getV13() { return v13; }
    public void setV13(Double v13) { this.v13 = v13; }
    public Double getV14() { return v14; }
    public void setV14(Double v14) { this.v14 = v14; }
    public Double getV15() { return v15; }
    public void setV15(Double v15) { this.v15 = v15; }
    public Double getV16() { return v16; }
    public void setV16(Double v16) { this.v16 = v16; }
    public Double getV17() { return v17; }
    public void setV17(Double v17) { this.v17 = v17; }
    public Double getV18() { return v18; }
    public void setV18(Double v18) { this.v18 = v18; }
    public Double getV19() { return v19; }
    public void setV19(Double v19) { this.v19 = v19; }
    public Double getV20() { return v20; }
    public void setV20(Double v20) { this.v20 = v20; }
    public Double getV21() { return v21; }
    public void setV21(Double v21) { this.v21 = v21; }
    public Double getV22() { return v22; }
    public void setV22(Double v22) { this.v22 = v22; }
    public Double getV23() { return v23; }
    public void setV23(Double v23) { this.v23 = v23; }
    public Double getV24() { return v24; }
    public void setV24(Double v24) { this.v24 = v24; }
    public Double getV25() { return v25; }
    public void setV25(Double v25) { this.v25 = v25; }
    public Double getV26() { return v26; }
    public void setV26(Double v26) { this.v26 = v26; }
    public Double getV27() { return v27; }
    public void setV27(Double v27) { this.v27 = v27; }
    public Double getV28() { return v28; }
    public void setV28(Double v28) { this.v28 = v28; }
}