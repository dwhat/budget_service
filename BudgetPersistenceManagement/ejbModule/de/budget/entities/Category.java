package de.budget.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Category Class
 * @author Marco
 * @date 11.05.2015
 */
@Entity
public class Category implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private int id;
	
	@NotNull
	private String name;
	private String notice;
	private boolean active;
	private boolean income; //True, if IncomeCategory; false, if LossesCategory
	private Date createDate;
	@ManyToOne
	private User user;
	
	/**
	 * Default Constructor
	 * @author Marco
	 * @date 11.05.2015
	 */
	public Category() {
		super();
	}
	
	/**
	 * Constructor
	 * @author Marco
	 * @date 11.05.2015
	 */
	public Category(User user) {
		this.user = user;
		this.user.addNewCategory(this);
		this.createDate = new Date();
	}
	
	/**
	 * @author Marco
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @author Marco
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @author Marco
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * True, if IncomeCategory; false, if LossesCategory
	 * @author Marco
	 * @return the income
	 */
	public boolean isIncome() {
		return income;
	}

	/**
	 * True, if IncomeCategory; false, if LossesCategory
	 * @author Marco
	 * @param income the income to set
	 */
	public void setIncome(boolean income) {
		this.income = income;
	}

	/**
	 * @author Marco
	 * @return the creationDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @author Marco
	 * @param creationDate the creationDate to set
	 */
	public void setCreateDate(Date creationDate) {
		this.createDate = creationDate;
	}

	/**
	 * @author Marco
	 * @return the username
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @author Marco
	 * @param username the username to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @author Marco
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @author Marco
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	/**
	 * @author Marco
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
