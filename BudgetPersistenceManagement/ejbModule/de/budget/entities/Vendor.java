package de.budget.entities;

import java.io.Serializable;
import java.sql.Timestamp;



//Import Persistence
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

//Import Base64 // libsOrdner 
//import org.apache.commons.codec.binary.Base64;

/**
 * Vendor class
 * Vendor Entity
 * @author Marco
 */
@Entity
public class Vendor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id; 
	
	@NotNull
	private String name;
	
	private Timestamp createDate;
	
	/**
	 * <p> Author: Moritz</p>
	 * Optimistischer Locking Ansatz
	 * Benutzer können nun parallel Daten einsehen, aber nicht parallel ändern
	 * Im Zweifall -> Exception (Datenintegriät und Datensicherheit Vorteil)
	 * 
	 */
	@Version
	private Timestamp lastChanged;
	
	
	@ManyToOne
	private User user;
	
	//wir verwenden einen base64 string
	private String logo;
	
	private String street;
	
	private String city;
	
	private int houseNumber; //streetnumber
	
	private int PLZ;
	
	
	
	/**
	 * Default Constructor
	 * <p> Author: Marco </p>
	 */
	public Vendor() {
		super();
	}
	/**
	 * Constructor
	 * <p> Author: Marco </p>
	 * @param user
	 */
	public Vendor (User user, String name, String logo, String street, String city, int PLZ, int houseNumber){
		this.user = user;
		this.name = name;
		this.logo = logo;
		this.street = street;
		this.city = city;
		this.houseNumber = houseNumber;
		this.PLZ = PLZ;
		this.user.addNewVendor(this);
		this.createDate = new Timestamp(System.currentTimeMillis());
		this.lastChanged = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * <p> Author: Marco </p>
	 * @return the id
	 */
	public int getId() {
		return this.id;
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
	
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logoBase64) {
		logo = logoBase64;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * <p> Author: Marco </p>
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
