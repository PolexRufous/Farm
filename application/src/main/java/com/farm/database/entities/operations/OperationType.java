package com.farm.database.entities.operations;

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
  SALARY("operation.type.salary.short", "operation.type.salary.desc"),
  SELL_PRODUCTION("operation.type.sell.production.short", "operation.type.sell.production.desc"),
  SELL_ACTIVES("operation.type.sell.actives.short", "operation.type.sell.actives.desc"),
  BUY_ACTIVES("operation.type.buy.actives.short", "operation.type.buy.actives.desc"),
  CORRECT_MISTAKE("operation.type.correct.mistake.short", "operation.type.correct.mistake.desc");

  private String shortDescription;
  private String description;
}
