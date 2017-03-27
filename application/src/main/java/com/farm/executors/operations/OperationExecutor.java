package com.farm.executors.operations;

import com.farm.database.entities.operations.Operation;

public interface OperationExecutor {
    OperationExecutionResult execute(Operation operation);
}
