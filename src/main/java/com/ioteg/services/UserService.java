package com.ioteg.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private UserRepository userRepository;
	private SecureRandom secureRandom;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * @param userRepository
	 * @param bCryptPasswordEncoder
	 * @throws NoSuchAlgorithmException
	 */
	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws NoSuchAlgorithmException {
		super();
		this.userRepository = userRepository;
		this.secureRandom = SecureRandom.getInstanceStrong();
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsernameWithChannels(username);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Username not found");

		return user.get();
	}

	@PreAuthorize("hasPermission(#id, 'User', 'OWNER')")
	public User loadUserById(Long id) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Username not found");

		return user.get();
	}

	public User loadLoggedUser() throws UsernameNotFoundException {
		Optional<User> user = userRepository
				.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		if (!user.isPresent())
			throw new UsernameNotFoundException("Username not found");

		return user.get();
	}

	@PreAuthorize("hasPermission(#id, 'User', 'OWNER')")
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

	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER')")
	public void modifyUserData(Long userId, User newUserData) {
		User user = loadUserById(userId);

		user.setUsername(newUserData.getUsername());
		user.setEmail(newUserData.getEmail());
		userRepository.save(user);
	}

	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER')")
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

	private String generateMQTTApiKey() {
		byte[] apikey = new byte[16];
		secureRandom.nextBytes(apikey);
		return Base64.encodeBase64URLSafeString(apikey);
	}
}
