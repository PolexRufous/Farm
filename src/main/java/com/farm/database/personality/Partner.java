package com.farm.database.personality;

import com.farm.database.FarmEntity;
import com.farm.database.address.Address;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Farm project. 2017
 * Description:
 */
@Entity
@Table(name = "PARTNER")
@Data
public class Partner implements FarmEntity
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

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Address> addresses;

}
