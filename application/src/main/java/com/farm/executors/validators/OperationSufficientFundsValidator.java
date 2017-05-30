package com.farm.executors.validators;

import com.farm.database.entities.accounts.Account;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OperationSufficientFundsValidator {

    public ValidationResult validate(Account account, BigDecimal amount) {
        ValidationResult validationResult  = new ValidationResult();
        BigDecimal balance = account.getBalance();
        BigDecimal rest = balance.subtract(amount);
        Boolean sufficient = rest.compareTo(BigDecimal.ZERO) >= 0;
        if (!sufficient)
            validationResult.addError(ValidationError.INSUFFICIENT_FUNDS);

        return validationResult;
    }
}
