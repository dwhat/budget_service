package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.AmountTO;

/**
 * @date 19.05.2015
 * @author Marco
 * Klasse für eine Liste von AmountWithIdResponses Objecten
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