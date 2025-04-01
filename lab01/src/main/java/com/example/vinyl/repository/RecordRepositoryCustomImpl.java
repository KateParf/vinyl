package com.example.vinyl.repository;

import com.example.vinyl.model.Record;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom {

    // https://mbukowicz.github.io/java/spring-data/2020/04/13/querying-in-spring-data.html

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Record> findAllFiltered(Integer genre_id, Integer performers_id, Integer groups_id, Integer startYear, Integer endYear) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Record> query = builder.createQuery(Record.class);
        Root<Record> record = query.from(Record.class);

        // Создаём список условий (Predicate)
        List<Predicate> predicates = new ArrayList<>();

        if (genre_id != null) {
            predicates.add(builder.equal(record.get("genre").get("id"), genre_id));
        }

        if (performers_id != null) {
            predicates.add(builder.equal(record.join("performers").get("id"), performers_id));
        }

        if (groups_id != null) {
            predicates.add(builder.equal(record.join("groups").get("id"), groups_id));
        }

        // Фильтр по диапазону лет
        if (startYear != null && endYear != null) {
            predicates.add(builder.between(record.get("year"), startYear, endYear));
        }

        query.select(record).where(predicates.toArray(new Predicate[0]));

        // Выполняем запрос и возвращаем результат
        return entityManager.createQuery(query).getResultList();
    }
}