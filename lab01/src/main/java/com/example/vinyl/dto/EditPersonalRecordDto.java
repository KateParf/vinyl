package com.example.vinyl.dto;

import com.example.vinyl.model.ConditionEnum;

import java.util.concurrent.locks.Condition;

public class EditPersonalRecordDto {
    private final Integer id;
    private final ConditionEnum condition;
    private final String comment;

    public EditPersonalRecordDto( Integer id, ConditionEnum condition, String comment) {

        this.id = id;
        this.condition = condition;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public String getComment() {
        return comment;
    }
}
