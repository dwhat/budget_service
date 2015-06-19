package de.budget.dto;

import java.io.Serializable;
/**
 * Class for the DTO-Objects of a vendor
 * @author Marco
 *
 */
public class VendorTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id; 
	private String name;
	private long createDate;
	private long lastChanged;
	private String logo;
	private String street;
	private String city;
	private int PLZ;
	private int houseNumber;

	/**
	 * Deafault Constructor
	 */
	public VendorTO() {
		
	}
	
	/**
	 * Constructor
	 * @param id
	 * @param name
	 * @param createDate
	 * @param lastChanged
	 * @param logo
	 */
	public VendorTO(int id, String name, long createDate, long lastChanged, String logo, String street, String city, int PLZ, int houseNumber) {
		this.id = id;
		this.name = name;
		this.createDate = createDate;
		this.lastChanged = lastChanged;
		this.logo = logo;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.PLZ = PLZ;
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


	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the pLZ
	 */
	public int getPLZ() {
		return PLZ;
	}

	/**
	 * @param pLZ the pLZ to set
	 */
	public void setPLZ(int pLZ) {
		PLZ = pLZ;
	}

	/**
	 * @return the houseNumber
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * @param houseNumber the houseNumber to set
	 */
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
}
