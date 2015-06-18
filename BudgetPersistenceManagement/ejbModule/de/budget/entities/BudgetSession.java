package de.budget.entities;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * BudgetSession for the Sessionhandling

 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
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
	* @author Marco
	* @param user
	* @date 08.05.2015
	*/
	public BudgetSession(User user) {
		this.username = user.getUserName();
		this.createDate = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	* Default Constructor
	* @author Marco
	* @date 08.05.2015
	*/
	public BudgetSession() {
		this.createDate = new Timestamp(System.currentTimeMillis());
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
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	* Method to set the creationtime
	* @param creationTime
	* @author Marco
	* @date 08.05.2015
	*/
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}
