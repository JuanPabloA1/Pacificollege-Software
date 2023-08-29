package com.application.main.features.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.main.features.auth.Auth;
import com.application.main.features.auth.AuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AuthRepository authRepository;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
    	System.out.println(username);
        Auth auth = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username" + username));
        
        return new User(auth.getUsername(), auth.getPassword(), new ArrayList<>());
    }
    
    
}
