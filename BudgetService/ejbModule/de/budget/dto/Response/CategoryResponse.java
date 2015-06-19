package de.budget.dto.Response;

import de.budget.dto.CategoryTO;


/**
 * Class for the Category response
 * @author Marco
 */
public class CategoryResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
		
	private CategoryTO categoryTo;

	
	public CategoryResponse() {
			
	}

	/**
	 * @return the categoryTo
	 */
	public CategoryTO getCategoryTo() {
		return categoryTo;
	}

	/**
	 * @param categoryTo the categoryTo to set
	 */
	public void setCategoryTo(CategoryTO categoryTo) {
		this.categoryTo = categoryTo;
	}

}
