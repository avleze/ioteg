package com.ioteg.users;

import java.security.SecureRandom;

import javax.validation.Valid;

import org.eclipse.paho.client.mqttv3.internal.websocket.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecureRandom secureRandom;

	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody @Valid User user) {
		ResponseEntity<User> response = null;
		
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
			byte[] apikey = new byte[16];
			secureRandom.nextBytes(apikey);
			user.setMqttApiKey(Base64.encodeBytes(apikey));
			response = ResponseEntity.ok().build();
			userRepository.save(user);
		}
		catch(Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
}
