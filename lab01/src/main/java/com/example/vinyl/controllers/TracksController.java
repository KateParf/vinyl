package com.example.vinyl.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
class TracksController {

    TracksController() {
        
    }

    // Получать мп3 30 сек и проигрывать
    @GetMapping("/play/{id}")
    void play(@RequestBody Integer id) {
        // TODO !!
    }

}