package com.farm.database.utilits;

import com.farm.database.entities.FarmEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class FarmEntityValidator {

    public static Map<String, String> getValidationErrors(FarmEntity entity) {
        Set<ConstraintViolation<FarmEntity>> constrains =
                Validation
                        .buildDefaultValidatorFactory()
                        .getValidator()
                        .validate(entity);

        return constrains.stream()
                .collect(Collectors.toMap(
                        constrain -> constrain.getPropertyPath().toString(),
                        ConstraintViolation::getMessage));
    }
}
