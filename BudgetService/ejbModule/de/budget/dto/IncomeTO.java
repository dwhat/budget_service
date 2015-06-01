package de.budget.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 28.05.2015
 * @author Marco
 * Class for the date transfer of an Income
 */
public class IncomeTO implements Serializable {


	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String notice;
	private double amount;
	private double quantity;
	private int period;
	private UserTO user;
	private Timestamp launchDate;
	private Timestamp finishDate;
	private Timestamp createDate;
	private Timestamp lastChanged;
	private CategoryTO category;
	
	
	/**
	 * Deafault Constructor
	 * @author Marco
	 * @date 28.05.2015
	 */
	public IncomeTO() {
		
	}
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 * @param quantity
	 * @param amount
	 * @param notice
	 * @param period
	 * @param createDate
	 * @param launchDate
	 * @param finishDate
	 * @param lastChanged
	 * @param user
	 * @param category
	 */
	public IncomeTO(int id, String name, double quantity, double amount, String notice, int period, Timestamp createDate, Timestamp launchDate, Timestamp finishDate, Timestamp lastChanged, UserTO user, CategoryTO category) {
		this.id = id;
		this.name = name;
		this.notice = notice;
		this.amount = amount;
		this.quantity = quantity;
		this.period = period;
		this.user = user;
		this.launchDate = launchDate;
		this.finishDate = finishDate;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.category = category;
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
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @return the user
	 */
	public UserTO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserTO user) {
		this.user = user;
	}

	/**
	 * @return the launchDate
	 */
	public Timestamp getLaunchDate() {
		return launchDate;
	}

	/**
	 * @param launchDate the launchDate to set
	 */
	public void setLaunchDate(Timestamp launchDate) {
		this.launchDate = launchDate;
	}

	/**
	 * @return the finishDate
	 */
	public Timestamp getFinishDate() {
		return finishDate;
	}

	/**
	 * @param finishDate the finishDate to set
	 */
	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
	}

	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastChanged
	 */
	public Timestamp getLastChanged() {
		return lastChanged;
	}

	/**
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(Timestamp lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * @return the category
	 */
	public CategoryTO getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(CategoryTO category) {
		this.category = category;
	}
	
	
}
