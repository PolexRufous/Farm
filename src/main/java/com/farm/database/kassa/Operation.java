package com.farm.database.kassa;

import static java.util.Objects.isNull;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "ACC_OPERATION")
@Data
public class Operation
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "OPERATION_DAY_ID", nullable = false)
  @NotNull
  private OperationDay operationDay;

  @Column(name = "ACCOUNT_FROM", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private AccountType accountTypeFrom;

  @Column(name = "ACCOUNT_TO", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private AccountType accountTypeTo;

  @Column(name = "AMOUNT", nullable = false)
  @DecimalMin(value = "0.0")
  private BigDecimal amount;

  @ManyToOne
  @NotNull
  @Valid
  @JoinColumn(name = "PARTNER_ID", nullable = false)
  private Partner partner;

  @Column(name = "OPERATION_TYPE")
  @NotNull
  @Enumerated(EnumType.STRING)
  private OperationType operationType;

  @PrePersist
  private void setMinAmount(){
    if (isNull(amount)){
      amount = new BigDecimal("0.0");
    }
  }

}