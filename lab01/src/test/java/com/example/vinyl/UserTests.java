package com.example.vinyl;

import com.example.vinyl.controllers.OpResult;
import com.example.vinyl.dto.SignInDto;
import com.example.vinyl.dto.SignUpDto;
import com.example.vinyl.model.RoleEnum;
import com.example.vinyl.service.AuthenticationService;
import com.example.vinyl.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserTests {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService authService;


	@Test @Order(1)
	public void testRegisterUser() {
		var sdto = new SignUpDto();
		sdto.setLogin("testLogin");
		sdto.setEmail("testLogin@email.com");
		sdto.setPassword("12345");
		authService.signUp(sdto);

		var user = userService.loadUserByUsername("testLogin");
		assertNotNull(user);
		assertNotEquals(user.getPassword(), "12345");

		// еще раз того же
		Exception exception = assertThrows(Exception.class,
			() -> authService.signUp(sdto) );
	}

	@Test @Order(2)
	public void testLogin() {
		var sdto = new SignInDto();
		sdto.setLogin("testLogin");
		sdto.setPassword("12345");
		var jwt = authService.authenticate(sdto);

		assertNotNull(jwt);
	
	}
}
