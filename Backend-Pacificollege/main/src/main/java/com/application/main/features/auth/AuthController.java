package com.application.main.features.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.main.features.security.JWTAuthResponseDTO;
import com.application.main.features.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody Auth auth){
        Authentication authentication = authenticationManager.authenticate(new 
                UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        //obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generarToken(authentication);
        
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }
}
