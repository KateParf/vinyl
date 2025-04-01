package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.exceptions.ResourceNotFoundException;
import com.example.vinyl.service.GenreService;
import com.example.vinyl.service.GroupService;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;

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
class GenreTests {

	@Autowired
	private GenreService genreService;
	
	// проверка добавления новой записи
	@Test @Order(1)
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

	@Test @Order(2)
	public void testErrorGenre() {
		// service - by id - return null
		var res = genreService.getById(888);
		assertNull(res);

		// service by name - return null
		var res2 = genreService.getByName("WrongName");
		assertNull(res2);
	}
	
	// проверка кейса когда добавлем дубль записи
    @Test @Order(3)
	public void testGenreDublicate() {
        Genre g1 = new Genre();
		g1.setName("Blues");
		genreService.add(g1);	

        Genre g2 = new Genre();
		g2.setName("Blues");
		DataIntegrityViolationException exception_dup = assertThrows(DataIntegrityViolationException.class,
		() -> genreService.add(g2));
		assertNotNull(exception_dup);
    }
}
