package com.farm.database.accounts;

import com.farm.database.address.Address;
import lombok.Data;

import javax.persistence.*;
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
  @Column(name = "ID")
  private Long id;

  @Column(name = "PARTNER_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  private PartnerType partnerType;

  @Column(name = "NAME", nullable = false)
  @NotNull
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @ManyToMany
  private Address address;

}
