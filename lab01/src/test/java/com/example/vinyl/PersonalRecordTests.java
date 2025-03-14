package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.User;
import com.example.vinyl.model.ConditionEnum;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.PersonalRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class PersonalRecordTests {

    @Autowired
    private RecordService recordService;

    @Autowired
    private PersonalRecordService personalRecordService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PerformerService performerService;
    
    @Test
    public void test_ClearAll() {
        userService.registerUser("katya", "kat@mail.ru", "xxxbbbvvv");
        User user = userService.getSessionUser();
        personalRecordService.clear(user);
        recordService.clear();
        performerService.clear();
        groupService.clear();
        genreService.clear();
    }

    @Test
    public void testAddExistRecord() {
        User user = userService.getSessionUser();

        Group abba = new Group();
        abba.setName("ABBA");
        groupService.add(abba);

        Genre pop1 = new Genre();
        pop1.setName("Pop");
        genreService.add(pop1);

        Record recordRingRing = new Record();
        recordRingRing.setName("Ring Ring");
        recordRingRing.setYear(1973);
        recordRingRing.setPublisher(null);
        recordRingRing.setBarcode("55666777");
        recordRingRing.addCover("http://covers.ru/Abba/RingRing.jpg");
        Genre pop2 = genreService.getByName("Pop");
        recordRingRing.setGenre(pop2);
        Group abba2 = groupService.getByName("ABBA");
        recordRingRing.addGroup(abba2);
        recordService.addNewRecord(recordRingRing);

        Integer ringRingId = recordService.getAllRecords().getLast().getId();

        PersonalRecord myRingRing = personalRecordService.addExistRecord(ringRingId, user);

        PersonalRecord myRingRing2 = personalRecordService.getAllRecords().getLast();
        assertEquals(myRingRing2.getRecord().getId(), ringRingId);
        assertEquals(myRingRing2.getRecord().getName(), "Ring Ring");
    }

    @Test
    public void testUpdatePersRecord() {
        PersonalRecord persrec = personalRecordService.getAllRecords().getLast();
        persrec.setComment("My liebe plastinken");
        persrec.setCondition(ConditionEnum.NEW);
        personalRecordService.updateRecord(persrec);

        PersonalRecord persrec2 = personalRecordService.getAllRecords().getLast();
        assertEquals(persrec2.getComment(), "My liebe plastinken");
        assertEquals(persrec2.getCondition(), ConditionEnum.NEW);
    }

}
