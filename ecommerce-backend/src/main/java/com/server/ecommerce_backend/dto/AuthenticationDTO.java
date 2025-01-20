package com.server.ecommerce_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationDTO {
    private String username;
    private String password;

    public AuthenticationDTO(String username, String password){
        this.username = username;
        this.password = password;
    }

}
