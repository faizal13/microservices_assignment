package com.rakbank.expensems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BudgetClientException extends RuntimeException {

	private static final long serialVersionUID = 2245513528680510774L;

	public BudgetClientException(String message) {
		super(message);
	}

}
