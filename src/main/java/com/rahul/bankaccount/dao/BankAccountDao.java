package com.rahul.bankaccount.dao;

import com.rahul.bankaccount.model.AccountType;
import com.rahul.bankaccount.model.AmountDto;
import com.rahul.bankaccount.model.BankAccount;
import com.rahul.bankaccount.model.BankTransaction;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class BankAccountDao {

    private ConcurrentMap<String, BankAccount> accountList = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        List<BankAccount> bankAccounts = Arrays.asList(
                new BankAccount("S1", AccountType.SAVINGS, "Rahul Mittal"),
                new BankAccount("S2", AccountType.SAVINGS, "qwerty"),
                new BankAccount("S3", AccountType.SAVINGS, "asdfg"),
                new BankAccount("C1", AccountType.CURRENT, "zxcvb"),
                new BankAccount("C2", AccountType.CURRENT, "qazxsw")
        );

        for (BankAccount bankAccount : bankAccounts) {
            accountList.put(bankAccount.getAccountId(), bankAccount);
        }
    }

    public BankAccount getBankAccount(String accountId) {
        return accountList.get(accountId);
    }

    public BankTransaction deposit(String accountId, BigDecimal amount) {
        BankAccount bankAccount = accountList.get(accountId);
        BankTransaction transaction = bankAccount.credit(amount);
        return transaction;
    }

    public BankTransaction withdraw(String accountId, BigDecimal amount) {
        BankAccount bankAccount = accountList.get(accountId);
        BankTransaction transaction = bankAccount.debit(amount);
        return transaction;
    }

    public AmountDto checkBalance(String accountId) {
        BankAccount bankAccount = accountList.get(accountId);
        BigDecimal balance = bankAccount.getCurrentBalance();
        return new AmountDto(balance);
    }

    public List<BankTransaction> listTransactions(String accountId) {
        BankAccount bankAccount = accountList.get(accountId);
        return bankAccount.getTransactions();
    }

    public Boolean accountExists(String accountId) {
        return accountList.containsKey(accountId);
    }

}
