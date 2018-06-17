package com.myowndesk.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class CustError {
	private final String message;
	private List<FieldError> fieldErrors = new ArrayList<>();

	public CustError(String message) {
		this.message = message;
	}

	public void addFieldError(String objectName, String fieldName, String defaultMsg) {
		FieldError error = new FieldError(objectName, fieldName, defaultMsg);
		fieldErrors.add(error);
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public String getMessage() {
		return message;
	}
}
