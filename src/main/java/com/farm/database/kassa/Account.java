package com.farm.database.kassa;

import lombok.Data;

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

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "ACCOUNT")
@Data
public class Account
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @Column(name = "ACCOUNT_NUMBER", unique = true)
  private String accountNumber;

  @Column(name = "ACCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private AccountType accountType;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "PARTNER_ID", referencedColumnName = "ID")
  })
  private Partner partner;

}
