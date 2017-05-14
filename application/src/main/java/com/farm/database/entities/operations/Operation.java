package com.farm.database.entities.operations;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.accounts.Account;
import com.farm.database.entities.documents.Document;
import com.farm.database.entities.personality.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "OPERATION")
@Getter
@Accessors(chain = true)
@Setter
public class Operation implements FarmEntity, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Past
    @Column(name = "ENTER_DATE")
    private Date enterDate;

    @NotNull
    @Column(name = "EXPECTED_COMMIT_DATE")
    private Date expectedCommitDate;

    @Column(name = "FACT_COMMIT_DATE")
    private Date factCommitDate;

    @Column(name = "AMOUNT", nullable = false)
    @DecimalMin(value = "0.0")
    @NotNull
    private BigDecimal amount;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "ACCOUNT_FROM", referencedColumnName = "ACCOUNT_NUMBER")
    @JsonIgnore
    private Account accountFrom;

    @Column(name = "ACCOUNT_FROM", insertable = false, updatable = false)
    private String accountFromString;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "ACCOUNT_TO", referencedColumnName = "ACCOUNT_NUMBER")
    @JsonIgnore
    private Account accountTo;

    @Column(name = "ACCOUNT_TO", insertable = false, updatable = false)
    private String accountToString;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "PARTNER_ID", nullable = false, referencedColumnName = "ID")
    @JsonIgnore
    private Partner partner;

    @Column(name = "PARTNER_ID", updatable = false, insertable = false)
    private Long partnerId;

    @Transient
    private String partnerName;

    @Column(name = "OPERATION_TYPE")
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "DOCUMENT", referencedColumnName = "ID")
    @JsonIgnore
    private Document document;

    @Column(name = "DOCUMENT", insertable = false, updatable = false)
    private Long documentId;

    @PrePersist
    private void setDescriptionIfNull(){
        description = ObjectUtils.defaultIfNull(description, "");
    }

    @PostLoad
    private void populatePartnerName(){
        partnerName = partner.getName();
    }
}
