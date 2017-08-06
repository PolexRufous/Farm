package com.farm.database.entities.personality;

import com.farm.database.entities.FarmEntity;
import com.farm.environment.configuration.security.Authority;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER")
@Accessors(chain = true)
@Data
public class User implements FarmEntity, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN", nullable = false, unique = true)
    @NotNull
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    @NotNull
    private String password;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Authority> authorities;

    @Column(name = "ACCOUNT_EXPIRATION_DATE", nullable = false)
    @NotNull
    private LocalDateTime accountExpirationDate;

    @Column(name = "PASSWORD_EXPIRATION_DATE", nullable = false)
    @NotNull
    private LocalDateTime passwordExpirationDate;

    @Column(name = "LOCKED", nullable = false)
    @NotNull
    private Boolean locked;

    @Column(name = "ENABLED", nullable = false)
    @NotNull
    private Boolean enabled;
}
