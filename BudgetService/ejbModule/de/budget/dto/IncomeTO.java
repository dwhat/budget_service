package de.budget.dto;

import java.io.Serializable;


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
	private long receiptDate;
	private CategoryTO category;
	
	
	/**
	 * Deafault Constructor
	 */
	public IncomeTO() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param quantity
	 * @param amount
	 * @param notice
	 * @param createDate
	 * @param lastChanged
	 * @param category
	 */
	public IncomeTO(int id, String name, double quantity, double amount, String notice, long receiptDate, CategoryTO category) {
		this.id = id;
		this.name = name;
		this.notice = notice;
		this.amount = amount;
		this.quantity = quantity;
		this.receiptDate = receiptDate;
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

	/**
	 * @return the receiptDate
	 */
	public long getReceiptDate() {
		return receiptDate;
	}

	/**
	 * @param receiptDate the receiptDate to set
	 */
	public void setReceiptDate(long receiptDate) {
		this.receiptDate = receiptDate;
	}



	
	
}
