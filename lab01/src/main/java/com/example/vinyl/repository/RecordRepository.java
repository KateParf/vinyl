package com.example.vinyl.repository;

import com.example.vinyl.model.Performer;
import com.example.vinyl.model.Record;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    Record findByName(String name);

    Record findByBarcode(String barcode);

    @Query("SELECT DISTINCT r FROM Record r " +
       "JOIN r.groups g " +
       "JOIN r.performers p " +
       "WHERE "+
       "(r.genre.id = :genre_id) AND " +
       "(:performer IS NULL OR p.id = :performer_id) AND " +
       "(:group IS NULL OR g.id = :group_id) AND " +
       "(:startYear IS NULL OR :endYear IS NULL OR (r.year >= :startYear AND r.year <= :endYear))")
    List<Record> findAllFiltered(
        @Param("genre_id") Integer genre_id,
        @Param("performer_id") Integer performer_id,
        @Param("group_id") Integer group_id,
        @Param("startYear") Integer startYear,
        @Param("endYear") Integer endYear
    );

    List<Record> findByGroups_IdAndPerformers_IdAndGenre_Id(Integer groups_id, Integer performers_id, Integer genre_id);

    // https://mbukowicz.github.io/java/spring-data/2020/04/13/querying-in-spring-data.html

}
