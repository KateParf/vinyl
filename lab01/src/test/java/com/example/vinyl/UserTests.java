package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.service.AuthenticationService;
import com.example.vinyl.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserTests {

	@Autowired
	private UserService userService;
	private AuthenticationService authService;

	@Test
	public void testRegisterUser() {

	}

}
