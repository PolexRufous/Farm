package com.farm.database.kassa;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table

public class OperationDay {
    @Id
    @GeneratedValue
    public Long id;
}
