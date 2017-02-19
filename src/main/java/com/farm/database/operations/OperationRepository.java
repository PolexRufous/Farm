package com.farm.database.operations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface OperationRepository extends CrudRepository<Operation, Long> {
}
