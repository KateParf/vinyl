package com.example.vinyl.dto;


import lombok.Data;

@Data
public class ViolationDto {
    private final String fieldName;
    private final String message;

    public ViolationDto(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
