package com.farm.database.utilits;

import com.farm.database.entities.accounts.AccountBalance;
import com.farm.database.entities.operations.Operation;
import com.farm.processes.AccountBalanceProcess;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
@Scope("prototype")
@Getter
public class OperationValidator {
    private List<ValidationError> errors;
    private AccountBalanceProcess accountBalanceProcess;

    public OperationValidator(AccountBalanceProcess accountBalanceProcess) {
        this.errors = new ArrayList<>();
        this.accountBalanceProcess = accountBalanceProcess;
    }

    public void validate(Operation operation) {
        AccountBalance accountBalance = accountBalanceProcess.findOrCreateByAccount(operation.getAccountFrom());
        BigDecimal balance = accountBalance.getBalanceAmount();
        BigDecimal rest = balance.subtract(operation.getAmount());
        Boolean sufficient = rest.compareTo(BigDecimal.ZERO) >= 0;
        if (!sufficient)
            errors.add(ValidationError.INSUFFICIENT_FUNDS);
    }

    public boolean hasErrors() {
        return isNotEmpty(errors);
    }

    public Map<String, String> getErrors() {
        return errors.stream()
                .collect(Collectors.toMap(
                        ValidationError::getShortName,
                        ValidationError::getErrorText
                ));
    }
}
