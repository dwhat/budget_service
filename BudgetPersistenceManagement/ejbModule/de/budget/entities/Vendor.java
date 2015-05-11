package de.budget.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * Vendor class
 * @author Marco
 * 11.05.2015
 */
@Entity
public class Vendor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id; 
	
	private String name;
	
	private Date creationDate;
	
	@ManyToOne
	private User user;
	//private LOGO????
	
	
	
	/**
	 * Default Constructor
	 * @author Marco
	 */
	public Vendor() {
		super();
	}
	/**
	 * Constructor
	 * @author Marco
	 * @param user
	 */
	public Vendor (User user){
		this.user = user;
		this.user.addNewVendor(this);
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @author Marco
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
