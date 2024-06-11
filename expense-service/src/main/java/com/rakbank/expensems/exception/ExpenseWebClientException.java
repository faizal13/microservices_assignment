package com.rakbank.expensems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExpenseWebClientException extends RuntimeException {

	private static final long serialVersionUID = 2245513528680510774L;

	public ExpenseWebClientException(String message) {
		super(message);
	}

}
