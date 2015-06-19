package de.budget.dto;

import java.io.Serializable;


/**
 * Class for the date transfer of an Item
 * @author Marco
 * 
 */
public class ItemTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;	
	private double quantity;	
	private double price;	
	private String notice;		
	private long createDate;	
	private long receiptDate;	
	private long lastChanged;
	private int basketId;
	private int categoryId;

	/**
	 * Deafault Constructor
	 */
	public ItemTO() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param createDate
	 * @param receiptDate
	 * @param lastChanged
	 * @param basketId
	 * @param categoryId
	 */
	public ItemTO(int id, String name, double quantity, double price, String notice, long createDate, long receiptDate, long lastChanged, int basketId, int categoryId) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.notice = notice;
		this.createDate = createDate;
		this.receiptDate = receiptDate;
		this.lastChanged = lastChanged;
		this.categoryId = categoryId;
		this.basketId = basketId;
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
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
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
	 * @return the basket
	 */
	public int getBasketId() {
		return basketId;
	}

	/**
	 * @param basketId the basket to set
	 */
	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}

	/**
	 * @return the category
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the category to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
