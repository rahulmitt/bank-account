package com.rahul.bankaccount.service;

import com.rahul.bankaccount.exception.BankAccountException;
import com.rahul.bankaccount.dao.BankAccountDao;
import com.rahul.bankaccount.model.AmountDto;
import com.rahul.bankaccount.model.BankAccount;
import com.rahul.bankaccount.model.BankTransaction;
import com.rahul.bankaccount.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountDao bankAccountDao;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ReconciliationService reconciliationService;

    public BankTransaction deposit(String accountId, AmountDto amount) throws BankAccountException {
        if (!bankAccountDao.accountExists(accountId))
            throw new BankAccountException(String.format("Account %s not found", accountId));

        if (!validationService.isValid(amount)) {
            String amt = amount == null || amount.getAmount() == null ? "null" : amount.getAmount().toPlainString();
            throw new BankAccountException(
                    String.format("Transaction declined for accountId %s :: Invalid amount %s", accountId, amt)
            );
        }

        BankAccount account = bankAccountDao.getBankAccount(accountId);
        BankTransaction transaction = bankAccountDao.deposit(accountId, amount.getAmount());
        boolean success = reconciliationService.reconcile(account, TransactionType.CREDIT, amount.getAmount());
        if (!success) {
            transaction.setSuccessful(false);
            account.revertLastTransaction();
        }
        return transaction;
    }

    public BankTransaction withdraw(String accountId, AmountDto amount) throws BankAccountException {
        if (!bankAccountDao.accountExists(accountId))
            throw new BankAccountException(String.format("Account %s not found", accountId));

        if (!validationService.isValid(amount)) {
            String amt = amount == null || amount.getAmount() == null ? "null" : amount.getAmount().toPlainString();
            throw new BankAccountException(
                    String.format("Transaction declined for accountId %s :: Invalid amount %s", accountId, amt)
            );
        }

        BankAccount account = bankAccountDao.getBankAccount(accountId);

        if (!validationService.hasSufficientBalance(account, amount.getAmount()))
            throw new BankAccountException(String.format("Insufficient balance in accountId %s", accountId));

        BankTransaction transaction = bankAccountDao.withdraw(accountId, amount.getAmount());
        boolean success = reconciliationService.reconcile(account, TransactionType.DEBIT, amount.getAmount());
        if (!success) {
            transaction.setSuccessful(false);
            account.revertLastTransaction();
        }
        return transaction;
    }

    public AmountDto getBalance(String accountId) throws BankAccountException {
        if (!bankAccountDao.accountExists(accountId))
            throw new BankAccountException(String.format("Account %s not found", accountId));

        return bankAccountDao.checkBalance(accountId);

    }

    public List<BankTransaction> list(String accountId) throws BankAccountException {
        if (!bankAccountDao.accountExists(accountId))
            throw new BankAccountException(String.format("Account %s not found", accountId));

        return bankAccountDao.listTransactions(accountId);
    }
}
