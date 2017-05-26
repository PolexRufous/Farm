package com.farm.processes;

import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationRepository;
import com.farm.executors.operations.OperationExecutionResult;
import com.farm.executors.operations.OperationExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OperationProcess {
    @NonNull
    private OperationRepository operationRepository;
    @NonNull
    private PartnerProcess partnerProcess;
    @NonNull
    private OperationExecutor operationExecutor;

    public void fillOperationPartner(Operation operation) {
        Optional.ofNullable(operation)
                .map(Operation::getPartnerId)
                .map(partnerProcess::findById)
                .ifPresent(operation::setPartner);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public List<Operation> findByPartnerId(Long partnerId) {
        return Optional.ofNullable(partnerId)
                .map(operationRepository::findAllByPartnerId)
                .orElse(Collections.emptyList());
    }

    public Operation findById(Long id) {
        return Optional.ofNullable(id)
                .map(operationRepository::findOne)
                .orElse(null);
    }

    public OperationExecutionResult execute(@Valid Operation operation) {
        return Optional.ofNullable(operation)
                .map(operationExecutor::execute)
                .orElse(null);
    }

    public List<Operation> findByDocumentId(Long documentId) {
        return Optional.ofNullable(documentId)
                .map(operationRepository::findAllByDocumentId)
                .orElse(Collections.emptyList());
    }
}
