/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if the database does a rollback
 * @author Marco
 */
public class RollbackException extends BudgetOnlineException{
	
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 407;
	private static final String ERROR_STRING = "ROLLBACK_EXCEPTION";

	
	public RollbackException() {
		super(CODE, ERROR_STRING, null);
	}
	public RollbackException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}
}