package br.com.gadsc.accounting.infra;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {

	private List<String> errors = new ArrayList<>();

	public ValidationError() {

	}

	public void addError(String message) {
		errors.add(message);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> fieldErrors) {
		this.errors = fieldErrors;
	}

}