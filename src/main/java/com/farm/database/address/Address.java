package com.farm.database.address;

import com.farm.database.FarmEntity;
import com.farm.database.personality.Partner;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ADDRESS")
@Data
public class Address implements FarmEntity, Serializable
{

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column
    private String town;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Partner> partner;

}
