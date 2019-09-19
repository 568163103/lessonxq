package com.beyeon.common.exception;

public class AppExceptionImpl extends Exception implements AppException {
	private static final long serialVersionUID = -498639706549287642L;

	public AppExceptionImpl() {
		super();
	}

	public AppExceptionImpl(String message, Throwable cause) {
		super(message, cause);
	}

	public AppExceptionImpl(String message) {
		super(message);
	}

	public AppExceptionImpl(Throwable cause) {
		super(cause);
	}
	
}
