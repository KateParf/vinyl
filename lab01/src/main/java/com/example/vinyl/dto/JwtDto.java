package com.example.vinyl.dto;

import lombok.Data;

@Data
public class JwtDto {
    private final String accessToken;

    public JwtDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
