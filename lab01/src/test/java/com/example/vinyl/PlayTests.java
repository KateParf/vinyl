package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.service.PlayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
class PlayTests {

	@Autowired
	private PlayService playService;
	
	//----

	@Test
	public void testPlay() {

		String mp3url = this.playService.getTrackMp3URL("Abba Mamma Mia");
		assertNotNull(mp3url); 

		String mp3body = this.playService.downloadMp3(mp3url);
		assertNotNull(mp3body); 

		
		String mp3url2 = this.playService.getTrackMp3URL("The Beatles Yellow Submarine");
		assertNotNull(mp3url2); 

		String mp3body2 = this.playService.downloadMp3(mp3url2);
		assertNotNull(mp3body2); 


		String mp3url3 = this.playService.getTrackMp3URL("Уродские бобры Дайте карандаш");
		assertNull(mp3url3); 
	
	}

}
