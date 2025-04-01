package com.example.vinyl;

import com.example.vinyl.service.*;
import com.example.vinyl.controllers.*;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.Group;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class ErrorTests {

	@Autowired
    private RecordsController recordsController;

	@Autowired
	private PersonalRecordsController personalRecordController;

	@Autowired
	private GenreService genreService;

	@Autowired
	private GroupsController groupController;
	@Autowired
	private GroupService groupService;

	@Autowired
	private PerformersController performerController;
	@Autowired
	private PerformerService performerService;

	@Test
	public void testErrorRecord() {
		// id
		ResourceNotFoundException exception_id = assertThrows(ResourceNotFoundException.class,
				() -> recordsController.getRecord(999));
		assertEquals("Record not found with id: 999", exception_id.getMessage());

		// name
		ResourceNotFoundException exception_name = assertThrows(ResourceNotFoundException.class,
				() -> recordsController.getByName("Name"));
		assertEquals("Record not found with name: Name", exception_name.getMessage());

		/* barcode
		ResourceNotFoundException exception_barcode = assertThrows(ResourceNotFoundException.class,
				() -> recordsController.getByBarcode("barcode"));
		assertEquals("Records not found with barcode: barcode", exception_name.getMessage());
 		*/
	}

	@Test
	public void testErrorPersonalRecord() {
		// id
		ResourceNotFoundException exception_id = assertThrows(ResourceNotFoundException.class,
		() -> personalRecordController.getRecord(999));
		assertEquals("Personal record not found with id: 999", exception_id.getMessage());
	}

	

	

	

}
