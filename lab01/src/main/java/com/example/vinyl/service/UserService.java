package com.example.vinyl.service;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.model.User;
import com.example.vinyl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public OpResult registerUser(String username, String email, String password, RoleEnum role) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encodedPassword, role);
        userRepository.save(newUser);
        return new OpResult(true);
    }

    public User getSessionUser() {
        return userRepository.findAll().getFirst();
        //!! TODO
    }

}
