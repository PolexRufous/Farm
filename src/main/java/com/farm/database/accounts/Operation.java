package com.farm.database.accounts;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "OPERATION")
@Data
public class Operation
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumns({
          @JoinColumn(name = "OPERATION_DAY_ID", nullable = false, referencedColumnName = "ID"),
          @JoinColumn(name = "OPERATION_DAY_DATE", nullable = false, referencedColumnName = "DATE")
  })
  @NotNull
  @Valid
  private OperationDay operationDay;

  @NotNull
  @Valid
  @ManyToOne(optional = false)
  @JoinColumn(name = "ACCOUNT_TO", nullable = false, referencedColumnName = "ACCOUNT_NUMBER")
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