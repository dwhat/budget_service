package de.budget.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	
	private Date creationDate;
	
	private Date pruchaseDate;
	
	//private document oid;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Payment payment;
	
	@ManyToOne
	private Vendor vendor;


	
	
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
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @return the pruchaseDate
	 */
	public Date getPruchaseDate() {
		return pruchaseDate;
	}

	/**
	 * @author Marco
	 * @date 12.05.2015
	 * @param pruchaseDate the pruchaseDate to set
	 */
	public void setPruchaseDate(Date pruchaseDate) {
		this.pruchaseDate = pruchaseDate;
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

}
