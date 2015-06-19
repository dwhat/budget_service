package de.budget.dto;

import java.io.Serializable;

/**
 * Class for the date transfer of a category
 * @author Marco
 * 
 */
public class CategoryTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String notice;
	private boolean active;
	private boolean income; //True, if IncomeCategory; false, if LossesCategory
	private long createDate;
	private long lastChanged;
	private String colour;
	
	/**
	 * Default Constructor
	 */
	public CategoryTO() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param notice
	 * @param active
	 * @param income
	 * @param createDate
	 * @param lastChanged
	 * @param colour    String with colourcode (FF00DD)
	 */
	public CategoryTO(int id, String name, String notice, boolean active, boolean income, String colour, long createDate, long lastChanged) {
		super();
		this.id = id;
		this.name = name;
		this.notice = notice;
		this.colour = colour;
		this.active = active;
		this.income = income;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}
	/**
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the income
	 */
	public boolean isIncome() {
		return income;
	}
	/**
	 * @param income the income to set
	 */
	public void setIncome(boolean income) {
		this.income = income;
	}
	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the lastChanged
	 */
	public long getLastChanged() {
		return lastChanged;
	}

	/**
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(long lastChanged) {
		this.lastChanged = lastChanged;
	}


	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}

}
