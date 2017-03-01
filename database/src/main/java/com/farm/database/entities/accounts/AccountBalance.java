package com.farm.database.entities.accounts;

import com.farm.database.entities.FarmEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

import static java.util.Objects.isNull;

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "ACCOUNT_BALANCE")
@Data
public class AccountBalance implements FarmEntity, Serializable
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @OneToOne
  @JoinColumn(name = "ACCOUNT_NUMBER", referencedColumnName = "ACCOUNT_NUMBER")
  @NotNull
  @Valid
  private Account account;

  @Column(name = "BALANCE_AMOUNT", nullable = false)
  private BigDecimal balanceAmount;

  @PrePersist
  private void setMinAmount(){
    if (isNull(balanceAmount)){
      balanceAmount = new BigDecimal("0.0");
    }
  }
}