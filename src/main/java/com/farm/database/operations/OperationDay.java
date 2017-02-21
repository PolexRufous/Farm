package com.farm.database.operations;

import com.farm.database.FarmEntity;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Farm project. 2017
 * Description: Base entity for operation day
 */
@Entity
@Table(name = "OPERATION_DAY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "id",
                "date"
        })
})
@Data
public class OperationDay implements FarmEntity, Serializable
{
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE", unique = true, nullable = false)
    @Past
    @NotNull
    private Date date;

    @Valid
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Operation> operations;
}
