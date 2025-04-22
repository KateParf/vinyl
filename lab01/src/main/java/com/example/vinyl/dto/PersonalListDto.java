package com.example.vinyl.dto;


import com.example.vinyl.model.PersonalRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonalListDto {

    @JsonProperty(value = "personalRecord", required = true)
    List<PersonalRecord> personalRecordList;

    public PersonalListDto(List<PersonalRecord> personalRecordList) {
        this.personalRecordList = personalRecordList;
    }

    public List<PersonalRecord> getPersonalRecordList() {
        return personalRecordList;
    }

    public void setPersonalRecordList(List<PersonalRecord> personalRecordList) {
        this.personalRecordList = personalRecordList;
    }
}
