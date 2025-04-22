package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.controllers.PersonalRecordsController;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.*;
import com.example.vinyl.model.Record;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

@SpringBootTest
class PersonalRecordTests {

    @Autowired
    private RecordService recordService;

    @Autowired
    private PersonalRecordService personalRecordService;

    @Autowired
    private PersonalRecordsController personalRecordController;

    @Autowired
    private GenreService genreService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PerformerService performerService;
    
    @Test @Order(1)
    public void test_ClearAll() {
        User user = userService.getSessionUser();
        personalRecordService.clear(user);
        recordService.clear();
        performerService.clear();
        groupService.clear();
        //genreService.clear();
    }

    // проверка добавления новой записи
    @Test @Order(2)
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

        Record record2 = new Record();
		record2.setName("Test delete record");
		record2.setYear(2000);
		record2.setGenre(pop1);
		recordService.addNewRecord(record2);
        Integer record2Id = recordService.getAllRecords().getLast().getId();
        PersonalRecord myrecord2 = personalRecordService.addExistRecord(record2Id, user);

        // --
        // проверка добавления несуществующей пластинки
        // service - by id - return null
        PersonalRecord testRec = personalRecordService.addExistRecord(999, user);
		assertNull(testRec);

        // controller - by id - return exception
        ResourceNotFoundException exception_id = assertThrows(ResourceNotFoundException.class,
         () -> personalRecordController.addExistingRecord(999));
         assertEquals("Record not found with id: 999", exception_id.getMessage());
        
    }

     // проверка кейса когда запись не найдена
     @Test @Order(3)
     public void testPerformerNotFound() {
         // service - by id - return null
        var testRec = personalRecordService.getRecord(999);
		assertNull(testRec);
 
         // controller - by id - return exception
        ResourceNotFoundException exception_id = assertThrows(ResourceNotFoundException.class,
         () -> personalRecordController.getRecord(999));
         assertEquals("Personal record not found with id: 999", exception_id.getMessage());
    }

    // проверка редактирования записи
    @Test @Order(4)
    public void testUpdatePersRecord() {
        PersonalRecord persrec = personalRecordService.getAllRecords().getLast();
        persrec.setComment("My liebe plastinken");
        persrec.setCondition(ConditionEnum.NEW);
        personalRecordService.updateRecord(persrec);

        PersonalRecord persrec2 = personalRecordService.getAllRecords().getLast();
        assertEquals(persrec2.getComment(), "My liebe plastinken");
        assertEquals(persrec2.getCondition(), ConditionEnum.NEW);
    }

    // тест удаления
	@Test @Order(5)
	public void testDeletePersRecord() {
		List<PersonalRecord> records = personalRecordService.getAllRecords();	

		// удаляем второй трек
		var recId = records.get(1).getId();	
		personalRecordService.deleteRecord(recId);

		// удалем немуществ запись - ошибки не будет
		personalRecordService.deleteRecord(121212);
	}

}
