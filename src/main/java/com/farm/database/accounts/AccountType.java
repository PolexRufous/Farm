package com.farm.database.accounts;

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
  CASH_UA("301", "account.cash.department.national"),
  BANK_ACCOUNT_UA("311", "account.bank.account.national"),
  SALARY("661", "account.salary");

  private String accountCode;
  private String description;

}
