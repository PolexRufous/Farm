package com.farm.executors.documents.external;

import com.farm.database.entities.operations.OperationType;
import org.springframework.stereotype.Service;

@Service
public class BuyRawMaterialsDocumentExecutorParameters implements ExternalDocumentExecutorParameters {

    private static final OperationType PAYMENT_OPERATION_TYPE = OperationType.PAY_FOR_RAW_MATERIALS_CASH_UA;
    private static final OperationType RECEIVE_OPERATION_TYPE = OperationType.RECEIVE_RAW_MATERIALS_UA;

    @Override
    public OperationType getPaymentOperationType() {
        return PAYMENT_OPERATION_TYPE;
    }

    @Override
    public OperationType getReceiveOperationType() {
        return RECEIVE_OPERATION_TYPE;
    }

    @Override
    public boolean isStartAccountNeeded() {
        return true;
    }
}
