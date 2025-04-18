package com.example.vinyl.model;

import java.io.Serializable;

import org.hibernate.annotations.Type;

import co.elastic.clients.elasticsearch.watcher.ConditionType;
import jakarta.persistence.*;

@Entity
@Table(name = "personal_record")
public class PersonalRecord implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "condition", columnDefinition = "condition_enum")
    @Enumerated(EnumType.STRING)
    private ConditionEnum condition;

    private String comment;

    public Integer getId() {
        return this.id;
    }

    public ConditionEnum getCondition() {
        return this.condition;
    }

    public void setCondition(ConditionEnum condition) {
        this.condition = condition;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Record getRecord() {
        return this.record;
    }

    public void setRecord(Record newRecord) {
        this.record = newRecord;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
