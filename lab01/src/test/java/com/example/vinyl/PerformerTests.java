package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.controllers.PerformersController;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.User;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
class PerformerTests {

	@Autowired
	private GroupService groupService;

	@Autowired
    private RecordService recordService;

    @Autowired
    private PersonalRecordService personalRecordService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;
    
	@Autowired
	private PerformersController performerController;
	@Autowired
	private PerformerService performerService;
    
    //----

    @Test @Order(1)
    public void test_ClearAll() {
        recordService.clear();
        performerService.clear();
        groupService.clear();
        //genreService.clear();
    }
	
	// проверка добавления новой записи
	@Test @Order(2)
	public void testAddNewPerformer() {
		Performer mccartney = new Performer();
		mccartney.setName("Paul McCartney");
		Group beatlesGroup = groupService.getByName("The Beatles");
		mccartney.setGroup(beatlesGroup);
		performerService.add(mccartney);	

		Performer lennon = new Performer();
		lennon.setName("John Lennon");
		lennon.setPicture("http://google.com/images/lennon.jpg");
		lennon.setGroup(beatlesGroup);
		performerService.add(lennon);		

		List<Performer> performers = performerService.getAll();
		assertNotNull(performers);
        assertFalse(performers.isEmpty());

        assertTrue(performers.stream().anyMatch(g -> "Paul McCartney".equals(g.getName())));
        assertTrue(performers.stream().anyMatch(g -> "John Lennon".equals(g.getName())));
	}

	 // проверка кейса когда запись не найдена
    @Test @Order(3)
	public void testPerformerNotFound() {
		// service - by id - return null
		var res = performerService.getById(888);
		assertNull(res);

        // controller - by id - return exception
		ResourceNotFoundException exception_id_2 = assertThrows(ResourceNotFoundException.class,
		() -> performerController.getPerformer(999));
		assertEquals("Performer not found with id: 999", exception_id_2.getMessage());

        // service by name - return null
        var res2 = performerService.getByName("WrongName");
		assertNull(res2);
    }

    // проверка кейса когда добавлем дубль записи
    @Test @Order(4)
	public void testPerformerDublicate() {
        Performer p1 = new Performer();
		p1.setName("Енот");
		performerService.add(p1);	

        Performer p2 = new Performer();
		p2.setName("Енот");
		DataIntegrityViolationException exception_dup = assertThrows(DataIntegrityViolationException.class,
		() -> performerService.add(p2));
    }

}
