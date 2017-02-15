package com.farm.database.kassa;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Farm project. 2017
 * Description:
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationType
{
  //TODO bind description with property keys for i18n
  SALARY("Salary", ""),
  SELL_PRODUCTION("Sell production", ""),
  SELL_ACTIVES("Sell Actives", ""),
  BUY_ACTIVES("Buy Actives", ""),
  CORRECT_MISTAKE("Correct mistake", "");

  //private int operationCode;
  private String shortDescription;
  private String description;
}
