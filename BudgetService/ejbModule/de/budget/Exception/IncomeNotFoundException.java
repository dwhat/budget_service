/**
 * Package for Exceptions
 */
package de.budget.Exception;



/**
 * Exception which will be thrown if a income cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class IncomeNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;
	
private static final String ERROR_STRING = "INCOME_NOT_FOUND_EXCEPTION";

	
	public IncomeNotFoundException() {
		super(ERROR_STRING, null);
	}
	public IncomeNotFoundException(String additionalMessage) {
		super(ERROR_STRING, additionalMessage);
	}

}