package com.farm.database.processes;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.accounts.AccountBalanceRepository;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;

@Service
@Transactional
public class OperationProcess {

    @Resource
    private OperationRepository operationRepository;
    @Resource
    private AccountBalanceRepository accountBalanceRepository;

    //TODO implement method
    public Operation save(@Valid Operation operation){
        Account accountFrom = operation.getAccountFrom();
        Account accountTo = operation.getAccountTo();

        return operationRepository.save(operation);
    }
}
