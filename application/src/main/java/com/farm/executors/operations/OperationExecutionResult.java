package com.farm.executors.operations;

import com.farm.database.entities.operations.Operation;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

@Data
public class OperationExecutionResult {
    private Map<String, String> errors;
    private Operation result;

    public boolean hasNoErrors() {
        return MapUtils.isEmpty(errors);
    }
}
