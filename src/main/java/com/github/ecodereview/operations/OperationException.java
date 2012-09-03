package com.github.ecodereview.operations;

/**
 * This is a custom exception used to wrap the java exception.
 *
 * @author chaikc
 */
public class OperationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
     * The constructor method
     *
     * @param message a custom message
     * @param cause java throws the exception
     */
	public OperationException(String message, Throwable cause) {
		super(message, cause);
	}
}
