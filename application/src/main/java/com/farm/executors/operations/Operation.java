package com.farm.executors.operations;

import com.farm.database.entities.operations.OperationEntity;

public interface Operation {
    OperationResult execute(OperationEntity operationEntity);
}
