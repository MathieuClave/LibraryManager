package com.ensta.librarymanager.exception;

public class ServiceException extends Exception {
	public ServiceException (String error) {super(error);}

	public ServiceException() {
		super("Service Error");
	}
}
