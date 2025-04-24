package com.example.vinyl.service;

import com.example.vinyl.dto.ChangePasswordDto;
import com.example.vinyl.dto.JwtDto;
import com.example.vinyl.dto.SignInDto;
import com.example.vinyl.dto.SignUpDto;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.model.Token;
import com.example.vinyl.model.User;
import com.example.vinyl.repository.TokenRepository;
import com.example.vinyl.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final UserService userService;


    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository, UserService userService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }


    public void signUp(SignUpDto request) {

        User user = new User();

        user.setLogin(request.getLogin());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleEnum.DEFAULT);

        userRepository.save(user);
    }

    private void revokeAllToken(User user) {

        List<Token> validTokens = tokenRepository.findAllAccessTokenByUser(user.getId());

        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.setLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {

        Token token = new Token();

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);

        tokenRepository.save(token);
    }

    public JwtDto authenticate(SignInDto request) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getLogin(),
                    request.getPassword()
                )
            );
        } catch (Exception ex) {
            return new JwtDto(null, null, ex.getMessage());
        }

        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new JwtDto(accessToken, refreshToken, null);
    }

    public ResponseEntity<JwtDto> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("No user found"));

        if (jwtService.isValidRefresh(token, user)) {
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity<>(new JwtDto(accessToken, refreshToken, null), HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    public void changePassword(ChangePasswordDto changePasswordDto) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByLogin(username).orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new SecurityException("Old password is incorrect!");
        }

        String encodedPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
        userRepository.updatePasswordById(user.getId(), encodedPassword);


    }
}
