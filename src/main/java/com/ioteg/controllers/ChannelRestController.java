package com.ioteg.controllers;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ioteg.model.ChannelType;
import com.ioteg.services.UserService;
import com.ioteg.users.User;

@RestController
@RequestMapping("/api/channels")
public class ChannelRestController {
	@Autowired
	private UserService userService;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ChannelType> generateConfigurableEvents(@RequestBody @Valid ChannelType channel,
			Principal user) {

		User loggedUser = (User) userService.loadUserByUsername(user.getName());
		loggedUser.getChannels().add(channel);
		userService.save(loggedUser);

		return ResponseEntity.ok().body(channel);
	}
}
