package com.farm.executors.operations.external;

import com.farm.database.entities.accounts.AccountType;
import org.springframework.stereotype.Service;

@Service
public class ReceiveRawMaterialsOperationExecutionParameters implements ExternalOperationParameters {
    public boolean isCheckFundsNeeded(){
        return false;
    }

    @Override
    public AccountType getAccountTypeTo() {
        return AccountType.RESERVES_PROD_RAW_MATERIALS;
    }

    @Override
    public String getOperationMessageKey() {
        return "receive.description";
    }

    @Override
    public String getAccountNumberTo() {
        return null;
    }
}
