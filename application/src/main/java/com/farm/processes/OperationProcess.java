package com.farm.processes;

import com.farm.database.entities.operations.OperationEntity;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.executors.operations.OperationResult;
import com.farm.executors.operations.Operation;
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

    public OperationResult save(@Valid OperationEntity operationEntity) {
        Operation executor = appContext.getBean(operationEntity.getOperationType().getExecutorClass());
        OperationResult executionResult = executor.execute(operationEntity);
        if (executionResult.hasNoErrors())
            executionResult.setResult(operationRepository.save(operationEntity));
        return executionResult;
    }

    public void fillOperation(OperationEntity operationEntity) {
        operationEntity.setPartnerEntity(partnerProcess.findById(operationEntity.getPartnerEntity().getId()));
    }

    public List<OperationEntity> findAll() {
        return operationRepository.findAll();
    }
}
