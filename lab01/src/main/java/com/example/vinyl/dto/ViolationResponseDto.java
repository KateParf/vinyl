package com.example.vinyl.dto;

import lombok.Data;

import java.util.List;


@Data
public class ViolationResponseDto {
    private final List<ViolationDto> violations;

    public ViolationResponseDto(List<ViolationDto> violations) {
        this.violations = violations;
    }
}
