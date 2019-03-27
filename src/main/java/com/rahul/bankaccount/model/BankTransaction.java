package com.rahul.bankaccount.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankTransaction {
    private String accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private Boolean successful;
    private LocalDateTime dateTime;

    public BankTransaction() {
    }

    public BankTransaction(String accountId, TransactionType transactionType, BigDecimal amount) {
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.successful = true;
        this.dateTime = LocalDateTime.now();
    }

    public String getAccountId() {
        return accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "BankTransaction{" +
                "accountId='" + accountId + '\'' +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", successful=" + successful +
                ", dateTime=" + dateTime +
                '}';
    }
}
