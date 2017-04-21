package com.farm.processes;

import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.executors.operations.OperationExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public class OperationProcess {
    private OperationRepository operationRepository;
    private ApplicationContext appContext;
    private PartnerProcess partnerProcess;

    @Autowired
    public OperationProcess(OperationRepository operationRepository, ApplicationContext appContext, PartnerProcess partnerProcess) {
        this.operationRepository = operationRepository;
        this.appContext = appContext;
        this.partnerProcess = partnerProcess;
    }

    public OperationExecutionResult save(@Valid Operation operation) {
        OperationExecutor executor = appContext.getBean(operation.getOperationType().getExecutorClass());
        OperationExecutionResult executionResult = executor.execute(operation);
        if (executionResult.hasNoErrors())
            executionResult.setResult(operationRepository.save(operation));
        return executionResult;
    }

    public void fillOperation(Operation operation) {
        operation.setPartner(partnerProcess.findById(operation.getPartner().getId()));
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public List<Operation> findByPartnerId(Long partnerId) {
        return operationRepository.findAllByPartnerId(partnerId);
    }
}
