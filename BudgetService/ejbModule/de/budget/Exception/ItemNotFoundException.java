/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a item cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class ItemNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;
	
	private static String itemMessage = "Item not found for this id.";


	public ItemNotFoundException() {
		super(itemMessage);
	}

}