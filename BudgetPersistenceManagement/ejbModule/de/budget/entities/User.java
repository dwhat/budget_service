package de.budget.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * User class
 * @author Marco
 */
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Size(min=1,max=150)
	private String userName;
	
	@NotNull
	@Size(min=8,max=150)
	private String password;
	
	@NotNull
	@Size(min=1,max=250)
	private String email;
	
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
	
	/**
	 * Bidirectional one to many relationship
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private List<Category> categories;
	
	/**
	 * Bidirectional one to many relationship
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private List<Payment> payments;
	
	/**
	 * Bidirectional one to many relationship
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private List<Income> incomes;
	
	/**
	 * Bidirectional one to many relationship
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private List<Vendor> vendors;
	
	/**
	 * Bidirectional one to many relationship
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private List<Basket> baskets;

	
	
	/**
	* Default Constructor
	*/
	public User() {
		super();
	}
	
	/**
	* Constructor
	* @param userName
	* @param password
	*/
	public User(String userName, String password, String email) {
		this.userName = userName.toLowerCase();
		this.password = password;
		this.email = email;
		this.createDate = new Timestamp(System.currentTimeMillis());
		this.categories = new ArrayList<Category>();
		this.payments = new ArrayList<Payment>();
		this.vendors = new ArrayList<Vendor>();
		this.baskets = new ArrayList<Basket>();
	}
	
	/**
	* Method to get the username
	*/
	public String getUserName() {
		return userName;
	}

	/**
	* Method to get the password
	*/
	public String getPassword() {
		return password;
	}
	
	/**
	* Method to set the username
	*/
	public void setUserName(String userName) {
		this.userName = userName.toLowerCase();
	}

	/**
	* Method to set the password
	*/
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	* Method to get the EmailAddress
	*/
	public String getEmail() {
		return this.email;
	}
	
	/**
	* Method to set the emailAddress
	*/
	public void setEmail(String adress) {
		this.email = adress;
	}

	/**
	* Method to set the date
	*/
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	* Method to set the date
	*/
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return a Set with all Categories of this User
	 */
	public List<Category> getCategories(){
		return this.categories;
	}
	
	/**
	 * @param categories
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	/**
	 * Method to get an specific CategoryObject
	 * @param categoryId
	 * @return Category Object
	 */
	public Category getCategory(int categoryId) {
		for (Category c : this.categories) {
			if (c.getId() == categoryId) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Method to add one category to this User
	 * @param newCategory
	 */
	public void addNewCategory(Category newCategory) {
		this.categories.add(newCategory);
	}
	
	/**
	 * @return a Set with all Payments of this User
	 */
	public List<Payment> getPayments(){
		return this.payments;
	}
	
	/**
	 * Method to get an specific PaymentObject
	 * @param paymentId
	 * @return Payment Object
	 */
	public Payment getPayment(int paymentId) {
		for (Payment p : this.payments) {
			if (p.getId()== paymentId) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @param payments
	 */
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	/**
	 * Method to add one Payment to this User
	 * @param newPayment
	 */
	public void addNewPayment(Payment newPayment) {
		this.payments.add(newPayment);
	}
	
	/**
	 * @return the incomes
	 */
	public List<Income> getIncomes() {
		return incomes;
	}

	/**
	 * Method to get an specific IncomeObject
	 * @param incomeId
	 * @return Income Object
	 */
	public Income getIncome(int incomeId) {
		for (Income i : this.incomes) {
			if (i.getId()== incomeId) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * @param incomes the incomes to set
	 */
	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}

	/**
	 * @param newIncome
	 */
	public void addNewIncome(Income newIncome) {
		this.incomes.add(newIncome);
	}
	
	/**
	 * @return a List with all Vendors of this User
	 */
	public List<Vendor> getVendors(){
		return this.vendors;
	}
	
	/**
	 * Method to get an specific VendorObject
	 * @param vendorId
	 * @return Vendor Object
	 */
	public Vendor getVendor(int vendorId) {
		for (Vendor v : this.vendors) {
			if (v.getId()== vendorId) {
				return v;
			}
		}
		return null;
	}
	
	/**
	 * @param vendors
	 */
	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}
	
	/**
	 * Method to add one Payment to this User
	 * @param newVendor
	 */
	public void addNewVendor(Vendor newVendor) {
		this.vendors.add(newVendor);
	}
	
	/**
	 * @return a Set with all Baskets of this User
	 */
	public List<Basket> getBaskets(){
		return this.baskets;
	}
	
	/**
	 * Method to get an specific BasketObject
	 * @param basketId
	 * @return Basket Object
	 */
	public Basket getBasket(int basketId) {
		for (Basket b : this.baskets) {
			if (b.getId()== basketId) {
				return b;
			}
		}
		return null;
	}
	
	/**
	 * @param baskets
	 */
	public void setBaskets(List<Basket> baskets) {
		this.baskets = baskets;
	}
	
	/**
	 * Method to add one basket to this User
	 * @param newBasket
	 */
	public void addNewBasket(Basket newBasket) {
		this.baskets.add(newBasket);
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


	@Override
	public String toString() {
		return this.userName;
	}
}