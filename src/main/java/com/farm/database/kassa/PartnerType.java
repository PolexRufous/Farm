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
public enum PartnerType
{
  //TODO bind description with property keys for i18n
  WORKER("Worker"),
  SELLER("Seller"),
  BUYER("Buyer");

  private String description;
}
