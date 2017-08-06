package com.farm.environment.configuration.security;

import com.farm.database.entities.personality.User;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

public class FarmUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private User user;

    public FarmUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return LocalDateTime.now().isBefore(user.getAccountExpirationDate());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return LocalDateTime.now().isBefore(user.getPasswordExpirationDate());
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
