package com.farm.executors.operations;

import com.farm.database.entities.operations.Operation;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

@Data
public class OperationExecutionResult {
    private Map<String, String> errors;
    private Operation result;

    public OperationExecutionResult(Operation operation, Map<String, String> errors){
        this.result = operation;
        this.errors = errors;
    }

    public OperationExecutionResult(){

    }

    public boolean hasNoErrors() {
        return MapUtils.isEmpty(errors);
    }
}
