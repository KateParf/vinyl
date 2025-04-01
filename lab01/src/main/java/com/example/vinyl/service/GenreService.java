package com.example.vinyl.service;

import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.repository.GenreRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    // Получить жанр по id
    // если не найден то вернуть null
    public Genre getById(Integer id) {
        return genreRepository.findById(id)
            .orElse(null);
    }

    // Получить жанр по названию
    // если не найдена то вернуть null
    public Genre getByName(String name) {
        return genreRepository.findByName(name)
            .orElse(null);
    }

    // добавляет новый жанр
    // При вставке дубликата - возвращается DataIntegrityViolationException
    public Genre add(Genre genre)  throws DataIntegrityViolationException {
        return genreRepository.save(genre);
    }

    public void clear() {
        genreRepository.deleteAll();
    }
}
