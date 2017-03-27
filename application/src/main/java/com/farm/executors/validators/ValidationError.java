package com.farm.executors.validators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ValidationError {

    INSUFFICIENT_FUNDS("validation.error.balance", "validation.error.insufficientFunds");

    private String shortName;
    private String errorText;
}
