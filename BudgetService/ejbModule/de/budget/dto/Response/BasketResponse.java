package de.budget.dto.Response;

import de.budget.dto.BasketTO;

/**
 * Class for the Basket response
 * @author Marco
 */
public class BasketResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
	
	private BasketTO basketTo;


	public BasketResponse() {
		
	}
	
	/**
	 * @return the basketTo
	 */
	public BasketTO getBasketTo() {
		return basketTo;
	}

	/**
	 * @param basketTo the basketTo to set
	 */
	public void setBasketTo(BasketTO basketTo) {
		this.basketTo = basketTo;
	}
	
	

}
