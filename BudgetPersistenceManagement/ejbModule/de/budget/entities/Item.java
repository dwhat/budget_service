package de.budget.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Item Class
 * @author Marco
 * 12.05.2015
 */
@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id;
	
	@NotNull
	private String name;
	@NotNull
	private double quantity;
	@NotNull
	private double price;
	
	private String notice;
	
	private Timestamp createDate;
	
	private Timestamp receiptDate;
	
	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * Optimistischer Locking Ansatz
	 * Benutzer können nun parallel Daten einsehen, aber nicht parallel ändern
	 * Im Zweifall -> Exception (Datenintegriät und Datensicherheit Vorteil)
	 * 
	 */
	@Version
	private Timestamp lastChanged;
	
	
	@ManyToOne(targetEntity=Basket.class)
	private Basket basket;
	
	@ManyToOne(targetEntity=Category.class)
	private Category category;
	
	
	/**
	 * Default Constructor
	 * @author Marco
	 */
	public Item() {
		super();
	}
	
	/**
	 * Constructor
	 * @author Marco
	 * @param basket 
	 * @param category 
	 */
	public Item(String name, double  quantity, double price, String notice, Timestamp receiptDate, Basket basket, Category category) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.notice = notice;
		this.receiptDate = receiptDate;
		this.category = category;
		this.basket = basket;
		this.createDate = new Timestamp(System.currentTimeMillis());
		this.lastChanged = new Timestamp(System.currentTimeMillis());
		this.basket.addNewItem(this);
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
	 * @author Marco
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @author Marco
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @author Marco
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}
	
	/**
	 * @author Marco
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * @author Marco
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @author Marco
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @author Marco
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}
	
	/**
	 * @author Marco
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	/**
	 * @author Marco
	 * @return the creationDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	/**
	 * @author Marco
	 * @param creationDate the creationDate to set
	 */
	public void setCreateDate(Timestamp creationDate) {
		this.createDate = creationDate;
	}
	

	/**
	 * @author Marco
	 * @return the basket
	 */
	public Basket getBasket() {
		return basket;
	}
	
	/**
	 * @author Marco
	 * @param basket the basket to set
	 */
	public void setBasket(Basket basket) {
		this.basket = basket;
	}
	
	/**
	 * @author Marco
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
	
	/**
	 * @author Marco
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
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
	 * @return the receiptDate
	 */
	public Timestamp getReceiptDate() {
		return receiptDate;
	}

	/**
	 * @param receiptDate the receiptDate to set
	 */
	public void setReceiptDate(Timestamp receiptDate) {
		this.receiptDate = receiptDate;
	}


}
