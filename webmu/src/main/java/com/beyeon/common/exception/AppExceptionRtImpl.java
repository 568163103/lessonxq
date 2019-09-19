package com.beyeon.common.exception;

public class AppExceptionRtImpl extends RuntimeException implements AppException {
	private static final long serialVersionUID = 4444102895085585139L;

	public AppExceptionRtImpl() {
		super();
	}

	public AppExceptionRtImpl(String message, Throwable cause) {
		super(message, cause);
	}

	public AppExceptionRtImpl(String message) {
		super(message);
	}

	public AppExceptionRtImpl(Throwable cause) {
		super(cause);
	}
}
