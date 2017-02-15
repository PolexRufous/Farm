package com.farm.database.accounts;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import java.math.BigDecimal;

import static java.util.Objects.isNull;

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "ACCOUNT_BALANCE")
@Data
public class AccountBalance
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @OneToOne
  @JoinColumns({
          @JoinColumn(name = "ACCOUNT_NUMBER", referencedColumnName = "ACCOUNT_NUMBER")
  })
  @NonNull
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
