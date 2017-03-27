package com.farm.database.entities.operations;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.personality.PartnerEntity;
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
public class OperationEntity implements FarmEntity, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Past
    @Column(name = "DATE")
    private Date date;

    @Column(name = "AMOUNT", nullable = false)
    @DecimalMin(value = "0.0")
    @NotNull
    private BigDecimal amount;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "PARTNER_ID", nullable = false, referencedColumnName = "ID")
    private PartnerEntity partnerEntity;

    @Column(name = "OPERATION_TYPE")
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
}
