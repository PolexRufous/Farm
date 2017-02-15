package com.farm.database.kassa;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Farm project. 2017
 * Description: Base entity for operation day
 */
@Entity
@Table(name = "KASSA_OPERATION_DAY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "id",
                "dateOfDay"
        })
})
@Data
class OperationDay {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE")
    @Past
    @NotNull
    private Date date;

    @OneToMany(mappedBy = "operationDay", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Operation> operations;
}
