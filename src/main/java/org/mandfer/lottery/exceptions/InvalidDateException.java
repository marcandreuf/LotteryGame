package org.mandfer.lottery.exceptions;

public class InvalidDateException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidDateException(String msg){
		super(msg);
	}

	public InvalidDateException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}
