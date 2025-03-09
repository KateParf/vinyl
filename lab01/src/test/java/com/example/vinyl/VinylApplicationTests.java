package com.example.vinyl;

import com.example.vinyl.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VinylApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void testRegisterUser() {
		String username = "testuser";
		String email = "testuser@example.com";
		String password = "testpassword";

		String result = userService.registerUser(username, email, password);

		assertEquals("User registered successfully!", result);
	}

}
