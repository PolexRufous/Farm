package com.farm.database.entities.accounts;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.personality.PartnerEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Entity
@Table(name = "ACCOUNT")
@Data
public class AccountEntity implements FarmEntity, Serializable {
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
    private PartnerEntity partnerEntity;

    @Column(name = "BALANCE")
    private BigDecimal balance = BigDecimal.ZERO;

    @PrePersist
    private void setAccountNumber() {
        if (isNull(accountNumber)) {
            accountNumber = accountType.getAccountCode().concat(partnerEntity.getId().toString());
        }
    }
}
