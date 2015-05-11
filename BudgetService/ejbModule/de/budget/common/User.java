package de.budget.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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


}