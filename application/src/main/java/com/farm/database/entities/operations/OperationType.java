package com.farm.database.entities.operations;

import com.farm.executors.operations.OperationExecutor;
import com.farm.executors.operations.PaySallaryCashUaOperationExecutor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationType
{
  PAY_SALARY_CASH_UA("operation.type.salary.short", "operation.type.salary.desc", PaySallaryCashUaOperationExecutor.class),

  BUY_PROD_FUEL_CASH_UA("", "", null),
  BUY_RAW_MATERIALS_CASH_UA("", "", null),
  BUY_MBP_CASH_UA("", "", null),

  BUY_VEHICLE_CASH("", "", null),
  BUY_TOOLS_CASH("", "", null),

  SELL_FINISHED_PRODUCTION_CASH("", "", null),
  SELL_ALIVE_ANIMALS("", "", null),

  SELL_ACTIVES_VEHICLES("", "", null),

  RECEIVE_PROD_FUEL_UA("", "", null),
  RECEIVE_RAW_MATERIALS_UA("", "", null),
  RECEIVE_MBP_UA("", "", null),
  RECEIVE_VEHICLE_UA("", "", null),
  RECEIVE_TOOLS_UA("", "", null),

  SHIP_ACTIVES_VEHICLE("", "", null),
  SHIP_FINISHED_PRODUCTION("", "", null),
  SHIP_ALIVE_ANIMALS("", "", null);

  private String shortDescription;
  private String description;
  private Class<? extends OperationExecutor> executorClass;
}
