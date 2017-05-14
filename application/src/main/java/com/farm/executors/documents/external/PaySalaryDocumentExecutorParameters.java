package com.farm.executors.documents.external;

import com.farm.database.entities.operations.OperationType;
import org.springframework.stereotype.Service;

@Service
public class PaySalaryDocumentExecutorParameters implements ExternalDocumentExecutorParameters {

    private static final OperationType PAYMENT_OPERATION_TYPE = OperationType.PAY_SALARY_CASH_UA;
    private static final OperationType RECEIVE_OPERATION_TYPE = OperationType.POSTING_OF_SALARY;

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
