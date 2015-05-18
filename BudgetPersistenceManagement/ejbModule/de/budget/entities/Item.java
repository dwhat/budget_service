package de.budget.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	
	private String name;
	
	private double quantity;
	
	private double price;
	
	private String notice;
	
	private boolean active; //true, if Income; false, if los
	
	private int period;
	
	private Date creationDate;
	
	private Date launchDate;
	
	private Date finishDate;
	
	@ManyToOne
	private Basket basket;
	
	@ManyToOne
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
	public Item(Basket basket, Category category) {
		this.category = category;
		this.basket = basket;
		this.basket.addNewItem(this);
		this.basket.getUser().addNewCategory(category);
		this.creationDate = new Date();
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * @author Marco
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @author Marco
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}
	
	/**
	 * @author Marco
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}
	
	/**
	 * @author Marco
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * @author Marco
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * @author Marco
	 * @return the launchDate
	 */
	public Date getLaunchDate() {
		return launchDate;
	}
	
	/**
	 * @author Marco
	 * @param launchDate the launchDate to set
	 */
	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}
	
	/**
	 * @author Marco
	 * @return the finishDate
	 */
	public Date getFinishDate() {
		return finishDate;
	}
	
	/**
	 * @author Marco
	 * @param finishDate the finishDate to set
	 */
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
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


}
