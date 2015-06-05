/**
 * Package for Exceptions
 */
package de.budget.Exception;

/**
 * Exception which will be thrown if a item cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class ItemNotFoundException extends BudgetOnlineException {

	private static final long serialVersionUID = 8759021636475023682L;
	private static final int CODE = 404;

	public ItemNotFoundException(String message) {
		super(CODE, message);
	}

}