package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class GenreTests {

	@Autowired
	private GenreService genreService;
	
	@Test
	public void testaddNewGenre() {
		genreService.clear();
		
		Genre rock = new Genre();
		rock.setName("Rock");
		genreService.add(rock);		

		Genre hiphop = new Genre();
		hiphop.setName("Hip Hop");
		genreService.add(hiphop);	
	}

	@Test
	public void testGetAll() {
		List<Genre> genres = genreService.getAll();
		System.out.println(genres);
	}


}
