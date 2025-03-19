package com.example.vinyl.repository;

import com.example.vinyl.model.Record;
import java.util.List;

public interface RecordRepositoryCustom {
    List<Record> findAllFiltered(Integer genre_id, Integer performers_id, Integer groups_id, Integer startYear, Integer endYear);
}