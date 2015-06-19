package de.budget.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Local;



import javax.persistence.EntityExistsException;

import de.budget.entities.Basket;
import de.budget.entities.BudgetSession;
import de.budget.entities.Category;
import de.budget.entities.Income;
import de.budget.entities.Payment;
import de.budget.entities.User;
import de.budget.entities.Vendor;
import de.budget.entities.Item;


/**
 * Interface for Data access object, welches für den Datenbankzugriff über JPA verantwortlich ist
 * 
 * @author Marco
 * @author Moritz
 * @date 15.05.2015 bis 10.06.2015
 * 
 */
@Local
public interface BudgetOnlineDAOLocal {
	
	/**
	 * Method to create a user
	 * @param username
	 * @param password
	 * @param email
	 * @return new User
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public User createUser(String username, String password, String email) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update a user
	 * @param user
	 * @return updated User object
	 * @throws IllegalArgumentException
	 */
	public User updateUser(User user) throws IllegalArgumentException;
	
	/**
	 * Method to find a user
	 * @param userName
	 * @return User object
	 * @throws IllegalArgumentException
	 */
	public User findUserByName(String userName) throws IllegalArgumentException;
	
	/**
	 * Method to delete an user
	 * @param username
	 * @throws IllegalArgumentException
	 */
	public void deleteUser(String username) throws IllegalArgumentException;
	
	
	/**
	 * Method to create a new session
	 * @param userObject
	 * @return sessionId
	 * @throws EntityExistsException
	 * @throws IllegalArgumentException
	 */
	public int createSession(User userObject) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to find a session with the id
	 * @param sessionId
	 * @return Session Object
	 * @throws IllegalArgumentException
	 */
	public BudgetSession findSessionById(int sessionId) throws IllegalArgumentException;
	
	/**
	 * Method to delete a session
	 * @param sessionId
	 * @throws IllegalArgumentException
	 */
	public void closeSession(int sessionId) throws IllegalArgumentException;
	
	/**
	 * Method to gets all old sessions
	 * @param dayBefore
	 * @return List with all old sessions
	 * @throws IllegalArgumentException
	 */
	public List<BudgetSession> getOldSessions(Timestamp dayBefore) throws IllegalArgumentException;
	
	/**
	 * Method to create a vendor
	 * @param user
	 * @param name
	 * @param logo
	 * @param street
	 * @param city
	 * @param PLZ
	 * @param houseNumber
	 * @return new Vendor
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public Vendor createVendor(User user, String name, String logo, String street, String city, int PLZ, int houseNumber) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update a vendor
	 * @param vendor
	 * @return updated vendor
	 * @throws IllegalArgumentException
	 */
	public Vendor updateVendor(Vendor vendor) throws IllegalArgumentException;
	
	/**
	 * Method to find a vendor with the id
	 * @param vendorId
	 * @return vendor object
	 * @throws IllegalArgumentException
	 */
	public Vendor findVendorById(int vendorId) throws IllegalArgumentException;
	
	/**
	 * method to deletes a vendor
	 * @param vendorId
	 * @throws IllegalArgumentException
	 */
	public void deleteVendor(int vendorId) throws IllegalArgumentException;
	
	
	/**
	 * Method to create a basket
	 * @param user
	 * @param name
	 * @param notice
	 * @param amount
	 * @param purchaseDate
	 * @param payment
	 * @param vendor
	 * @param items
	 * @return new basket Object
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public Basket createBasket(User user, String name, String notice, double amount, Timestamp purchaseDate,Payment payment, Vendor vendor, List<Item> items) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update a basket
	 * @param basket
	 * @return updated basket
	 * @throws IllegalArgumentException
	 */
	public Basket updateBasket(Basket basket) throws IllegalArgumentException;
	
	/**
	 * Method to find a basket with the is
	 * @param basketId
	 * @return basket Object
	 * @throws IllegalArgumentException
	 */
	public Basket findBasketById(int basketId) throws IllegalArgumentException;
	
	/**
	 * Method to deletes a basket
	 * @param basketId
	 * @throws IllegalArgumentException
	 */
	public void deleteBasket(int basketId) throws IllegalArgumentException;
	
	/**
	 * Method to get the last x baskets
	 * @param username
	 * @param start
	 * @param end
	 * @return List with the last baskets
	 * @throws IllegalArgumentException
	 */
	public List<Basket> getLastBaskets(String username, int start, int end) throws IllegalArgumentException;
	
