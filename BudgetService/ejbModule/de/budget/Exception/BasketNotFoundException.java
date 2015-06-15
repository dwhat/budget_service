/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a basket cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class BasketNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;

	private static String basketMessage = "Basket not found for this id.";


	public BasketNotFoundException(String message) {
		super(basketMessage);
	}

}