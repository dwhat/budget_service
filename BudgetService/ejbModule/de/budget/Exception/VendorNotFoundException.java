/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a vendor cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class VendorNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;

	private static String vendorMessage = "VEndor not found for this id.";
	
	public VendorNotFoundException() {
		super(vendorMessage);
	}

}