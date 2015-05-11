package de.budget.entities;

import java.io.Serializable;


import javax.persistence.*;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userName;
	private String password;

	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	/**
	* Method to get the Username
	* @author Marco
	* @date 08.05.2015
	*/
	public String getUserName() {
		return userName;
	}

	/**
	* Method to get the password
	* @author Marco
	* @date 08.05.2015
	*/
	public String getPassword() {
		return password;
	}
	
	/**
	* Method to set the username
	* @author Marco
	* @date 08.05.2015
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	* Method to set the password
	* @author Marco
	* @date 08.05.2015
	*/
	public void setPassword(String password) {
		this.password = password;
	}


}