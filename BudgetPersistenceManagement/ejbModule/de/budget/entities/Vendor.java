package de.budget.entities;

import java.io.Serializable;
import java.util.Date;


//Import Persistence
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
	
	private Date createDate;
	
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
	public Vendor (User user){
		this.user = user;
		this.user.addNewVendor(this);
		this.createDate = new Date();
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
