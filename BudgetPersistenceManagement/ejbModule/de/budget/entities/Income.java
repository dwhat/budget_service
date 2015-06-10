package de.budget.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.jboss.logging.Logger;

import de.budget.dao.BudgetOnlineDAO;

/**
 * @date 27.05.2015
 * @author Marco
 * Income Class
 */
/*
@NamedQueries( {
	@NamedQuery (
			name = "findLastIncomes",
			query = "select i from Income i where i.user.userName like :username order by i.receiptDate" 
			)
})
*/
@Entity
public class Income implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id;
	
	@NotNull
	private String name;
	
	private String notice;
	@NotNull
	private double quantity;
	@NotNull
	private double amount;
	
	@ManyToOne
	private User user;
	
	private Date receiptDate;
	
	private Date createDate;
	
	@ManyToOne
	private Category category;
	
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
	
	private static final Logger logger = Logger.getLogger(Income.class);
	/**
	 *@author Marco
	 *Default Constructor
	 */
	public Income() {
		super();
	}
	
	public Income(String name, String notice, double quantity, double amount,  Date receiptDate, Category category, User user) {
		this.user = user;
		this.name = name;
		this.notice = notice;
		this.quantity = quantity;
		this.amount = amount;
		this.receiptDate = receiptDate;
		this.category = category;
		this.createDate = new Date(System.currentTimeMillis());
		this.lastChanged = new Timestamp(System.currentTimeMillis());
		//this.user.addNewIncome(this);
		logger.info("xyz-Constructor angelegt");
		
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the launchDate
	 */

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the receiptDate
	 */
	public Date getReceiptDate() {
		return receiptDate;
	}

	/**
	 * @param receiptDate the receiptDate to set
	 */
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
}
