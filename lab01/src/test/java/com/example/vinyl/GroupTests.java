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
import com.example.vinyl.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
class GroupTests {

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

}
