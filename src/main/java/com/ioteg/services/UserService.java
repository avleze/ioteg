package com.ioteg.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import javax.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ioteg.repositories.UserRepository;
import com.ioteg.users.PasswordDTO;
import com.ioteg.users.PasswordNotMatchException;
import com.ioteg.users.User;

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

	public User loadUserById(Long id) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Username not found");

		return user.get();
	}
	
	public User loadUserByIdWithChannels(Long id) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByIdWithChannels(id);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Username not found");

		return user.get();
	}

	public User signup(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setMqttApiKey(generateMQTTApiKey());

		return userRepository.save(user);
	}

	public void modifyUserData(Long userId, User newUserData) {
		User user = loadUserById(userId);

		user.setUsername(newUserData.getUsername());
		user.setEmail(newUserData.getEmail());
		userRepository.save(user);
	}


	public void changePassword(Long userId, PasswordDTO passwordDTO) throws PasswordNotMatchException {
		User user = loadUserById(userId);

		if (bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
			userRepository.save(user);
		} else
			throw new PasswordNotMatchException();
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	@PostConstruct
	private void initialize() throws NoSuchAlgorithmException {
		this.secureRandom = SecureRandom.getInstanceStrong();
	}

	private String generateMQTTApiKey() {
		byte[] apikey = new byte[16];
		secureRandom.nextBytes(apikey);
		return Base64.encodeBase64URLSafeString(apikey);
	}
}
