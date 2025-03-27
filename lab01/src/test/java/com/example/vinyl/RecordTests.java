package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class RecordTests {

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
		User user = userService.getSessionUser();
        personalRecordService.clear(user);
        recordService.clear();
        performerService.clear();
        groupService.clear();
        ///genreService.clear();
	}

	@Test
	public void testaddNewRecord() {
		/*
		Genre rock_add = new Genre();
		rock_add.setName("Rock");
		genreService.add(rock_add);		
		*/

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

		Set<Track> tracks = new HashSet<>();
		tracks.add(new Track("Come together"));
		tracks.add(new Track("Octopuses garden"));
		tracks.add(new Track("You never give me your money"));
		recordAbbeyRoad.setTracks(tracks);

		recordService.addNewRecord(recordAbbeyRoad);

		Record newRecord = recordService.getAllRecords().getLast();
		assertEquals(newRecord.getName(), "Abbey Road");
		List<Group> groups = new ArrayList<>(newRecord.getGroups());
		Group recGroup = groups.get(0);
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
	}

	@Test
	public void testFilterRecords() {
		List<Record> records = recordService.getFilterRecords(null, null, null, null);	
		assertNotNull(records);
        assertFalse(records.isEmpty());

		Genre rock = genreService.getByName("Rock");
		List<Record> records2 = recordService.getFilterRecords(rock.getId(), null, null, null);	
		assertNotNull(records2);
        assertFalse(records2.isEmpty());
	}	

	@Test
	public void testAllRecords() {
		List<Record> records = recordService.getAllRecords();	
		assertNotNull(records);
        assertFalse(records.isEmpty());
	}	

}
