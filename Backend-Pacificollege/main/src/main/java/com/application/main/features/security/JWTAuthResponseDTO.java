package com.application.main.features.security;

import lombok.Data;

@Data
public class JWTAuthResponseDTO {
    
    private String tokenDeAcceso;
    private String tipoToken = "Bearer";
    
    public JWTAuthResponseDTO(String tokenDeAcceso) {
        super();
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public JWTAuthResponseDTO(String tokenDeAcceso, String tipoDeToken) {
        super();
        this.tokenDeAcceso = tokenDeAcceso;
        this.tipoToken = tipoDeToken;
    }
}
