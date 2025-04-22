package com.example.vinyl.repository;

import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;

import com.example.vinyl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {
    Optional<PersonalRecord> findByUserAndRecord(User user, Record record);
    List<PersonalRecord> findAllByUser(User user);
}
