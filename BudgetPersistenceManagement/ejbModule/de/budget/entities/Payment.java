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
 * Payment Class
 * @author Marco
 */
@Entity
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private int id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String number;
	
	@NotNull
	private String bic;
	
	private boolean active;
	
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
	 */
	public Payment() {
		super();
	}
	
	/**
	 * Constructor
	 * <p> Author: Marco </p>
	 */
	public Payment(User user, String name, String number, String bic) {
		this.user = user;
		this.name = name;
		this.number = number;
		this.bic = bic;
		this.active = true;
		this.user.addNewPayment(this);
		this.lastChanged = new Timestamp(System.currentTimeMillis());
		this.createDate = new Timestamp(System.currentTimeMillis());
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
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * <p> Author: Marco </p>
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * <p> Author: Marco </p>
	 * @return the bic
	 */
	public String getBic() {
		return bic;
	}
	/**
	 * <p> Author: Marco </p>
	 * @param bic the bic to set
	 */
	public void setBic(String bic) {
		this.bic = bic;
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
	public void setCreationDate(Timestamp creationDate) {
		this.createDate = creationDate;
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
	

}
