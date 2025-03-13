package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.service.PerformerService;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class PerformerTests {

	@Autowired
	private PerformerService performerService;
	@Autowired
	private GroupService groupService;
	
	@Test
	public void testaddNewPerformer() {
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

	}

	@Test
	public void testGetAll() {
		List<Performer> performers = performerService.getAll();
		System.out.println(performers);
	}

}
