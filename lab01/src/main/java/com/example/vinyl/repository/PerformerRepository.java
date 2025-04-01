package com.example.vinyl.repository;

import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Performer;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Integer> {
    Optional<Performer> findByName(String name);
}
