package de.budget.dto.Response;

import de.budget.dto.IncomeTO;

/**
 * Response for the Income
 * @author Marco
 *
 */
public class IncomeResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;

	private IncomeTO incomeTo;
	
	/**
	 * Default Constructor
	 */
	public IncomeResponse() {
		
	}

	/**
	 * @return the incomeTo
	 */
	public IncomeTO getIncomeTo() {
		return incomeTo;
	}

	/**
	 * @param incomeTo the incomeTo to set
	 */
	public void setIncomeTo(IncomeTO incomeTo) {
		this.incomeTo = incomeTo;
	}
}
