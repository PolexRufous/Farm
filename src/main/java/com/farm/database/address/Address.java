package com.farm.database.address;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Lex on 15.02.2017.
 */
@Entity
@Table(name = "ADDRESS")
@Data
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column
    private String town;

}
