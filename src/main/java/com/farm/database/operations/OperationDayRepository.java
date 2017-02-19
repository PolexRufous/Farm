package com.farm.database.operations;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface OperationDayRepository extends CrudRepository<OperationDay, Long>{

    OperationDay findByDate(Date date);
}
