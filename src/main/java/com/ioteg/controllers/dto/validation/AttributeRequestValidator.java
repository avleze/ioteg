package com.ioteg.controllers.dto.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ioteg.controllers.dto.AttributeRequest;

public class AttributeRequestValidator implements Validator {

	private static final String ALPHABETICAL_REGEX = "[A-Z]+";
	private static final String ALPHANUMERIC_REGEX = "[0-9A-Z]+";

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AttributeRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AttributeRequest req = (AttributeRequest) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "field.required", "The field type is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "generationType", "field.required",
				"The generation type is required.");

		String genType = req.getGenerationType();
		String type = req.getType();

		if (genType != null && type != null) {
			if (genType.equalsIgnoreCase("Fixed"))
				fixedValidations(errors, req);
			else if (genType.equalsIgnoreCase("Random"))
				randomValidations(errors, req);
			else if (genType.equalsIgnoreCase("Sequential"))
				sequentialValidations(errors, req);

			if (Arrays.asList("Date", "Time").contains(type)) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "format", "field.required",
						"The format is required.");
				if (req.getFormat() != null)
					try {
						new SimpleDateFormat(req.getFormat());
					} catch (IllegalArgumentException e) {
						errors.rejectValue("format", "field.notvalid", e.getMessage());

					}
			}

		}

	}

	private void fixedValidations(Errors errors, AttributeRequest req) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "field.required", "The value is required.");
		if (Arrays.asList("Integer", "Long", "Float").contains(req.getType())) {
			if (req.getValue() != null)
				try {
					Double.parseDouble(req.getValue());
				} catch (NumberFormatException e) {
					errors.rejectValue("value", "field.notvalid",
							"If type is Integer, Long or Float, value has to be a number");
				}
		}

		if (Arrays.asList("Alphanumeric").contains(req.getType()) && req.getValue() != null
				&& !req.getValue().matches(ALPHANUMERIC_REGEX)) {
			errors.rejectValue("value", "field.notvalid",
					"If type is Alphanumeric, value has to follow the pattern " + ALPHANUMERIC_REGEX);
		}

		if (Arrays.asList("String").contains(req.getType()) && req.getValue() != null
				&& !req.getValue().matches(ALPHABETICAL_REGEX)) {
			errors.rejectValue("value", "field.notvalid",
					"If type is Alphanumeric, value has to follow the pattern " + ALPHABETICAL_REGEX);
		}
		
		if (Arrays.asList("Date", "Time").contains(req.getType())) {

			SimpleDateFormat sdf = null;
			if (req.getFormat() != null) {
				try {
					sdf = new SimpleDateFormat(req.getFormat());
					if (req.getValue() != null)
						try {
							sdf.parse(req.getValue());
						} catch (ParseException e) {
							errors.rejectValue("value", "field.notvalid", "Value has to follow the format pattern.");
						}
				} catch (IllegalArgumentException e) {
					errors.rejectValue("format", "field.notvalid", e.getMessage());
				}

			}

		}
	}

	private void randomValidations(Errors errors, AttributeRequest req) {
		if (Arrays.asList("Integer", "Long", "Float").contains(req.getType())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "min", "field.required", "The minimum is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "max", "field.required", "The maximum is required.");

			if(req.getMin() != null && req.getMax() != null && req.getMin() > req.getMax()) {
				errors.rejectValue("min", "field.notvalid", "Minimum has to be smaller than maximum");
				errors.rejectValue("max", "field.notvalid", "Maximum has to be greater than minimum");
			}
		}

		if (Arrays.asList("Date", "Time").contains(req.getType()))
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "format", "field.required", "The format is required.");
	}

	private void sequentialValidations(Errors errors, AttributeRequest req) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "begin", "field.required", "The begin is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "step", "field.required", "The step is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "end", "field.required", "The end is required.");

		if (req.getStep() != null)
			try {
				Double.parseDouble(req.getStep());
			} catch (NumberFormatException e) {
				errors.rejectValue("step", "field.notvalid", "Step has to be a number");
			}

		if (Arrays.asList("Integer", "Long", "Float").contains(req.getType())) {
			if (req.getBegin() != null)
				try {
					Double.parseDouble(req.getBegin());
				} catch (NumberFormatException e) {
					errors.rejectValue("begin", "field.notvalid",
							"If type is Integer, Long or Float, begin has to be a number");
				}

			if (req.getEnd() != null)
				try {
					Double.parseDouble(req.getEnd());
				} catch (NumberFormatException e) {
					errors.rejectValue("end", "field.notvalid",
							"If type is Integer, Long or Float, end has to be a number");
				}

		}

		if (Arrays.asList("Alphanumeric").contains(req.getType())) {
			if (req.getBegin() != null && !req.getBegin().matches(ALPHANUMERIC_REGEX))
				errors.rejectValue("begin", "field.notvalid",
						"If type is Alphanumeric, begin has to follow the pattern " + ALPHANUMERIC_REGEX);

			if (req.getEnd() != null && !req.getEnd().matches(ALPHANUMERIC_REGEX))
				errors.rejectValue("end", "field.notvalid",
						"If type is Alphanumeric, end has to follow the pattern " + ALPHANUMERIC_REGEX);
		}

		if (Arrays.asList("String").contains(req.getType())) {
			if (req.getBegin() != null && !req.getBegin().matches(ALPHABETICAL_REGEX))
				errors.rejectValue("begin", "field.notvalid",
						"If type is String, begin has to follow the pattern " + ALPHABETICAL_REGEX);

			if (req.getEnd() != null && !req.getEnd().matches(ALPHABETICAL_REGEX))
				errors.rejectValue("end", "field.notvalid",
						"If type is String, end has to follow the pattern " + ALPHABETICAL_REGEX);
		}

		if (Arrays.asList("Date", "Time").contains(req.getType())) {

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unit", "field.required", "The unit is required.");
			SimpleDateFormat sdf = null;
			if (req.getFormat() != null) {
				try {
					sdf = new SimpleDateFormat(req.getFormat());
					if (req.getBegin() != null)
						try {
							sdf.parse(req.getBegin());
						} catch (ParseException e) {
							errors.rejectValue("begin", "field.notvalid", "Begin has to follow the format pattern.");
						}

					if (req.getEnd() != null)
						try {
							sdf.parse(req.getEnd());
						} catch (ParseException e) {
							errors.rejectValue("end", "field.notvalid", "End has to follow the format pattern.");
						}
				} catch (IllegalArgumentException e) {
					errors.rejectValue("format", "field.notvalid", e.getMessage());
				}

			}

		}
	}

}
