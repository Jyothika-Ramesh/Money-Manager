package com.springapps.familymanager.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;

}

