/**
 * Package for Exceptions
 */
package de.budget.Exception;


/**
 * Exception which will be thrown if a category cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class CategoryNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;

	private static String categoryMessage = "Category not found for this id";


	public CategoryNotFoundException() {
		super(categoryMessage);
	}

}