package com.farm.processes;

import com.farm.database.accounts.Account;
import com.farm.database.accounts.AccountBalance;
import com.farm.database.accounts.AccountBalanceRepository;
import com.farm.database.operations.Operation;
import com.farm.database.operations.OperationRepository;
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

    public Operation save(@Valid Operation operation){
        Account accountFrom = operation.getAccountFrom();
        Account accountTo = operation.getAccountTo();

        //AccountBalance accountFromBalance =

        return operationRepository.save(operation);
    }
}
