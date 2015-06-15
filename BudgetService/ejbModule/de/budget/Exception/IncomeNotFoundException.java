/**
 * Package for Exceptions
 */
package de.budget.Exception;

import javax.annotation.Resource;

/**
 * Exception which will be thrown if a income cound not be found 
 * @author Marco
 * @date 05.06.2015
 */
public class IncomeNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 8759021636475023682L;
	
	@Resource
	private static String incomeMessage = "No income found for this id";


	public IncomeNotFoundException() {
		super(incomeMessage);
	}

}