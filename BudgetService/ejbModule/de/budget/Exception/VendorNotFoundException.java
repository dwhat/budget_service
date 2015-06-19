/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a vendor cound not be found 
 * @author Marco
 */
public class VendorNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;
	
	private static final String ERROR_STRING = "VENDOR_NOT_FOUND_EXCEPTION";

	
	public VendorNotFoundException() {
		super(ERROR_STRING, null);
	}
	public VendorNotFoundException(String additionalMessage) {
		super(ERROR_STRING, additionalMessage);
	}
}