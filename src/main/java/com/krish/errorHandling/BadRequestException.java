package com.krish.errorHandling;

public class BadRequestException extends RuntimeException {
	String message;

	public BadRequestException(String message) {
		super();
		this.message = message;
	}
}
