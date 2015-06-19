package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.AmountTO;

/**
 * Klasse für eine Liste von AmountWithIdResponses Objecten
 * @author Marco
 * 
 */
public class AmountListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<AmountTO> amountList;
		
	/**
	 * DefaultConstructor
	 */
	public AmountListResponse() {

	}

	public List<AmountTO> getAmountList() {
		return amountList;
	}

	public void setAmountList(List<AmountTO> amountList) {
		this.amountList = amountList;
	}

}