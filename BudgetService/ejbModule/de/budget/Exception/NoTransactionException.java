/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a transaction is required but not found
 * @author Marco
 */
public class NoTransactionException extends BudgetOnlineException{
	
	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 408;
	private static final String ERROR_STRING = "NO_TRANSACTION_EXCEPTION";

	
	public NoTransactionException() {
		super(CODE, ERROR_STRING, null);
	}
	public NoTransactionException(String additionalMessage) {
		super(CODE, ERROR_STRING, additionalMessage);
	}
}