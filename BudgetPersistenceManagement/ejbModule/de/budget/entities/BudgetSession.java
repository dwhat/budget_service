package de.budget.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * BudgetSession for the Sessionhandling
 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
 */
@Entity
public class BudgetSession {

	@Id @GeneratedValue
	private int id;
	private String username;
	private Date creationDate;
	
	/**
	* Constructor
	* @author Marco
	* @param user
	* @date 08.05.2015
	*/
	public BudgetSession(User user) {
		this.username = user.getUserName();
		this.creationDate = new Date();
	}
	
	/**
	* Default Constructor
	* @author Marco
	* @date 08.05.2015
	*/
	public BudgetSession() {
		this.creationDate = new Date();
	}

	/**
	* Method to get the Session Id
	* @author Marco
	* @date 08.05.2015
	*/
	public int getId() {
		return id;
	}

	/**
	* Method to set the SessionId
	* @author Marco
	* @param id
	* @date 08.05.2015
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	* Method to get the username
	* @author Marco
	* @date 08.05.2015
	*/
	public String getUsername() {
		return this.username;
	}

	/**
	* Method to set the username
	* @author Marco
	* @param username
	* @date 08.05.2015
	*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	* Method to get the creationtime
	* @author Marco
	* @date 08.05.2015
	*/
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	* Method to set the creationtime
	* @param creationTime
	* @author Marco
	* @date 08.05.2015
	*/
	public void setCreationDate(Date creationTime) {
		this.creationDate = creationTime;
	}
}
