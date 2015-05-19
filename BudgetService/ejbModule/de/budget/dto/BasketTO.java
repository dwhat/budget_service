package de.budget.dto;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @date 18.05.2015
 * @author Marco
 * Class for the date transfer of a basket
 */
public class BasketTO implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private int id;
	private String notice;
	private double amount;
	private Timestamp createDate;
	private Timestamp purchaseDate;
	private Timestamp lastChanged;
	private String user;
	private int vendor;
	private int payment;
	
	/**
	 * Default Constructor
	 * @author Marco
	 */
	public BasketTO() {
		
	}
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param id
	 * @param notice
	 * @param amount
	 * @param createDate
	 * @param purchaseDate
	 * @param lastChanged
	 * @param user
	 * @param vendor
	 * @param payment
	 */
	public BasketTO(int id, String notice, double amount, Timestamp createDate, Timestamp purchaseDate, Timestamp lastChanged, String user, int vendor, int payment) {
		super();
		this.setId(id);
		this.setNotice(notice);
		this.setAmount(amount);
		this.setCreateDate(createDate);
		this.setPurchaseDate(purchaseDate);
		this.setLastChanged(lastChanged);
		this.setUser(user);
		this.setVendor(vendor);
		this.setPayment(payment);
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
	 * @return the purchaseDate
	 */
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
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
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the vendor
	 */
	public int getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(int vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the payment
	 */
	public int getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(int payment) {
		this.payment = payment;
	}
	
}