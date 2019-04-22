package com.ioteg.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ioteg.repositories.UserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(UniqueUsername uniqueUsername) {
		// There's no initialization required.
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext cxt) {
		return username != null && !userRepository.findByUsername(username).isPresent();
	}

}