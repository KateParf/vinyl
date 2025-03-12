package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class RecordTests {

	@Autowired
	private RecordService recordService;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GenreService genreService;
		
	@Test
	public void testaddNewRecord() {
		Record recordAbbeyRoad = new Record();
		recordAbbeyRoad.setName("Abbey Road");
		recordAbbeyRoad.setYear(1969);
		recordAbbeyRoad.setPublisher(null);
		recordAbbeyRoad.setBarcode("111222333");
		//recordAbbeyRoad.addGroup( groupService.getByName("The Beatles") );
		//recordAbbeyRoad.addCover("http://covers.ru/Beatles/AbbeyRoad.jpg");
		Genre rock = genreService.getByName("Rock");
		recordAbbeyRoad.setGenre( rock );
		recordService.addNewRecord(recordAbbeyRoad);
	}

	@Test
	public void testGetAll() {
		List<Record> records = recordService.getAllRecords();
		System.out.println(records);		
	}


}
