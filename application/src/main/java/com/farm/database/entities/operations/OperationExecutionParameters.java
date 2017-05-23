package com.farm.database.entities.operations;

import com.farm.database.entities.accounts.AccountType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "OPERATION_EXECUTION_PARAMETERS")
@Accessors(chain = true)
@Data
public class OperationExecutionParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "OPERATION_TYPE", nullable = false)
    private OperationType operationType;

    @NotNull
    @Column(name = "CHECK_FUNDS_NEEDED", nullable = false)
    private boolean checkFundsNeeded;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE_TO", nullable = false)
    private AccountType accountTypeTo;

    @NotNull
    @Column(name = "OPERATION_MESSAGE_KEY", nullable = false)
    private String operationMessageKey;

    @Column(name = "ACCOUNT_NUMBER_TO")
    private String accountNumberTo;
}
