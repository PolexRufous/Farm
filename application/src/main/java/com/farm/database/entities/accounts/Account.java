package com.farm.database.entities.accounts;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.personality.Partner;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static java.util.Objects.isNull;

@Entity
@Table(name = "ACCOUNT")
@Data
public class Account implements FarmEntity, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @Valid
    @NotNull
    @JoinColumn(name = "PARTNER_ID", referencedColumnName = "ID")
    private Partner partner;

    @PrePersist
    private void setAccountNumber() {
        if (isNull(accountNumber)) {
            accountNumber = accountType.getAccountCode().concat(partner.getId().toString());
        }
    }
}
