package com.example.vinyl.dto;

import lombok.Data;

@Data
public class JwtDto {
    private final String accessToken;
    private final String refreshToken;

    public JwtDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
