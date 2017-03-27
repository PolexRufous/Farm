package com.farm.database.entities.personality;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.address.AddressEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PARTNER")
@Data
public class PartnerEntity implements FarmEntity, Serializable
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
  private List<AddressEntity> addressEntities;

    public static PartnerEntity getFarm() {
      PartnerEntity farm = new PartnerEntity();
      farm.setId(0L);
      return farm;
    }

    public void update(PartnerEntity partnerEntity) {
    this.setName(partnerEntity.getName());
    this.setDescription(partnerEntity.getDescription());
  }
}
