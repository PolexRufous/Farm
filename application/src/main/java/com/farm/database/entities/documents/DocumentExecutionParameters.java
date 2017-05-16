package com.farm.database.entities.documents;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.operations.OperationType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOCUMENT_EXECUTION_PARAMETERS")
@Data
public class DocumentExecutionParameters implements FarmEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DOCUMENT_TYPE", nullable = false, unique = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(name = "PAYMENT_OPERATION_TYPE", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType paymentOperationType;

    @Column(name = "RECEIVE_OPERATION_TYPE", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType receiveOperationType;

    @Column(name = "START_ACCOUNT_NEEDED", nullable = false)
    @NotNull
    private boolean startAccountNeeded;

}
