package com.farm.database.entities.address;

import com.farm.database.entities.FarmEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
@Accessors(chain = true)
@Data
public class Address implements FarmEntity, Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column
    private String town;

    public Address update(Address donorAddress) {
        this.town = donorAddress.town;
        return this;
    }
}
