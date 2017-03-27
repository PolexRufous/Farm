package com.farm.database.entities.address;

import com.farm.database.entities.FarmEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
@Data
public class AddressEntity implements FarmEntity, Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column
    private String town;

    public void update(AddressEntity donorAddressEntity) {
        this.town = donorAddressEntity.town;
    }
}