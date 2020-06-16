package com.sts.employeems.exception;

public class FailedToSaveEmployeeException extends Exception {

	private static final long serialVersionUID = -6661584791338974833L;

	public FailedToSaveEmployeeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedToSaveEmployeeException(String message) {
		super(message);
	}

}
