package de.budget.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 * Payment Class
 * @author Marco
 * @date 11.05.2015
 */
@Entity
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private String name;
	
	private String number;
	
	private String bic;
	
	private boolean active;
	
	private Date creationDate;
	
	@ManyToOne
	private User user;
	
	
	
	/**
	 * Default Constructor
	 * @author Marco
	 * @date 11.05.2015
	 */
	public Payment() {
		super();
	}
	
	/**
	 * Constructor
	 * @author Marco
	 * @date 11.05.2015
	 */
	public Payment(User user) {
		this.user = user;
		this.user.addNewPayment(this);
		this.creationDate = new Date();
	}
	
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @return the bic
	 */
	public String getBic() {
		return bic;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @param bic the bic to set
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @author Marco
	 * @date 11.05.2015
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	

}
