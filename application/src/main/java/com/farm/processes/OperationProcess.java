package com.farm.processes;

import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.executors.operations.OperationExecutor;
import com.farm.executors.operations.external.ExternalOperationExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OperationProcess {
    @NonNull
    private OperationRepository operationRepository;
    @NonNull
    private PartnerProcess partnerProcess;
    @NonNull
    private ExternalOperationExecutor externalOperationExecutor;

    public void fillOperationPartner(Operation operation) {
        Long partnerId = operation.getPartnerId();
        if (Objects.nonNull(partnerId)){
            operation.setPartner(partnerProcess.findById(partnerId));
        }
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public List<Operation> findByPartnerId(Long partnerId) {
        return operationRepository.findAllByPartnerId(partnerId);
    }

    public Operation findById(Long id) {
        return operationRepository.findOne(id);
    }

    public OperationExecutionResult execute(@Valid Operation operation) {
        return externalOperationExecutor.execute(operation);
    }

    public List<Operation> findByDocumentId(Long documentId){
        return Objects.nonNull(documentId) ?
                operationRepository.findAllByDocumentId(documentId) :
                Collections.emptyList();
    }
}
