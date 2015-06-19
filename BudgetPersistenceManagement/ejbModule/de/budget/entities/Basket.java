package de.budget.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Basket Class
 * Enity for Baskets
 * @author Marco
 * @date 12.05.2015
 */
@NamedQueries( {
	@NamedQuery (
			name = "findLastBaskets",
			query = "select b from Basket b where b.user.userName like :username order by b.purchaseDate"
			),
	@NamedQuery (
			name = "findBasketsOfMonth",
			query = "select b from Basket b where b.user.userName like :username AND b.purchaseDate >= :date"
			)
})
@Entity
public class Basket implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id;
	
	@NotNull
	private String name; 
	
	private String notice;
	@NotNull
	private double amount;
	
	private Timestamp createDate;
	
	/**
	 * <p> Author: Moritz </p>
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
	
	@ManyToOne(targetEntity=Payment.class)
	private Payment payment;
	
	@ManyToOne(targetEntity=Vendor.class)
	private Vendor vendor;

	/**
	 * Bidirectional one to many relationship
	 * <p> Author: Marco </p>
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="basket")
	private List<Item> items;
	
	
	/**
	 * Default Constructor
	 * <p> Author: Marco </p>
	 */
	 public Basket() {
		 super();
	 }
	 
	 /**
	  * <p> Author: Marco </p>
	  * @param user
	  * @param payment
	  * @param vendor
	  */
	 public Basket(User user, String name, String notice, double amount, Timestamp purchaseDate, Payment payment, Vendor vendor) {
		 this.user = user;
		 this.notice = notice;
		 this.amount = amount;
		 this.payment = payment;
		 this.name = name;
		 this.vendor = vendor;
		 this.purchaseDate = purchaseDate;
		 this.items = new ArrayList<Item>();
		 this.createDate = new Timestamp(System.currentTimeMillis());
		 this.lastChanged = new Timestamp(System.currentTimeMillis());
		 this.user.addNewBasket(this);
	 }
	 
	 /**
	  * <p> Author: Marco </p>
	  * @param user
	  * @param payment
	  * @param vendor
	  */
	 public Basket(User user, String name, String notice, double amount, Timestamp purchaseDate, Payment payment, Vendor vendor, List<Item> items) {
		 this.user = user;
		 this.notice = notice;
		 this.amount = amount;
		 this.payment = payment;
		 this.name = name;
		 this.vendor = vendor;
		 this.purchaseDate = purchaseDate;
		 this.user.addNewBasket(this);
		 this.user.addNewPayment(payment);
		 this.user.addNewVendor(vendor);
		 this.items = items;
		 this.createDate = new Timestamp(System.currentTimeMillis());
		 this.lastChanged = new Timestamp(System.currentTimeMillis());
	 }

	/**
	 * <p> Author: Marco </p>
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @return the name
	 * <p> Author: Marco </p> 
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * method to sum the amount and returns this value
	 * <p> Author: Marco </p>
	 * @return the amount
	 */
	public double getAmount() {
		this.amount = sumAmount();
		return amount;
	}

	/**
	 * <p> Author: Marco </p>5
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * <p> Author: Marco </p>
	 * @return the creationDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param creationDate the creationDate to set
	 */
	public void setCreateDate(Timestamp creationDate) {
		this.createDate = creationDate;
	}

	/**
	 * <p> Author: Marco </p>
	 * @return the purchaseDate
	 */
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @return the user of the basket
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @return the payment of the basket
	 */
	public Payment getPayment() {
		return this.payment;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @return the vendor of the basket
	 */
	public Vendor getVendor() {
		return this.vendor;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @param vendor
	 */
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @param payment
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * <p> Author: Moritz </p>
	 * @param itemId
	 * @return
	 */
	public Item getItem(int itemId)
	{
		for (Item i : this.items) {
			if (i.getId()== itemId) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @return a set with all Items of a basket
	 */
	public List<Item> getItems(){
		return this.items;
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @param items
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	/**
	 * Method to add one item to this basket
	 * <p> Author: Marco </p>
	 * @param newBasket
	 */
	public void addNewItem(Item newItem) {
		this.items.add(newItem);
	}
	
	/**
	 * Sums the prices of the items of this basket and sets the sum into the amount Variable
	 * <p> Author: Marco </p>
	 * @return amount of the basket
	 */
	private double sumAmount() {
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
	 * <p> Author: Marco </p>
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
