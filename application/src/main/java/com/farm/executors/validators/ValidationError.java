package com.farm.executors.validators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ValidationError {

    INSUFFICIENT_FUNDS("validation.error.balance", "validation.error.insufficientFunds"),
    FACT_COMMIT_DATE("validation.error.factCommitDate","validation.error.fact.commit.date"),
    ID_IS_MISSING("validation.error.id","validation.error.id.missing"),
    ID_IS_COMMITED("validation.error.id","validation.error.id.commited");

    private String shortName;
    private String errorText;
}
