package com.ioteg.users;

import java.security.Principal;
import java.security.SecureRandom;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping
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
			
			user.setMqttApiKey(Base64.encodeBase64URLSafeString(apikey));
			response = ResponseEntity.ok().build();
			userRepository.save(user);
		}
		catch(Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
	@PutMapping
	public ResponseEntity<User> modifyUser(@RequestBody @Valid UserDTO user, Principal principal) {
		ResponseEntity<User> response = null;
		
		try {
			User databaseUser = userRepository.findByUsername(principal.getName()).get();
			databaseUser.setUsername(user.getUsername());
			databaseUser.setEmail(user.getEmail());
			userRepository.save(databaseUser);
		}
		catch(Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		return response;
	}
	
}
