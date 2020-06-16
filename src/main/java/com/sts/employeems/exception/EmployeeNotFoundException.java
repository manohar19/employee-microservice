package com.sts.employeems.exception;

public class EmployeeNotFoundException extends Exception {

	private static final long serialVersionUID = -3984011278033403031L;

	public EmployeeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeNotFoundException(String message) {
		super(message);
	}

}
