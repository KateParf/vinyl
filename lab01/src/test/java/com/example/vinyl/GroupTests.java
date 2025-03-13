package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class GroupTests {

	@Autowired
	private GroupService groupService;
	
	@Test
	public void testaddNewGroup() {

		Group beatles = new Group();
		beatles.setName("The Beatles");
		beatles.setPicture("http://google.com/images/beatles.jpg");
		groupService.add(beatles);
		
		Group bratany = new Group();
		bratany.setName("Бутырка");
		groupService.add(bratany);	
	
	}

	@Test
	public void testGetAll() {
		List<Group> groups = groupService.getAll();
		System.out.println(groups);
	}


}
