package com.rahul.bankaccount.service;

import com.rahul.bankaccount.model.BankAccount;
import com.rahul.bankaccount.model.AmountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidationService {

    public boolean isValid(AmountDto dto) {
        if(dto == null || dto.getAmount() == null) return false;
            return dto.getAmount().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasSufficientBalance(BankAccount account, BigDecimal debitAmount) {
        if (account == null) return false;
        if (debitAmount == null) return true;
        return account.getCurrentBalance().compareTo(debitAmount) >= 0;
    }
}
