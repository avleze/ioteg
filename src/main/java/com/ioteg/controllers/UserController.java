package com.ioteg.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.controllers.dto.PasswordChangeRequest;
import com.ioteg.controllers.dto.UserDataChangeRequest;
import com.ioteg.controllers.dto.UserRegistrationRequest;
import com.ioteg.controllers.dto.UserResponse;
import com.ioteg.controllers.dto.mappers.UserMapper;
import com.ioteg.model.User;
import com.ioteg.services.EntityNotFoundException;
import com.ioteg.services.PasswordNotMatchException;
import com.ioteg.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;
	
	@PostMapping
	public ResponseEntity<UserResponse> signUp(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
		return ResponseEntity.ok().body(userMapper.userToUserResponse(userService.signup(userMapper.userRegistrationRequestToUser(userRegistrationRequest))));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) throws EntityNotFoundException {
		User user = userService.loadUserById(id);
		return ResponseEntity.ok().body(userMapper.userToUserResponse(user));
	}

	@PatchMapping("/{id}/password")
	public ResponseEntity<Object> changePassword(@PathVariable("id") Long id,
			@RequestBody @Valid PasswordChangeRequest passwordChangeRequest) throws PasswordNotMatchException, EntityNotFoundException {
		userService.changePassword(id, passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> modifyUser(@PathVariable("id") Long id, @RequestBody @Valid UserDataChangeRequest userData) throws EntityNotFoundException {
		User user = userService.modifyUserData(id, userData.getUsername(), userData.getEmail());
		return ResponseEntity.ok().body(userMapper.userToUserResponse(user));
	}

}
