package com.example.vinyl;

import com.example.vinyl.controllers.GroupsController;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.User;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroupTests {

	@Autowired
	private GroupService groupService;
    @Autowired
	private GroupsController groupController;

	@Autowired
    private RecordService recordService;

    @Autowired
    private PersonalRecordService personalRecordService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private PerformerService performerService;
    
    //---

    @Test @Order(1)
    public void testClearAll() {
        recordService.clear();
        performerService.clear();
        groupService.clear();
        //genreService.clear();
    }
	
    // проверка добавления новой записи
	@Test @Order(2)
	public void testAddNewGroup() {

		Group beatles = new Group();
		beatles.setName("The Beatles");
		beatles.setPicture("http://google.com/images/beatles.jpg");
		groupService.add(beatles);
		
		Group bratany = new Group();
		bratany.setName("Бутырка");
		groupService.add(bratany);	

		List<Group> groups = groupService.getAll();
		assertNotNull(groups);
        assertFalse(groups.isEmpty());

        assertTrue(groups.stream().anyMatch(g -> "The Beatles".equals(g.getName())));
        assertTrue(groups.stream().anyMatch(g -> "Бутырка".equals(g.getName())));
	
	}

    // проверка кейса когда запись не найдена
    @Test @Order(3)
	public void testGroupNotFound() {
		// service - by id - return null
		var res = groupService.getById(888);
		assertNull(res);

        // controller - by id - return exception
		ResourceNotFoundException exception_id_2 = assertThrows(ResourceNotFoundException.class,
		() -> groupController.getGroup(999));
		assertEquals("Group not found with id: 999", exception_id_2.getMessage());

        // service by name - return null
        var res2 = groupService.getByName("WrongName");
		assertNull(res2);
    }

    // проверка кейса когда добавлем дубль записи
    @Test @Order(4)
	public void testGroupDublicate() {
        Group grp1 = new Group();
		grp1.setName("Еноты");
		groupService.add(grp1);	

        Group grp2 = new Group();
		grp2.setName("Еноты");
		DataIntegrityViolationException exception_dup = assertThrows(DataIntegrityViolationException.class,
		() -> groupService.add(grp2));
    }

}
