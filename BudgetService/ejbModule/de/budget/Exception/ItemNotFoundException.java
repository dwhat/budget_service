/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a item cound not be found 
 * @author Marco
 */
public class ItemNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;
	
private static final String ERROR_STRING = "ITEM_NOT_FOUND_EXCEPTION";

	
	public ItemNotFoundException() {
		super(ERROR_STRING, null);
	}
	public ItemNotFoundException(String additionalMessage) {
		super(ERROR_STRING, additionalMessage);
	}
}