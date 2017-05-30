package com.farm.executors.validators;

import com.farm.database.entities.operations.Operation;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class OperationForExecutionValidator {
    public ValidationResult validate(Operation operation, Operation originalOperation) {
        ValidationResult validationResult  = new ValidationResult();

        if (isNull(operation.getFactCommitDate())) {
            validationResult.addError(ValidationError.FACT_COMMIT_DATE);
        }

        if (isNull(originalOperation)) {
            validationResult.addError(ValidationError.ID_IS_MISSING);
        }

        if (nonNull(originalOperation) && nonNull(originalOperation.getFactCommitDate())) {
            validationResult.addError(ValidationError.ID_IS_COMMITED);
        }

        return validationResult;
    }
}
