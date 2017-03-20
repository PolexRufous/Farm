package com.farm.database.entities.personality;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.address.Address;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PARTNER")
@Data
public class Partner implements FarmEntity, Serializable
{
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME", nullable = false, unique = true)
  @NotNull
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Address> addresses;

  public void update(Partner partner) {
    this.setName(partner.getName());
    this.setDescription(partner.getDescription());
  }
}
