package com.farm.executors.operations;

import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.operations.OperationType;

public interface OperationExecutor {
    OperationExecutionResult execute(Operation operation);
    OperationExecutionResult executeCreate(OperationType operationType, Document document, Account accountFrom);
}
