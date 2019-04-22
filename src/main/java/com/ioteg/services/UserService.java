package com.ioteg.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ioteg.model.ChannelType;
import com.ioteg.model.User;
import com.ioteg.repositories.UserRepository;

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
	public UserDetails loadUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

	@PreAuthorize("hasPermission(#id, 'User', 'OWNER') or hasRole('ADMIN')")
	public User loadUserById(Long id) throws EntityNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
	}

	public User loadLoggedUser() throws EntityNotFoundException {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
	}

	@PreAuthorize("hasPermission(#id, 'User', 'OWNER') or hasRole('ADMIN')")
	public User loadUserByIdWithChannels(Long id) throws EntityNotFoundException {
		return userRepository.findUserByIdWithChannels(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
	}

	public User signup(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setMqttApiKey(generateMQTTApiKey());
		user.setRole("ROLE_USER");
		return userRepository.save(user);
	}

	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER') or hasRole('ADMIN')")
	public User modifyUserData(Long userId, String username, String email) throws EntityNotFoundException {
		User user = loadUserById(userId);

		user.setUsername(username);
		user.setEmail(email);
		return userRepository.save(user);
	}

	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER') or hasRole('ADMIN')")
	public void changePassword(Long userId, String oldPassword, String newPassword) throws EntityNotFoundException, PasswordNotMatchException {
		User user = loadUserById(userId);

		if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			userRepository.save(user);
		} else
			throw new PasswordNotMatchException();
	}
	
	@PreAuthorize("hasPermission(#userId, 'User', 'OWNER') or hasRole('ADMIN')")
	public List<ChannelType> getAllChannels(Long userId) throws EntityNotFoundException {
		return this.loadUserByIdWithChannels(userId).getChannels();
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
