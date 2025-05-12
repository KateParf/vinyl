package com.example.vinyl.repository;

import com.example.vinyl.model.PersonalRecord;
import com.example.vinyl.model.Record;

import com.example.vinyl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Integer> {
    Optional<PersonalRecord> findByUserAndRecord(User user, Record record);
    List<PersonalRecord> findAllByUser(User user);

    @Query("SELECT pr.record.id FROM PersonalRecord pr WHERE pr.user.id = :userId")
    List<Integer> findRecordIdsByUserId(@Param("userId") Long userId);
}
