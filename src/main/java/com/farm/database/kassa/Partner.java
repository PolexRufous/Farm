package com.farm.database.kassa;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "PARTNER")
@Data
public class Partner
{
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "PARTNER_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private PartnerType partnerType;



}
