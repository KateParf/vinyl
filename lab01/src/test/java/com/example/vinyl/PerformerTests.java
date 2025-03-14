package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.service.PersonalRecordService;
import com.example.vinyl.service.RecordService;
import com.example.vinyl.service.UserService;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
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

}
