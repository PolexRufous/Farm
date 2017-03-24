package com.farm.database.entities.operations;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.personality.Partner;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "OPERATION")
@Data
public class Operation implements FarmEntity, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Past
    @Column(name = "DATE")
    private Date date;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "ACCOUNT_FROM", nullable = false, referencedColumnName = "ACCOUNT_NUMBER")
    private Account accountFrom;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "ACCOUNT_TO", nullable = false, referencedColumnName = "ACCOUNT_NUMBER")
    private Account accountTo;

    @Column(name = "AMOUNT", nullable = false)
    @DecimalMin(value = "0.0")
    @NotNull
    private BigDecimal amount;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "PARTNER_ID", nullable = false, referencedColumnName = "ID")
    private Partner partner;

    @Column(name = "OPERATION_TYPE")
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
}
