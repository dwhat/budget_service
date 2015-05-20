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
 * @author Marco
 * 11.05.2015
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
	 * @author Moritz
	 * @date 19.05.2015
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
	
	
	
	/**
	 * Default Constructor
	 * @author Marco
	 */
	public Vendor() {
		super();
	}
	/**
	 * Constructor
	 * @author Marco
	 * @param user
	 */
	public Vendor (User user, String name, String logo){
		this.user = user;
		this.name = name;
		this.logo = logo;
		this.user.addNewVendor(this);
		this.createDate = new Timestamp(System.currentTimeMillis());
		this.lastChanged = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * @author Marco
	 * @return the id
	 */
	public int getId() {
		return this.id;
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
	
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logoBase64) {
		logo = logoBase64;
	}
	
	/**
	 * @author Marco
	 * @return the creationDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @author Marco
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
	 * @author Marco
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @author Marco
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
