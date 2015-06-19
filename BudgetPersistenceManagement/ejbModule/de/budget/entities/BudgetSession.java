package de.budget.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * BudgetSession for the SessionEntities
 * @author Marco
 * 
 */

@NamedQueries( {
	@NamedQuery (
			name = "findOldSessions",
			query = "select b from BudgetSession b where b.createDate <= :date"
			)
})
@Entity
public class BudgetSession {

	@Id @GeneratedValue
	private int id;
	private String username;
	private Timestamp createDate;
	
	/**
	* Constructor
	* <p> Author: Marco </p>
	* @param user
	*/
	public BudgetSession(User user) {
		this.username = user.getUserName();
		this.createDate = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	* Default Constructor
	* <p> Author: Marco </p>
	*/
	public BudgetSession() {
		this.createDate = new Timestamp(System.currentTimeMillis());
	}

	/**
	* Method to get the Session Id
	* <p> Author: Marco </p>
	*/
	public int getId() {
		return id;
	}

	/**
	* Method to set the SessionId
	* <p> Author: Marco </p>
	* @param id
	*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	* Method to get the username
	* <p> Author: Marco </p>
	*/
	public String getUsername() {
		return this.username;
	}

	/**
	* Method to set the username
	* <p> Author: Marco </p>
	* @param username
	*/
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	* Method to get the creationtime
	* <p> Author: Marco </p>
	*/
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	* Method to set the creationtime
	* @param createDate
	* <p> Author: Marco </p>
	*/
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}
