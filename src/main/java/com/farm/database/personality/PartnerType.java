package com.farm.database.personality;

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
  WORKER("partner.worker"),
  SELLER("partner.seller"),
  BUYER("partner.buyer");

  private String description;
}
