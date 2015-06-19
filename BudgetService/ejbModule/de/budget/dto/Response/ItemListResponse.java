package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.ItemTO;

/**
 * Klasse für eine Liste von ItemTO Objecten als Antwort auf Anfragen
 * @author Marco
 *
 */
public class ItemListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<ItemTO> itemList;
		
	/**
	 * DefaultConstructor
	 */
	public ItemListResponse() {

	}

	public List<ItemTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemTO> itemList) {
		this.itemList = itemList;
	}

}