/**
* Package for Exceptions
*/
package de.budget.Exception;

public class NotFoundException extends BudgetOnlineException{

	private static final long serialVersionUID = 1L;

	private static final int CODE = 404;
	
	
	public NotFoundException(String message) {
		super(CODE, message);
	}

}
