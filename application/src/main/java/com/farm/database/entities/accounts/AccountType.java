package com.farm.database.entities.accounts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AccountType
{
  CASH_UA("301", "account.cash.department.national"),
  BANK_ACCOUNT_UA("311", "account.bank.account.national"),
  SALARY("661", "account.salary"),
  RECEIVABLES_FROM_CUSTOMERS("361", "account.receivables.from.customers");

  private String accountCode;
  private String description;

}
