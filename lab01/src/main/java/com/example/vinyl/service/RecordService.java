package com.example.vinyl.service;

import com.example.vinyl.exceptions.RecordNotFoundException;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.Track;
import com.example.vinyl.repository.RecordRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // Получить все пластинки
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    // Получить все пластинки
    public List<Record> getFilterRecords(Integer genre_id, Integer performer_id, Integer group_id, Integer decade) {
        Integer startYear = null;
        Integer endYear = null;
        if (decade != null) {
            startYear = decade; // Например, 1960
            endYear = startYear + 9; // 1960 -> 1969
        }
        // Логируем параметры для отладки
        String info = String.format(
                "Filtering records with genre= %d, performer= %d, group= %d, startYear= %d, endYear= %d",
                genre_id, performer_id, group_id, startYear, endYear);
        log.info(info);
        // return recordRepository.findAllFiltered(genre_id, performer_id, group_id,
        // startYear, endYear);

        // return recordRepository.findByGroups_IdAndPerformers_IdAndGenre_Id(group_id,
        // performer_id, genre_id);

        return recordRepository.findAllFiltered(genre_id, performer_id, group_id, startYear, endYear);
    }

    // Получить пластинку по ID
    public Record getRecord(Integer id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Поиск пластинки по штрихкоду
    public List<Record> getByBarcode(String barcode) {
        return recordRepository.findByBarcode(barcode);
    }

    // Поиск пластинки по имени
    public Record searchRecord(String name) {
        return recordRepository.findByName(name);
    }

    // Добавить новую пластинку в общий каталог
    public Record addNewRecord(Record record) {
        return recordRepository.save(record);
    }

    // Edit single item
    public Record updateRecord(Record editRecord) {
        return recordRepository.findById(editRecord.getId())
                .map(record -> {
                    record.setName(editRecord.getName());
                    record.setYear(editRecord.getYear());
                    record.setPublisher(editRecord.getPublisher());
                    record.setBarcode(editRecord.getBarcode());
                    return recordRepository.save(record);
                })
                .orElseGet(() -> {
                    return recordRepository.save(editRecord);
                });
    }

    // Delete single item
    public void deleteRecord(Integer id) {
        recordRepository.deleteById(id);
    }

    public void clear() {
        recordRepository.deleteAll();
    }

    public String getTrackNameById(Integer recordId, Integer trackId) {
        Record record = this.getRecord(recordId);

        Optional<Track> trackOptional = record.getTracks().stream()
                .filter(track -> track.getId().equals(trackId))
                .findFirst();

        if (trackOptional.isPresent()) {

            // Получаем название группы (если есть)
            String groupName = record.getGroups().stream()
                    .findFirst()
                    .map(Group::getName)
                    .orElse(null);

            // Получаем название исполнителя (если есть)
            String performerName = record.getPerformers().stream()
                    .findFirst()
                    .map(Performer::getName)
                    .orElse(null);

            if (groupName != null) {
                return groupName + " " + trackOptional.get().getName();
            }
            if (performerName != null) {
                return performerName + " " + trackOptional.get().getName();
            }
        }
        return null;
    }
}
