package com.example.vinyl.service;

import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.repository.GenreRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // Получить жанры
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    //!! TODO !!
    public Genre getByName(String name) {
        return genreRepository.findAll().getFirst();
    }

    // add
    public Genre add(Genre genre) {
        return genreRepository.save(genre);
    }

    public void clear() {
        genreRepository.deleteAll();
    }
}
