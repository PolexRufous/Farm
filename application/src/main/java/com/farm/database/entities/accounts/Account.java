package com.farm.database.entities.accounts;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.personality.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
@Data
public class Account implements FarmEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne(optional = false, targetEntity = Partner.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_ID", nullable = false, referencedColumnName = "ID")
    @Valid
    @JsonIgnore
    private Partner partner;

    @NotNull
    @Column(name = "PARTNER_ID", insertable = false, updatable = false)
    private Long partnerId;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @NotNull
    @Column(name = "SUBJECT")
    private String subject;

    @PrePersist
    private void setZeroBalanceIfNull() {
        balance = ObjectUtils.defaultIfNull(balance, BigDecimal.ZERO);
    }
}
