package com.farm.executors.operations.external;

import com.farm.database.entities.accounts.AccountType;
import org.springframework.stereotype.Service;

@Service
public class PaySalaryOperationExecutionParameters implements ExternalOperationParameters {
    public boolean isCheckFundsNeeded(){
        return true;
    }

    @Override
    public AccountType getAccountTypeTo() {
        return AccountType.CALCULATIONS_WORKER_SALARY;
    }

    @Override
    public String getOperationMessageKey() {
        return "payment.description";
    }

    @Override
    public String getAccountNumberTo() {
        return null;
    }
}
