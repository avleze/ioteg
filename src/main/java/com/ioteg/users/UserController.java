package com.ioteg.users;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {


	@Autowired
	private UserService userService;


	@PostMapping
	public ResponseEntity<User> signUp(@RequestBody @Valid User user) {
		ResponseEntity<User> response = null;
		
		try {
			userService.signup(user);
			response = ResponseEntity.ok().build();
		}
		catch(Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	@PutMapping
	public ResponseEntity<User> modifyUser(@RequestBody @Valid UserDTO user, Principal principal) {
		ResponseEntity<User> response = ResponseEntity.ok().build();
		
		try {
			userService.modifyUserData(user);
		}
		catch(Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
}
