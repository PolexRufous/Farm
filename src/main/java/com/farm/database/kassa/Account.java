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
public enum Account
{
  CASH(30, "Central cash department"),
  BANK_ACCOUNT(31, "Main bank account");

  private int accountCode;
  private String description;

}
