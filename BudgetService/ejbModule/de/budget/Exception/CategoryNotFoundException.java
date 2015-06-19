/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a category cound not be found 
 * @author Marco
 */
public class CategoryNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;

private static final String ERROR_STRING = "CATEGORY_NOT_FOUND_EXCEPTION";

	
	public CategoryNotFoundException() {
		super(ERROR_STRING, null);
	}
	public CategoryNotFoundException(String additionalMessage) {
		super(ERROR_STRING, additionalMessage);
	}

}