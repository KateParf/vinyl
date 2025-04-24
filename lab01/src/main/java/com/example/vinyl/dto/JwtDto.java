package com.example.vinyl.dto;

import lombok.Data;

@Data
public class JwtDto {
    private final String accessToken;
    private final String refreshToken;
    private final String error;

    public JwtDto(String accessToken, String refreshToken, String error) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.error = error;
    }
}
