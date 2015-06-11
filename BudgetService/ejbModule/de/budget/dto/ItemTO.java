package de.budget.dto;

import java.io.Serializable;


/**
 * @date 18.05.2015
 * @author Marco
 * Class for the date transfer of an Item
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
	 * @author Marco
	 * @date 19.05.2015
	 */
	public ItemTO() {
		
	}
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param createDate
	 * @param receiptDate
	 * @param lastChanged
	 * @param basket
	 * @param category
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
	 * @author Marco
	 * @date 19.05.2015
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the lastChanged
	 */
	public long getLastChanged() {
		return lastChanged;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(long lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the basket
	 */
	public int getBasketId() {
		return basketId;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param basket the basket to set
	 */
	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @return the category
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param category the category to set
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
