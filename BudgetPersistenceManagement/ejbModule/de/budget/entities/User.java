package de.budget.entities;

import java.io.Serializable;


import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.*;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userName;
	private String password;
	private String email;
	private Date createDate;

	/**
	* Dafault Cunstructor
	* @author Marco
	* @date 08.05.2015
	*/
	public User() {
		super();
	}
	
	/**
	* Constructor
	* @author Marco
	* @param userName
	* @param password
	* @date 08.05.2015
	*/
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.setCreateDate((Date) java.util.Calendar.getInstance().getTime());
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

	/**
	* Method to get the EmailAddress
	* @author Marco
	* @date 08.05.2015
	*/
	public String getEmail() {
		return this.email;
	}
	
	/**
	* Method to set the emailAddress
	* @author Marco
	* @date 08.05.2015
	*/
	public void setEmail(String adress) {
		this.email = adress;
	}

	/**
	* Method to set the date
	* @author Marco
	* @date 08.05.2015
	*/
	public Date getCreateDate() {
		return createDate;
	}

	/**
	* Method to set the date
	* @author Marco
	* @date 08.05.2015
	*/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}