package com.farm.executors.operations;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountType;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.personality.Partner;
import com.farm.executors.validators.OperationSufficientFundsValidator;
import com.farm.processes.AccountProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaySallaryCashUaOperationExecutor implements OperationExecutor {
    private static final AccountType accountTypeFrom = AccountType.MONEY_CASH_UA;
    private static final AccountType accountTypeTo = AccountType.CALCULATIONS_WORKER_SALARY;

    private AccountProcess accountProcess;
    private OperationSufficientFundsValidator operationSufficientFundsValidator;

    @Autowired
    public PaySallaryCashUaOperationExecutor(AccountProcess accountProcess, OperationSufficientFundsValidator operationSufficientFundsValidator) {
        this.accountProcess = accountProcess;
        this.operationSufficientFundsValidator = operationSufficientFundsValidator;
    }

    @Override
    public OperationExecutionResult execute(Operation operation) {
        OperationExecutionResult executionResult = new OperationExecutionResult();

        Account accountFrom = accountProcess.findOrCreateByType(accountTypeFrom, Partner.getFarm());
        executionResult.setErrors(operationSufficientFundsValidator.validate(accountFrom, operation.getAmount()));

        if (executionResult.hasNoErrors())
            transferFunds(accountFrom, operation);
        return executionResult;
    }

    private void transferFunds(Account accountFrom, Operation operation) {
        BigDecimal balanceAmountFrom = accountFrom.getBalance();

        Account accountTo = accountProcess.findOrCreateByType(accountTypeTo, operation.getPartner());
        BigDecimal balanceAmountTo = accountTo.getBalance();

        accountFrom.setBalance(balanceAmountFrom.subtract(operation.getAmount()));
        accountTo.setBalance(balanceAmountTo.add(operation.getAmount()));

        accountProcess.saveAccount(accountTo);
        accountProcess.saveAccount(accountFrom);
    }
}