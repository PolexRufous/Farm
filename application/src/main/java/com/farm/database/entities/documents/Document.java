package com.farm.database.entities.documents;

import com.farm.database.entities.FarmEntity;
import com.farm.database.entities.operations.Operation;
import com.farm.database.entities.personality.Partner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "DOCUMENT")
@Accessors(chain = true)
@Data
public class Document implements FarmEntity{

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

    @Column(name = "AMOUNT", nullable = false)
    @DecimalMin(value = "0.0")
    @NotNull
    private BigDecimal amount;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "SUBJECT")
    private String subject;

    @ManyToOne(optional = false, targetEntity = Partner.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_ID", nullable = false, referencedColumnName = "ID")
    @JsonIgnore
    private Partner partner;

    @NotNull
    @Column(name = "PARTNER_ID", insertable = false, updatable = false)
    private Long partnerId;

    @OneToMany(targetEntity = Operation.class)
    @JoinColumn(name = "DOCUMENT", referencedColumnName = "ID")
    @JsonIgnore
    private List<Operation> operations;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Transient
    private String startAccountNumber;

    @Transient
    private String partnerName;

    @PostLoad
    private void populatePartnerName(){
        partnerName = partner.getName();
    }
}
