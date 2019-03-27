package com.rahul.bankaccount.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountId;
    private AccountType accountType;
    private String customerName;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
    private List<BankTransaction> transactions;

    public BankAccount(String accountId, AccountType accountType, String customerName) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.customerName = customerName;
        this.transactions = new ArrayList<>();
        this.currentBalance = BigDecimal.ZERO;
        this.previousBalance = BigDecimal.ZERO;
    }

    public BankTransaction debit(BigDecimal amount) {
        if (currentBalance.compareTo(amount) < 0) return null;
        this.previousBalance = this.currentBalance;
        this.currentBalance = this.currentBalance.subtract(amount);

        BankTransaction transaction = new BankTransaction(accountId, TransactionType.DEBIT, amount);
        this.transactions.add(transaction);
        return transaction;
    }

    public BankTransaction credit(BigDecimal amount) {
        this.previousBalance = this.currentBalance;
        this.currentBalance = this.currentBalance.add(amount);
        BankTransaction transaction = new BankTransaction(accountId, TransactionType.CREDIT, amount);
        this.transactions.add(transaction);
        return transaction;
    }

    public void revertLastTransaction() {
        this.currentBalance = this.previousBalance;
        if (transactions.size() <= 1) return;
        this.previousBalance = computePreviousBalance();
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }

    public BigDecimal computePreviousBalance() {
        BankTransaction trans = null;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            trans = transactions.get(i);
            if(trans.getSuccessful()) break;
        }

        if(trans != null && trans.getTransactionType() == TransactionType.CREDIT) {
            return this.currentBalance.subtract(trans.getAmount());
        }

        if(trans != null && trans.getTransactionType() == TransactionType.DEBIT) {
            return this.currentBalance.add(trans.getAmount());
        }

        return BigDecimal.ZERO;
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountId='" + accountId + '\'' +
                ", accountType=" + accountType +
                ", customerName='" + customerName + '\'' +
                ", previousBalance=" + previousBalance +
                ", currentBalance=" + currentBalance +
                ", transactions=" + transactions +
                '}';
    }
}