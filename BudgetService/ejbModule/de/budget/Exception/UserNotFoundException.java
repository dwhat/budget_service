
/**
 * Package for Exceptions
 */
package de.budget.Exception;


public class UserNotFoundException extends BudgetOnlineException {
	
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 20;
	private static final String ERROR_STRING = "USER_NOT_FOUND_EXCEPTION";
	


	public UserNotFoundException() {
		super(CODE, ERROR_STRING, null);
	}
	public UserNotFoundException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}

}
