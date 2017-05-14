package com.farm.executors.documents.external;

import com.farm.database.entities.operations.OperationType;

public interface ExternalDocumentExecutorParameters {
    OperationType getPaymentOperationType();
    OperationType getReceiveOperationType();
    boolean isStartAccountNeeded();
}
