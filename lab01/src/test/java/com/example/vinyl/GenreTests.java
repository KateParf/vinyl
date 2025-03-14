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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
class GenreTests {

	@Autowired
	private GenreService genreService;
	
	@Test
	public void testaddNewGenre() {
		Genre rock = new Genre();
		rock.setName("Rock");
		genreService.add(rock);		

		Genre hiphop = new Genre();
		hiphop.setName("Hip Hop");
		genreService.add(hiphop);	

		List<Genre> genres = genreService.getAll();

        assertNotNull(genres);
        assertFalse(genres.isEmpty());

        assertTrue(genres.stream().anyMatch(g -> "Rock".equals(g.getName())));
        assertTrue(genres.stream().anyMatch(g -> "Hip Hop".equals(g.getName())));
	}

}
