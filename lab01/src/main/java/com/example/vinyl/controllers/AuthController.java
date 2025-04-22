package com.example.vinyl.controllers;

import com.example.vinyl.dto.ChangePasswordDto;
import com.example.vinyl.dto.JwtDto;
import com.example.vinyl.dto.SignInDto;
import com.example.vinyl.dto.SignUpDto;
import com.example.vinyl.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    JwtDto signIn(@RequestBody SignInDto signInDto) {
        return authenticationService.authenticate(signInDto);
    }

    @PostMapping("/register")
    ResponseEntity<String> singUp(@RequestBody SignUpDto signUpDto) {
        authenticationService.signUp(signUpDto);
        return ResponseEntity.ok("Регистрация прошла успешно!");
    }

    @PostMapping("/password_change")
    ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        authenticationService.changePassword(changePasswordDto);
        return ResponseEntity.ok("Пароль успешно изменен!");

    }
}
