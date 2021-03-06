/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a basket could not be found 
 * @author Marco
 */
public class BasketNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;

	private static final String ERROR_STRING = "BASKET_NOT_FOUND_EXCEPTION";

	
	public BasketNotFoundException() {
		super(ERROR_STRING, null);
	}
	public BasketNotFoundException(String additionalMessage) {
		super(ERROR_STRING, additionalMessage);
	}

}