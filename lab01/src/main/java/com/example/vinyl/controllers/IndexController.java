package com.example.vinyl.controllers;

import com.example.vinyl.dto.ViolationDto;
import com.example.vinyl.dto.ViolationResponseDto;
import jakarta.validation.ConstraintViolationException;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController implements ErrorController {
    @RequestMapping(value = "${server.error.path:${error.path:/error}}")
    public String error() {
        return "forward:/index.html";
    }
}