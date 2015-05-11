package de.budget.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * User class
 * @author Marco
 * @date 11.05.2015
 */
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String userName;
	
	private String password;
	
	private String email;
	
	private Date createDate;
	
	/**
	 * Bidirectional one to many relationship
	 * @author Marco
	 * @date 11.05.2015
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	@JoinColumn(name="User_FK")
	private Set<Category> categories;

	
	
	/**
	* Default Constructor
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
		this.createDate = new Date();
		this.categories = new HashSet<Category>();
	}
	
	/**
	* Method to get the username
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

	/**
	 * @author Marco
	 * @return a Set with all Categories of this User
	 */
	public Set<Category> getCategories(){
		return this.categories;
	}
	
	/**
	 * @author Marco
	 * @param categories
	 */
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	/**
	 * Method to add one category to this User
	 * @author Marco
	 * @param newCategory
	 */
	public void addNewCategory(Category newCategory) {
		this.categories.add(newCategory);
	}
	
	/**
	 * @author Marco
	 */
	@Override
	public String toString() {
		return this.userName;
	}
}