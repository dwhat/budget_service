package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.IncomeTO;

/**
 * Response for Incomes
 * @author Marco
 *
 */
public class IncomeListResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;

	private List<IncomeTO> incomeList;
	
	/**
	 * Default Constructor
	 */
	public IncomeListResponse() {
		
	}

	/**
	 * @return the incomeList
	 */
	public List<IncomeTO> getIncomeList() {
		return incomeList;
	}

	/**
	 * @param incomeList the incomeList to set
	 */
	public void setIncomeList(List<IncomeTO> incomeList) {
		this.incomeList = incomeList;
	}
}
