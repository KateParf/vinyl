package com.example.vinyl.controllers;

import com.example.vinyl.dto.ChangePasswordDto;
import com.example.vinyl.dto.ErrorResponse;
import com.example.vinyl.dto.JwtDto;
import com.example.vinyl.dto.OpResult;
import com.example.vinyl.dto.SignInDto;
import com.example.vinyl.dto.SignUpDto;
import com.example.vinyl.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationService authenticationService;


    public AuthController(AuthenticationService authenticationService, Validator validator) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    JwtDto signIn(@RequestBody @Valid SignInDto signInDto) {

        return authenticationService.authenticate(signInDto);
    }

    @PostMapping("/register")
    JwtDto singUp(@RequestBody @Valid SignUpDto signUpDto) {
        return authenticationService.signUp(signUpDto);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<JwtDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        return authenticationService.refreshToken(request, response);
    }

    @PostMapping("/password_change")
    ResponseEntity<OpResult> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        OpResult res = authenticationService.changePassword(changePasswordDto);
        return ResponseEntity.ok(res);
    }
}
