/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a session could not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class NoSessionException extends BudgetOnlineException{
	
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 10;
	private static final String ERROR_STRING = "SESSION_NOT_FOUND_EXCEPTION";

	
	public NoSessionException() {
		super(CODE, ERROR_STRING, null);
	}
	public NoSessionException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}
}
