package com.example.vinyl;

import com.example.vinyl.controllers.RecordsController;
import com.example.vinyl.dto.GroupDto;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Track;
import com.example.vinyl.model.User;
import com.example.vinyl.model.Record;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecordTests {

	@Autowired
    private RecordService recordService;

	@Autowired
    private RecordsController recordsController;

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
	
	//-----
		
	@Test @Order(1)
	public void testClearAll() {
		User user = userService.getUserByName("testLogin");
        personalRecordService.clear(user);
        recordService.clear();
        performerService.clear();
        groupService.clear();
        genreService.clear();
	}

	// тест добавления новой пластинки
	@Test @Order(2)
	public void testAddNewRecord() {
		
		Genre rock_add = new Genre();
		rock_add.setName("Rock");
		genreService.add(rock_add);		
		

		Group beatles_add = new Group();
		beatles_add.setName("The Beatles");
		beatles_add.setPicture("http://google.com/images/beatles.jpg");
		groupService.add(beatles_add);

		Record recordAbbeyRoad = new Record();
		recordAbbeyRoad.setName("Abbey Road");
		recordAbbeyRoad.setYear(1969);
		recordAbbeyRoad.setPublisher(null);
		recordAbbeyRoad.setBarcode("111222333");
		recordAbbeyRoad.addCover("http://covers.ru/Beatles/AbbeyRoad.jpg");
		Genre rock = genreService.getByName("Rock");
		recordAbbeyRoad.setGenre(rock);
		Group beatles = groupService.getByName("The Beatles");
		recordAbbeyRoad.addGroup( beatles );

		List<Track> tracks = new ArrayList<>();
		tracks.add(new Track("Come together"));
		tracks.add(new Track("Octopuses garden"));
		tracks.add(new Track("You never give me your money"));
		recordAbbeyRoad.setTracks(tracks);

		recordService.addNewRecord(recordAbbeyRoad);

		Record newRecord = recordService.getAllRecords().getLast();
		assertEquals(newRecord.getName(), "Abbey Road");
		List<GroupDto> groups = new ArrayList<>(newRecord.getGroups());
		GroupDto recGroup = groups.getFirst();
		assertEquals(recGroup.getName(), "The Beatles");
		
		//--

		Record record2 = new Record();
		record2.setName("XXXX");
		record2.setYear(1968);
		record2.setGenre(rock);
		recordAbbeyRoad.addGroup( beatles );
		recordService.addNewRecord(record2);

		List<Record> records = recordService.getAllRecords();	
		assertNotNull(records);
        assertFalse(records.isEmpty());

		//---
		// проверка вставки дублей
		// нельзя рекорды с одинаковым баркодом
		Record record3 = new Record();
		record3.setName("Testrecord3");
		record3.setGenre(rock);
		record3.setBarcode("322223322223");
		recordService.addNewRecord(record3);

		Record record4 = new Record();
		record4.setName("Testrecord4");
		record4.setGenre(rock);
		record4.setBarcode("322223322223");
		Exception exception_2 = assertThrows(Exception.class,
			() -> recordService.addNewRecord(record4));

	}

	// проверка кейса когда запись не найдена
	@Test @Order(3)
	public void testErrorRecord() {
		// service - by id - return null
		var res = recordService.getRecord(888);
		assertNull(res);

		// controller - by id - return exception
		ResourceNotFoundException exception_id = assertThrows(ResourceNotFoundException.class,
				() -> recordsController.getRecord(999));
		assertEquals("Record not found with id: 999", exception_id.getMessage());

		// service by name - return null
        var res2 = recordService.searchRecord("WrongName");
		assertNull(res2);

		// controller - by name - return exception
		ResourceNotFoundException exception_name = assertThrows(ResourceNotFoundException.class,
				() -> recordsController.getByName("Name"));
		assertEquals("Record not found with name: Name", exception_name.getMessage());

		/* barcode
		// service - by id - return null
		var res3 = recordService.getByBarcode("wrongBarcode");
		assertTrue(res3.isEmpty());

		// controller - by name - return exception
		ResourceNotFoundException exception_barcode = assertThrows(ResourceNotFoundException.class,
				() -> recordsController.getByBarcode("barcode"));
		assertEquals("Records not found with barcode: barcode", exception_name.getMessage());
 		*/
	}

	// тест получения пластинки с учетом фильтров
	@Test @Order(4)
	public void testFilterRecords() {
		List<Record> records = recordService.getFilterRecords(null, null, null, null);	
		assertNotNull(records);
        assertFalse(records.isEmpty());

		Genre rock = genreService.getByName("Rock");
		List<Record> records2 = recordService.getFilterRecords(rock.getId(), null, null, null);	
		assertNotNull(records2);
        assertFalse(records2.isEmpty());
	}	

	// тест получения всех пластинок
	@Test @Order(5)
	public void testAllRecords() {
		List<Record> records = recordService.getAllRecords();	
		assertNotNull(records);
        assertFalse(records.isEmpty());
	}	

	// тест получения названия трека по ид пластинки и трека
	@Test @Order(6)
	public void testGetTrackNameById() {
		List<Record> records = recordService.getAllRecords();	

		var recId = records.get(0).getId();
		var trackId = records.get(0).getTracks().toArray(new Track[0])[0].getId();

		String name = recordService.getTrackNameById(recId, trackId);	
		assertEquals("The Beatles Come together", name);

		// для несущест рекорда или трека - возврат нулл
		name = recordService.getTrackNameById(999, 888);
		assertNull(name);

		name = recordService.getTrackNameById(recId, 888);
		assertNull(name);

		name = recordService.getTrackNameById(777, trackId);
		assertNull(name);
	}

	// тест удаления
	@Test @Order(7)
	public void testDeleteRecord() {
		List<Record> records = recordService.getAllRecords();	

		// удаляем второй трек
		var recId = records.get(1).getId();	
		recordService.deleteRecord(recId);

		// удалем немуществ запись - ошибки не будет
		recordService.deleteRecord(121212);
	}


}
