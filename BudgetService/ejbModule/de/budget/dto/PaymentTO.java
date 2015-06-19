package de.budget.dto;

import java.io.Serializable;



/**
 * Class for the date transfer of a payment
 * @author Marco
 * 
 */
public class PaymentTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String number;
	private String bic;
	private boolean active;
	private long createDate;
	private long lastChanged;

	
	/**
	 * Default Constructor
	 */
	public PaymentTO() {
		
	}
	
	/**
	 * Constructor for class PaymentTO
	 * @param id
	 * @param name
	 * @param number
	 * @param bic
	 * @param active
	 * @param createDate
	 * @param lastChanged
	 */
	public PaymentTO(int id, String name, String number, String bic, boolean active, long createDate, long lastChanged) {
		this.id = id;
		this.name = name;
		this.bic = bic; 
		this.active = active;
		this.createDate = createDate;
		this.lastChanged = lastChanged;

		
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the bic
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * @param bic the bic to set
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the lastChanged
	 */
	public long getLastChanged() {
		return lastChanged;
	}

	/**
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(long lastChanged) {
		this.lastChanged = lastChanged;
	}

	
}
