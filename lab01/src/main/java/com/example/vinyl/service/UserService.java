package com.example.vinyl.service;

import com.example.vinyl.model.User;
import com.example.vinyl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getSessionUser() {
        //!! FOR DEBUG
        User user = userRepository.findByLogin("testLogin").orElseThrow(() -> new RuntimeException("User not found!"));

        //String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userRepository.findByLogin(username).orElseThrow(() -> new RuntimeException("User not found!"));
        
        return user;
    }

    public User getUserByName(String username) {
        return userRepository.findByLogin(username).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
