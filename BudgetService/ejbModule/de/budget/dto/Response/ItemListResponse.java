package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.ItemTO;

/**
 * @date 19.05.2015
 * @author Marco
 *Klasse für eine Liste von ItemTO Objecten als Antwort auf Anfragen
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