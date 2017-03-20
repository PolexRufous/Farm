package com.farm.database.entities.operations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional
public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> findByDate(Date date);
    @Override
    List<Operation> findAll();
}
