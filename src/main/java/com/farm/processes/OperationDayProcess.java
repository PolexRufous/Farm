package com.farm.processes;

import com.farm.database.operations.OperationDay;
import com.farm.database.operations.OperationDayRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;

import static java.util.Objects.isNull;


@Service
@Transactional
public class OperationDayProcess {

    @Resource
    private OperationDayRepository operationDayRepository;

    public OperationDay findOrCreateToday(){
        Date today = Date.valueOf(LocalDate.now());
        return createNewIfNull(
                operationDayRepository.findByDate(today), today);
    }

    public OperationDay findOrCreateByDate(Date date){
        return createNewIfNull(
                operationDayRepository.findByDate(date), date);
    }

    private OperationDay createNewIfNull(OperationDay operationDay, Date date){
        if (isNull(operationDay)){
            operationDay = new OperationDay();
            operationDay.setDate(date);
            operationDay = operationDayRepository.save(operationDay);
        }
        return operationDay;
    }

    public OperationDay save(OperationDay operationDay){
        return operationDayRepository.save(operationDay);
    }

    public OperationDay findById(Long id){
        return operationDayRepository.findOne(id);
    }


}
