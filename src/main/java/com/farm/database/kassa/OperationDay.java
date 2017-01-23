package com.farm.database.kassa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OperationDay {
    @Id
    @GeneratedValue
    public Long id;
}
