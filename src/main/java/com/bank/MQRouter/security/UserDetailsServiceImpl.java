package com.bank.MQRouter.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Utilisateur en dur (pour test)
        if ("admin".equals(username)) {
            return new User("admin", new BCryptPasswordEncoder().encode("admin123"), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
    }
}