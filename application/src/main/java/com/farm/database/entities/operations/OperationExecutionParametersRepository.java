package com.farm.database.entities.operations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OperationExecutionParametersRepository extends CrudRepository<OperationExecutionParameters, Long> {
    OperationExecutionParameters findByOperationType(OperationType operationType);
}
