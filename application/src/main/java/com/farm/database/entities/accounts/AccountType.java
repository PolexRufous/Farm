package com.farm.database.entities.accounts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AccountType
{
  //1 - main actives
  MAIN_LAND("101", "account.101"),
  MAIN_BUILDINGS("103", "account.103"),
  MAIN_EQUIPMENTS("104", "account.104"),
  MAIN_VEHICLES("105", "account.105"),
  MAIN_TOOLS("106", "account.106"),
  MAIN_ANIMALS("107", "account.107"),
  MAIN_PLANTATIONS("108", "account.108"),
  MAIN_OTHER("109", "account.109"),

  //2 - reserves
  RESERVES_PROD_RAW_MATERIALS("201", "account.201"),
  RESERVES_PROD_FUEL("203", "account.203"),
  RESERVES_PROD_CONTAINERS("204", "account.204"),
  RESERVES_PROD_CONSTRUCTION_MATERIALS("205", "account.205"),
  RESERVES_PROD_OTHER("209", "account.209"),
  RESERVES_ALIVE_PLANTS("211", "account.211"),
  RESERVES_ALIVE_ANIMALS("212", "account.212"),
  RESERVES_MBP("220", "account.220"),
  RESERVES_FINISHED_PRODUCTS("260", "account.260"),
  RESERVES_GOODS_STOCK("281", "account.281"),
  RESERVES_GOODS_MARGIN("285", "account.285"),
  RESERVES_OTHER("290", "account.290"),

  //3 - money
  MONEY_CASH_UA("301", "account.301"),
  MONEY_BANK_ACCOUNT_UA("311", "account.311"),
  MONEY_RECEIVABLES_FROM_CUSTOMERS_UA("361", "account.361"),
  MONEY_RECEIVABLES_FROM_CUSTOMERS_OTHER("362", "account.362"),
  MONEY_PREPAID_EXPENSES("371", "account.371"),
  MONEY_FUTURE_EXPENSES("390", "account.390"),

  //4 - equity
  EQUITY_BASE("400", "account.400"),
  EQUITY_ADDITIONAL_OWN("422", "account.422"),
  EQUITY_ADDITIONAL_OTHER("425", "account.425"),
  EQUITY_PROFIT("441", "account.441"),
  EQUITY_LESION("442", "account.442"),

  //5 - long-term liabilities
  LT_LIABILITY_BANK_UA("501", "account.501"),
  LT_LIABILITY_OTHER_UA("505", "account.505"),

  //6 - short-term liability and calculations
  ST_LIABILITY_BANK_UA("601", "account.601"),
  CALCULATION_PROVIDERS_UA("631", "account.631"),
  CALCULATIONS_PROVIDERS_OTH("632", "account.632"),
  CALCULATIONS_TAXES_BASE("641", "account.641"),
  CALCULATIONS_TAXES_OTHER("642", "account.642"),
  CALCULATIONS_TAXES_LIABILITIES("643", "account.643"),
  CALCULATIONS_TAXES_CREDIT("644", "account.644"),
  CALCULATIONS_SOCIAL_INSURANCE_GOV("651", "account.651"),
  CALCULATIONS_SOCIAL_INSURANCE_OTH("652", "account.652"),
  CALCULATIONS_WORKER_SALARY("661", "account.661"),
  CALCULATIONS_WORKER_OTHER("663", "account.663"),
  CALCULATIONS_OWNER_PROFIT("671", "account.671"),
  CALCULATIONS_OWNER_OTHER("672", "account.672"),
  CALCULATIONS_PREPAID_EXPENSES("681", "account.681"),
  CALCULATIONS_INTEREST("684", "account.684"),
  CALCULATIONS_OTHER_CREDITORS("685", "account.685"),
  CALCULATIONS_FUTURE_PROFIT("690", "account.690"),

  //7 - profit adn results
  PROFIT_SELL_PRODUCTION("701", "account.701"),
  PROFIT_SELL_GOODS("702", "account.702"),
  PROFIT_SELL_SERVICES("703", "account.703"),
  PROFIT_DEDUCTION("704", "account.704"),
  PROFIT_OPERATIONS_OTHER("719", "account.719"),
  PROFIT_OTHER("746", "account.746"),
  RESULT_OPERATIONS("791", "account.791"),
  RESULT_FINANCIAL("792", "account.792"),
  RESULT_OTHER("793", "account.793"),

  //8 - expenses
  EXPENSES_RAW_MATERIALS("801", "account.801"),
  EXPENSES_FUEL("803", "account.803"),
  EXPENSES_CONTAINERS("804", "account.804"),
  EXPENSES_BUILDING_MATERIALS("805", "account.805"),
  EXPENSES_GOODS("808", "account.808"),
  EXPENSES_OTHER_MATERIALS("809", "account.809"),
  EXPENSES_SALARY_BASE("811", "account.811"),
  EXPENSES_SALARY_PRIZE("812", "account.812"),
  EXPENSES_SALARY_OTHER("816", "account.816"),
  EXPENSES_DEPRECIATION_BASE("831", "account.831"),
  EXPENSES_DEPRECIATION_OTHER("832", "account.832"),
  EXPENSES_AMORTIZATION("833", "account.833"),
  EXPENSES_OTHER_OPERATIONS("840", "account.840"),
  EXPENSES_OTHER("850", "account.850"),

  //9 - activity expenses
  ACTIVITY_EXPENSES_PRODUCTION_COST_PRISE("901", "account.901"),
  ACTIVITY_EXPENSES_GOODS_COST_PRICE("902", "account.902"),
  ACTIVITY_EXPENSES_SERVICES_COST_PRICE("903", "account.903"),
  ACTIVITY_EXPENSES_INSURANCE_PAYMENTS("904", "account.904"),
  ACTIVITY_EXPENSES_GLOBAL_PRODUCTION("910", "account.910"),
  ACTIVITY_EXPENSES_ADMINISTRATION("920", "account.920"),
  ACTIVITY_EXPENSES_SALES_COST("930", "account.930"),
  ACTIVITY_EXPENSES_OTHER_OPERATIONS("949", "account.949"),
  ACTIVITY_EXPENSES_INTERESTS("951", "account.951"),
  ACTIVITY_EXPENSES_OTHER_FINANCIAL("952", "account.952"),
  ACTIVITY_EXPENSES_OTHER("977", "account.977"),
  ACTIVITY_EXPENSES_PROFIT_TAX("981", "account.981"),

  //0 - off-balance
  OFF_BALANCE_BLANKS("080", "account.080");

  private String accountCode;
  private String description;

}
