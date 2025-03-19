package com.example.vinyl.repository;

import com.example.vinyl.model.Record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RecordRepository extends JpaRepository<Record, Integer>, RecordRepositoryCustom {
    Record findByName(String name);

    Record findByBarcode(String barcode);

    //List<Record> findByGroups_IdAndPerformers_IdAndGenre_Id(Integer groups_id, Integer performers_id, Integer genre_id);
    
}
