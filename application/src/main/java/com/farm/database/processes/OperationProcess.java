package com.farm.database.processes;

import com.farm.database.entities.accounts.AccountBalance;
import com.farm.database.entities.accounts.AccountBalanceRepository;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.database.utilits.OperationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OperationProcess {
    private OperationRepository operationRepository;
    private AccountBalanceRepository accountBalanceRepository;

    @Autowired
    public OperationProcess(OperationRepository operationRepository,
                            AccountBalanceRepository accountBalanceRepository,
                            OperationValidator operationValidator) {
        this.operationRepository = operationRepository;
        this.accountBalanceRepository = accountBalanceRepository;
    }

    public Operation save(@Valid Operation operation){
        transferFunds(operation);
        return operationRepository.save(operation);
    }

    private void transferFunds(Operation operation) {
        AccountBalance accountBalanceFrom = accountBalanceRepository.findByAccount(operation.getAccountFrom());
        BigDecimal balanceAmountFrom = accountBalanceFrom.getBalanceAmount();
        AccountBalance accountBalanceTo = accountBalanceRepository.findByAccount(operation.getAccountTo());
        BigDecimal balanceAmountTo = accountBalanceTo.getBalanceAmount();
        accountBalanceFrom.setBalanceAmount(balanceAmountFrom.subtract(operation.getAmount()));
        accountBalanceTo.setBalanceAmount(balanceAmountTo.add(operation.getAmount()));
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }
}
