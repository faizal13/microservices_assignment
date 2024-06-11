package com.rakbank.expensems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCustomException extends RuntimeException{

	private static final long serialVersionUID = -2710220100884473075L;

	 public BadRequestCustomException(String exception) {
	        super(exception);
	    }
}
