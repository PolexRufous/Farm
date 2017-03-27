package com.farm.executors.operations;

import com.farm.database.entities.accounts.AccountEntity;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.operations.OperationEntity;
import com.farm.database.entities.personality.PartnerEntity;
import com.farm.database.utilits.OperationSufficientFundsValidator;
import com.farm.processes.AccountProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaySallaryCashUaOperation implements Operation {
    private static final AccountType accountTypeFrom = AccountType.MONEY_CASH_UA;
    private static final AccountType accountTypeTo = AccountType.CALCULATIONS_WORKER_SALARY;

    private AccountProcess accountProcess;
    private OperationSufficientFundsValidator operationSufficientFundsValidator;

    @Autowired
    public PaySallaryCashUaOperation(AccountProcess accountProcess, OperationSufficientFundsValidator operationSufficientFundsValidator) {
        this.accountProcess = accountProcess;
        this.operationSufficientFundsValidator = operationSufficientFundsValidator;
    }

    @Override
    public OperationResult execute(OperationEntity operationEntity) {
        OperationResult executionResult = new OperationResult();

        AccountEntity accountEntityFrom = accountProcess.findOrCreateByType(accountTypeFrom, PartnerEntity.getFarm());
        executionResult.setErrors(operationSufficientFundsValidator.validate(accountEntityFrom, operationEntity.getAmount()));

        if (executionResult.hasNoErrors())
            transferFunds(accountEntityFrom, operationEntity);
        return executionResult;
    }

    private void transferFunds(AccountEntity accountEntityFrom, OperationEntity operationEntity) {
        BigDecimal balanceAmountFrom = accountEntityFrom.getBalance();

        AccountEntity accountEntityTo = accountProcess.findOrCreateByType(accountTypeTo, operationEntity.getPartnerEntity());
        BigDecimal balanceAmountTo = accountEntityTo.getBalance();

        accountEntityFrom.setBalance(balanceAmountFrom.subtract(operationEntity.getAmount()));
        accountEntityTo.setBalance(balanceAmountTo.add(operationEntity.getAmount()));

        accountProcess.saveAccount(accountEntityTo);
        accountProcess.saveAccount(accountEntityFrom);
    }
}