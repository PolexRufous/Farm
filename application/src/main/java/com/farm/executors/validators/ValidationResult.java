package com.farm.executors.validators;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Getter
public class ValidationResult {
    private List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return isNotEmpty(errors);
    }

    public Map<String, String> getErrorsMap() {
        return errors.stream()
                .collect(Collectors.toMap(
                        ValidationError::getShortName,
                        ValidationError::getErrorText
                ));
    }

    public void addAll(ValidationResult otherValidationResults) {
        errors.addAll(otherValidationResults.getErrors());
    }
}
