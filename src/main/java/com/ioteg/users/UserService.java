package com.ioteg.users;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private SecureRandom secureRandom;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsernameWithChannels(username);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Username not found");

		return user.get();
	}

	public User signup(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		byte[] apikey = new byte[16];
		secureRandom.nextBytes(apikey);

		user.setMqttApiKey(Base64.encodeBase64URLSafeString(apikey));
		return userRepository.save(user);
	}
	
	public void modifyUserData(UserDTO newUserData) {
		Optional<User> databaseUser = userRepository.findByUsername(authenticatedUser().getName());
		
		if(databaseUser.isPresent())
		{
			databaseUser.get().setUsername(newUserData.getUsername());
			databaseUser.get().setEmail(newUserData.getEmail());
			userRepository.save(databaseUser.get());
		}
	}
	
	private Principal authenticatedUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@PostConstruct
	private void initialize() throws NoSuchAlgorithmException {
		this.secureRandom = SecureRandom.getInstanceStrong();
	}
}
