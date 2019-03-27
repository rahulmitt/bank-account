package com.rahul.bankaccount.service;

import com.rahul.bankaccount.model.BankAccount;
import com.rahul.bankaccount.model.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReconciliationService {

    public boolean reconcile(BankAccount account, TransactionType transactionType, BigDecimal amount) {
        if(transactionType == TransactionType.CREDIT) {
            return account.getPreviousBalance().add(amount).compareTo(account.getCurrentBalance()) == 0;
        }

        if(transactionType == TransactionType.DEBIT) {
            return account.getPreviousBalance().subtract(amount).compareTo(account.getCurrentBalance()) == 0;
        }

        return false;
    }

}
