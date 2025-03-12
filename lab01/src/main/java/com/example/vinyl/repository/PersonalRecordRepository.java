package com.example.vinyl.repository;

import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Track;

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
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {
    // Найти все пластинки по ID юзера ---????
    List<Track> findByUserId(Integer userId);
}
