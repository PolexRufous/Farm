package com.farm.executors.operations;

import com.farm.database.entities.operations.Operation;

@FunctionalInterface
public interface OperationExecutor {
    OperationExecutionResult execute(Operation operation);
}
