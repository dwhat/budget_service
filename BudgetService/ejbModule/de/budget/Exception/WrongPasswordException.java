/**
 * Package for Exceptions
 */
package de.budget.Exception;

/**
 * Exception which will be thrown if a password is not equal the right password
 * @author Marco
 */
public class WrongPasswordException extends BudgetOnlineException {
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 500;

	private static final String ERROR_STRING = "PASSWORD_WRONG_EXCEPTION";

	
	public WrongPasswordException() {
		super(CODE, ERROR_STRING, null);
	}
	public WrongPasswordException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}

}