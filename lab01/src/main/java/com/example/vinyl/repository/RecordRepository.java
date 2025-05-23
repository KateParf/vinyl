package com.example.vinyl.repository;

import com.example.vinyl.model.Record;

import com.example.vinyl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer>, RecordRepositoryCustom {
    Optional<Record> findByName(String name);

    List<Record> findByBarcode(String barcode);

    @Query("SELECT r FROM Record r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Record> findByNameContainingIgnoreCase(@Param("name") String name);

    // Найти все пластинки, где есть исполнитель с указанным ID
    @Query("SELECT DISTINCT r FROM Record r " +
           "LEFT JOIN r.performers p " +
           "LEFT JOIN r.groups g " +
           "LEFT JOIN g.performers gp " +
           "WHERE p.id = :performerId OR gp.id = :performerId")
    List<Record> findByPerformerId(@Param("performerId") Integer performerId);

    // Найти все пластинки, где есть группа с указанным ID
    @Query("SELECT DISTINCT r FROM Record r JOIN r.groups g WHERE g.id = :groupId")
    List<Record> findByGroupId(@Param("groupId") Integer groupId);

    //List<Record> findByGroups_IdAndPerformers_IdAndGenre_Id(Integer groups_id, Integer performers_id, Integer genre_id);
    
}
