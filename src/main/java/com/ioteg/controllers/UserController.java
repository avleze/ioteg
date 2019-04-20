package com.ioteg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.services.UserService;
import com.ioteg.users.PasswordDTO;
import com.ioteg.users.PasswordNotMatchException;
import com.ioteg.users.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> signUp(@RequestBody @Valid User user) {
		ResponseEntity<User> response = ResponseEntity.ok().build();

		try {
			userService.signup(user);
		} catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		ResponseEntity<User> response = ResponseEntity.ok().build();
		User user = userService.loadUserById(id);
		
		response = ResponseEntity.ok().body(user);

		return response;
	}

	@PatchMapping("/{id}/password")
	public ResponseEntity<Object> changePassword(@PathVariable("id") Long id, @RequestBody @Valid PasswordDTO passwordDTO) {
		ResponseEntity<Object> response = ResponseEntity.ok().build();
		
		try {
			userService.changePassword(id, passwordDTO);
		} catch (PasswordNotMatchException passwordNotMatchException) {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(passwordNotMatchException.getMessage());
		}
	
		return response;
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> modifyUser(@PathVariable("id") Long id, @RequestBody @Valid User user) {
		userService.modifyUserData(id, user);
		return ResponseEntity.ok().build();
	}

}
