package com.farm.database.accounts;

import com.farm.database.FarmEntity;
import com.farm.database.personality.Partner;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "ACCOUNT")
@Data
public class Account implements FarmEntity
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
  private String accountNumber;

  @Column(name = "ACCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private AccountType accountType;

  @ManyToOne
  @Valid
  @NotNull
  @JoinColumns({
          @JoinColumn(name = "PARTNER_ID", referencedColumnName = "ID")
  })
  private Partner partner;

  @PrePersist
  private void setAccountNumber(){
    if (isNull(accountNumber)){
      accountNumber = accountType.getAccountCode().concat(partner.getId().toString());
    }
  }

}
