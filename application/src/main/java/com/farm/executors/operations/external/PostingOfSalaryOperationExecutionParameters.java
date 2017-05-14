package com.farm.executors.operations.external;

import com.farm.database.entities.accounts.AccountType;
import org.springframework.stereotype.Service;

@Service
public class PostingOfSalaryOperationExecutionParameters implements ExternalOperationParameters {
    public boolean isCheckFundsNeeded(){
        return false;
    }

    @Override
    public AccountType getAccountTypeTo() {
        return AccountType.EXPENSES_SALARY_BASE;
    }

    @Override
    public String getOperationMessageKey() {
        return "posting.description";
    }

    @Override
    public String getAccountNumberTo() {
        return null;
    }
}
