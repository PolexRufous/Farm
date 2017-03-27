package com.farm.database.utilits;

import com.farm.database.entities.accounts.AccountEntity;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter
public class OperationSufficientFundsValidator {

    public Map<String, String> validate(AccountEntity accountEntity, BigDecimal amount) {
        List<ValidationError> errors  = new ArrayList<>();
        BigDecimal balance = accountEntity.getBalance();
        BigDecimal rest = balance.subtract(amount);
        Boolean sufficient = rest.compareTo(BigDecimal.ZERO) >= 0;
        if (!sufficient)
            errors.add(ValidationError.INSUFFICIENT_FUNDS);

        return getErrorsMap(errors);
    }

    private Map<String, String> getErrorsMap(List<ValidationError> errors) {
        return errors.stream()
                .collect(Collectors.toMap(
                        ValidationError::getShortName,
                        ValidationError::getErrorText
                ));
    }
}
