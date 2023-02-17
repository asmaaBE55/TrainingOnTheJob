package com.tacos.tacocloud_nosql.service;

import com.tacos.tacocloud_nosql.model.User;
import com.tacos.tacocloud_nosql.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService {
    @Bean
    public com.tacos.tacocloud_nosql.reposecurity.UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }
}
