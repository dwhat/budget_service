package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.BasketTO;

/**
 * Klasse für eine Liste von basketTO Objecten als Antwort auf Anfragen
 * @author Marco
 *
 */
public class BasketListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<BasketTO> basketList;
		
	/**
	 * DefaultConstructor
	 */
	public BasketListResponse() {

	}

	public List<BasketTO> getBasketList() {
		return basketList;
	}

	public void setBasketList(List<BasketTO> basketList) {
		this.basketList = basketList;
	}

}
