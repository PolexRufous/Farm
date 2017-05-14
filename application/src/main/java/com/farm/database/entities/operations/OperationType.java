package com.farm.database.entities.operations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationType
{
  PAY_SALARY_CASH_UA("", ""),
  PAY_FOR_RAW_MATERIALS_CASH_UA("", ""),

  BUY_PROD_FUEL_CASH_UA("", ""),
  BUY_RAW_MATERIALS_CASH_UA("", ""),
  BUY_MBP_CASH_UA("", ""),

  BUY_VEHICLE_CASH("", ""),
  BUY_TOOLS_CASH("", ""),

  SELL_FINISHED_PRODUCTION_CASH("", ""),
  SELL_ALIVE_ANIMALS("", ""),

  SELL_ACTIVES_VEHICLES("", ""),

  RECEIVE_PROD_FUEL_UA("", ""),
  RECEIVE_RAW_MATERIALS_UA("", ""),
  RECEIVE_MBP_UA("", ""),
  RECEIVE_VEHICLE_UA("", ""),
  RECEIVE_TOOLS_UA("", ""),

  POSTING_OF_SALARY("", ""),

  SHIP_ACTIVES_VEHICLE("", ""),
  SHIP_FINISHED_PRODUCTION("", ""),
  SHIP_ALIVE_ANIMALS("", "");

  private String shortDescription;
  private String description;
}
