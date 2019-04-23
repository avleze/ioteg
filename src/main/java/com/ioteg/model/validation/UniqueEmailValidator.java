package com.ioteg.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ioteg.repositories.UserRepository;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(UniqueEmail uniqueEmail) {
		// There's no initialization required.
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext cxt) {
		return email != null && !userRepository.findByUsername(email).isPresent();
	}

}