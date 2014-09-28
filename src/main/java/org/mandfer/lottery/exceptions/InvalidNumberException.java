package org.mandfer.lottery.exceptions;

public class InvalidNumberException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidNumberException(String msg){
		super(msg);
	}

	public InvalidNumberException(String msg, Throwable throwable){
		super(msg, throwable);
	}
}
