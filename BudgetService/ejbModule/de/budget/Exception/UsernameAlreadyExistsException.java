package de.budget.Exception;


/**
 * Exception, if an username is already existing
 * @author Marco
 *
 */
public class UsernameAlreadyExistsException extends BudgetOnlineException {
	
	private static final long serialVersionUID = 1L;
	private static final int CODE = 409;
	private static final String ERROR_STRING = "USER_EXISTS_EXCEPTION";

	
	public UsernameAlreadyExistsException() {
		super(CODE, ERROR_STRING, null);
	}
	public UsernameAlreadyExistsException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}
}
