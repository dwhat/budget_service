/**
 * Package for Exceptions
 */
package de.budget.Exception;

/**
 * Exception which will be thrown if a income cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class InvalidPasswordException extends BudgetOnlineException {

	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 500;

	private static final String ERROR_STRING = "PASSWORD_INVALID_EXCEPTION";

	
	public InvalidPasswordException() {
		super(CODE, ERROR_STRING, null);
	}
	public InvalidPasswordException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}

}