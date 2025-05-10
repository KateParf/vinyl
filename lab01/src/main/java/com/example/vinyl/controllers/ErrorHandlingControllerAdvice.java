package com.example.vinyl.controllers;

import com.example.vinyl.dto.ViolationDto;
import com.example.vinyl.dto.ViolationResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ViolationResponseDto onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<ViolationDto> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new ViolationDto(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ViolationResponseDto(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ViolationResponseDto onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<ViolationDto> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ViolationDto(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ViolationResponseDto(violations);
    }

}