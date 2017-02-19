package com.farm.database.operations;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Transactional
public interface OperationDayRepository extends CrudRepository<OperationDay, Long>{

    OperationDay findByDate(Date date);
}
