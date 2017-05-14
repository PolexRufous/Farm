package com.farm.executors.operations.external;

import com.farm.database.entities.accounts.AccountType;

public interface ExternalOperationParameters {
    boolean isCheckFundsNeeded();
    AccountType getAccountTypeTo();
    String getOperationMessageKey();
    String getAccountNumberTo();
}
