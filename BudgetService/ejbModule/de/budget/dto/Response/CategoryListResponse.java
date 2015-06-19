package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.CategoryTO;

/**
 * Klasse für eine Liste von CategoryTO Objecten als Antwort auf Anfragen
 * @author Marco
 *
 */
public class CategoryListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<CategoryTO> categoryList;
		
	/**
	 * DefaultConstructor
	 */
	public CategoryListResponse() {

	}

	public List<CategoryTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryTO> categoryList) {
		this.categoryList = categoryList;
	}

}