	/**
	 * Method to get all baskets of the actual month
	 * @param username
	 * @return list with all baskets of the actual Month
	 * @throws IllegalArgumentException
	 */
	public List<Basket> getBasketsOfActualMonth(String username) throws IllegalArgumentException;
	
	
	/**
	 * Method to create a category
	 * @param user
	 * @param name
	 * @param notice
	 * @param income
	 * @param colour
	 * @return new category
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public Category createCategory(User user, String name, String notice, boolean income, String colour) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update a category
	 * @param category
	 * @return updated category
	 * @throws IllegalArgumentException
	 */
	public Category updateCategory(Category category) throws IllegalArgumentException;
	
	/**
	 * Method to find a category with the id
	 * @param categoryId
	 * @return category Object
	 * @throws IllegalArgumentException
	 */
	public Category findCategoryById(int categoryId) throws IllegalArgumentException;
	
	/**
	 * Method to deletes a category
	 * @param categoryId
	 * @throws IllegalArgumentException
	 */
	public void deleteCategory(int categoryId) throws IllegalArgumentException;
	
	
	/**
	 * Method to create an item
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param receiptDate
	 * @param basket
	 * @param category
	 * @return new item object
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public Item createItem(String name, double quantity, double price, String notice, Timestamp receiptDate, Basket basket, Category category) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update an item
	 * @param item
	 * @return updated item
	 * @throws IllegalArgumentException
	 */
	public Item updateItem(Item item) throws IllegalArgumentException;
	
	/**
	 * Method to find an item with the id
	 * @param itemId
	 * @return item object
	 * @throws IllegalArgumentException
	 */
	public Item findItemById(int itemId) throws IllegalArgumentException;
	
	/**
	 * Method to deletes an item
	 * @param itemId
	 * @throws IllegalArgumentException
	 */
	public void deleteItem(int itemId) throws IllegalArgumentException;
	
	
	/**
	 * Method to create an income
	 * @param user
	 * @param name
	 * @param notice
	 * @param quantity
	 * @param amount
	 * @param receiptDate
	 * @param category
	 * @return new income
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public Income createIncome(User user, String name, String notice, double quantity, double amount, Date receiptDate, Category category) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update an income
	 * @param income
	 * @return updated income object
	 * @throws IllegalArgumentException
	 */
	public Income updateIncome(Income income) throws IllegalArgumentException;
	
	/**
	 * Method to find an income with the id
	 * @param incomeId
	 * @return income object
	 * @throws IllegalArgumentException
	 */
	public Income findIncomeById(int incomeId) throws IllegalArgumentException;
	
	/**
	 * Method to delete an income
	 * @param income
	 * @throws IllegalArgumentException
	 */
	public void deleteIncome(int income) throws IllegalArgumentException;
	
	/**
	 * Method to gets the last X incomes
	 * @param username
	 * @param start
	 * @param end
	 * @return List with the last incomes
	 * @throws IllegalArgumentException
	 */
	public List<Income> getLastIncome(String username, int start, int end) throws IllegalArgumentException;
	
	/**
	 * Method to gets the incomes of the actual 
	 * @param username
	 * @return list with all incomes of the actual month
	 * @throws IllegalArgumentException
	 */
	public List<Income> getIncomeOfActualMonth(String username) throws IllegalArgumentException;

	
	/**
	 * Method to create a payment
	 * @param user
	 * @param name
	 * @param number
	 * @param bic
	 * @return new payment object
	 * @throws IllegalArgumentException
	 * @throws EntityExistsException
	 */
	public Payment createPayment(User user, String name, String number, String bic) throws EntityExistsException, IllegalArgumentException;
	
	/**
	 * Method to update a payment
	 * @param payment
	 * @return updated payment object
	 * @throws IllegalArgumentException
	 */
	public Payment updatePayment(Payment payment) throws IllegalArgumentException;
	
	/**
	 * Method to get a payment with id 
	 * @param paymentId
	 * @return payment object
	 * @throws IllegalArgumentException
	 */
	public Payment findPaymentById(int paymentId) throws IllegalArgumentException;
	
	/**
	 * Method to delete a payment
	 * @param paymentId
	 * @throws IllegalArgumentException
	 */
	public void deletePayment(int paymentId) throws IllegalArgumentException;

	
}
