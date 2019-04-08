package com.ioteg.users;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(UniqueUsername uniqueUsername) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext cxt) {
		return username != null && !userRepository.findByUsername(username).isPresent();
	}

}