package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserTests {

	@Autowired
	private UserService userService;

	@Test
	public void testRegisterUser() {
		String username = "testuser";
		String email = "testuser@example.com";
		String password = "testpassword";

		OpResult result = userService.registerUser(username, email, password, RoleEnum.DEFAULT);

		assertEquals(true, result.result);
	}

}
