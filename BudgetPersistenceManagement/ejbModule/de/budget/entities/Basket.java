package de.budget.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Basket Class
 * @author Marco
 * @date 12.05.2015
 */
@Entity
public class Basket implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id;
	
	private String notice;
	
	private double amount;
	
	private Timestamp createDate;
	
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
	
	
	@NotNull
	private Timestamp purchaseDate;
	
	//private document oid;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Payment payment;
	
	@ManyToOne
	private Vendor vendor;

	/**
	 * Bidirectional one to many relationship
	 * @author Marco
	 * @date 12.05.2015
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="basket")
	private List<Item> items;
	
	
	/**
	 * Default Constructor
	 * @author Marco
	 * @date 12.05.2015
	 */
	 public Basket() {
		 super();
	 }
	 
	 /**
	  * @author Marco
	  * @date 12.05.2015
	  * @param user
	  * @param payment
	  * @param vendor
	  */
	 public Basket(User user, Payment payment, Vendor vendor) {
		 this.user = user;
		 this.payment = payment;
		 this.vendor = vendor;
		 this.user.addNewBasket(this);
		 this.user.addNewPayment(payment);
		 this.user.addNewVendor(vendor);
		 this.createDate = new Timestamp(System.currentTimeMillis());
	 }

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @return the creationDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @param creationDate the creationDate to set
	 */
	public void setCreateDate(Timestamp creationDate) {
		this.createDate = creationDate;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @return the purchaseDate
	 */
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	/**
	 * @author Marco
	 * @return the user of the basket
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * @author Marco
	 * @return the payment of the basket
	 */
	public Payment getPayment() {
		return this.payment;
	}
	
	/**
	 * @author Marco
	 * @return the vendor of the basket
	 */
	public Vendor getVendor() {
		return this.vendor;
	}
	
	/**
	 * @author Marco
	 * @param vendor
	 */
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	/**
	 * @author Marco
	 * @param payment
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	/**
	 * @author Marco
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @author Marco
	 * @return a set with all Items of a basket
	 */
	public List<Item> getItems(){
		return this.items;
	}
	
	/**
	 * @author Marco
	 * @param items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	/**
	 * Method to add one item to this basket
	 * @author Marco
	 * @param newBasket
	 */
	public void addNewItem(Item newItem) {
		this.items.add(newItem);
	}
	
	/**
	 * Sums the prices of the items of this basket and sets the sum into the amount Variable
	 * @author Marco
	 * @date 17.05.2015
	 * @return amount of the basket
	 */
	public double sumAmount() {
		double amount = 0.0;
		Iterator<Item> it = items.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			amount = amount + item.getPrice();
		}
		this.amount = amount;
		return amount;
	}
	
	/**
	 * Gives the quantity of items of the basket
	 * @author Marco
	 * @date 17.05.2015
	 * @return quantity of Items
	 */
	public int countItems() {
		int quantity = 0;
		Iterator<Item> it = items.iterator();
		while (it.hasNext()) {
			quantity = quantity + 1;
		}
		return quantity;
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
}
