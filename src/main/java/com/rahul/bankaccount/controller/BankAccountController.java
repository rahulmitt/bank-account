package com.rahul.bankaccount.controller;

import com.rahul.bankaccount.exception.BankAccountException;
import com.rahul.bankaccount.model.AmountDto;
import com.rahul.bankaccount.model.BankTransaction;
import com.rahul.bankaccount.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class BankAccountController {
    Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping(
            value = "/{accountId}/credit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deposit(@PathVariable final String accountId, @RequestBody AmountDto dto) {
        logger.info("Deposit transaction initiated for accountId {} :: Amount to be credited {}", accountId, dto.getAmount());

        try {
            BankTransaction transaction = bankAccountService.deposit(accountId, dto);
            logger.info("Deposit transaction successful. {} amount credited", dto.getAmount());
            return ResponseEntity.status(HttpStatus.OK).body(transaction);

        } catch (BankAccountException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(
            value = "/{accountId}/debit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> withdraw(@PathVariable final String accountId, @RequestBody AmountDto dto) {
        logger.info("Withdrawal transaction initiated for accountId {} :: Amount to be debited {}", accountId, dto.getAmount());

        try {
            BankTransaction transaction = bankAccountService.withdraw(accountId, dto);
            logger.info("Withdrawal transaction successful. {} amount debited", dto.getAmount());
            return ResponseEntity.status(HttpStatus.OK).body(transaction);

        } catch (BankAccountException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            value = "/{accountId}/check",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> checkBalance(@PathVariable final String accountId) {
        logger.info("Checking balance for accountId {}", accountId);

        try {
            AmountDto amountDto = bankAccountService.getBalance(accountId);
            logger.info("Balance for accountId {} is {}", accountId, amountDto.getAmount());
            return ResponseEntity.status(HttpStatus.OK).body(amountDto);

        } catch (BankAccountException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(
            value = "/{accountId}/list",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> listAllTransactions(@PathVariable final String accountId) {
        logger.info("Listing all transactions for accountId {}", accountId);

        try {
            List<BankTransaction> transactions = bankAccountService.list(accountId);
            logger.info("Transactions for accountId {}: {}", accountId, transactions);
            return ResponseEntity.status(HttpStatus.OK).body(transactions);

        } catch (BankAccountException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
