package com.krish.errorHandling;

public class AuthenticationFailedException extends RuntimeException {
	private String message;

	public AuthenticationFailedException(String message) {
		super();
		this.message = message;
	}

}
