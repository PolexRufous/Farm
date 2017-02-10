package com.farm.database.kassa;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Farm project. 2017
 * Description: Accounts plan
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AccountType
{
  CASH(30, "central.cash.department"),
  BANK_ACCOUNT(31, "main.bank.account");

  private int accountCode;
  private String description;

}
