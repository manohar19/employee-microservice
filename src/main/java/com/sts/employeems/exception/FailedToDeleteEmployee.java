package com.sts.employeems.exception;

public class FailedToDeleteEmployee extends Exception {

	private static final long serialVersionUID = 3910655798420503671L;

	public FailedToDeleteEmployee(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedToDeleteEmployee(String message) {
		super(message);
	}

}
