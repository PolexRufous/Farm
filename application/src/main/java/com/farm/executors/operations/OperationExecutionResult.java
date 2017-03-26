package com.farm.executors.operations;

import com.farm.database.entities.operations.Operation;
import lombok.Data;

import java.util.Map;

@Data
public class OperationExecutionResult {
    private Map<String, String> errors;
    private Operation result;
}
