package com.farm.environment.configuration.security;

import com.farm.database.entities.personality.User;
import com.farm.database.entities.personality.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DaoUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public DaoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null)
            throw new UsernameNotFoundException("user "+ login + " not found");
        return new FarmUserDetails(user);
    }
}
