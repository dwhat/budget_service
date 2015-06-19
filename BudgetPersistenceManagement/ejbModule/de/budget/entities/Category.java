package de.budget.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Category Entity Class
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
	@NotNull
	private boolean income; //True, if IncomeCategory; false, if LossesCategory
	
	private String colour;
	
	private Timestamp createDate;
	
	/**
	 * <p> Author: Moritz </p>
	 * Optimistischer Locking Ansatz
	 * Benutzer können nun parallel Daten einsehen, aber nicht parallel ändern
	 * Im Zweifall -> Exception (Datenintegriät und Datensicherheit Vorteil)
	 * 
	 */
	@Version
	private Timestamp lastChanged;
	
	@ManyToOne
	private User user;
	
	/**
	 * Default Constructor
	 * <p> Author: Marco </p>
	 * @date 11.05.2015
	 */
	public Category() {
		super();
	}
	
	/**
	 * Constructor
	 * <p> Author: Marco </p>
	 * @date 11.05.2015
	 */
	public Category(User user, boolean income, String name, String notice, String colour) {
		this.user = user;
		this.income = income;
		this.name = name;
		this.notice = notice;
		this.active = true;
		this.setColour(colour);
		this.user.addNewCategory(this);
		this.createDate = new Timestamp(System.currentTimeMillis());
		this.lastChanged = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * <p> Author: Marco </p>
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <p> Author: Marco </p>
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p> Author: Marco </p>
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * True, if IncomeCategory; false, if LossesCategory
	 * <p> Author: Marco </p>
	 * @return the income
	 */
	public boolean isIncome() {
		return income;
	}

	/**
	 * True, if IncomeCategory; false, if LossesCategory
	 * <p> Author: Marco </p>
	 * @param income the income to set
	 */
	public void setIncome(boolean income) {
		this.income = income;
	}

	/**
	 * @return the colour
	 */
	public String getColour() {
		return colour;
	}

	/**
	 * @param colour the colour to set
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}

	/**
	 * <p> Author: Marco </p>
	 * @return the creationDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param creationDate the creationDate to set
	 */
	public void setCreateDate(Timestamp creationDate) {
		this.createDate = creationDate;
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
	 * <p> Author: Marco </p>
	 * @return the username
	 */
	public User getUser() {
		return user;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param username the username to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * <p> Author: Marco </p>
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * <p> Author: Marco </p>
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	/**
	 * <p> Author: Marco </p>
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
