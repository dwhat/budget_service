package de.budget.dao;

//Lokales Interface das die Operationen erleichtert 
// wie zb findUser // createSession etc

import javax.ejb.Local;


import de.budget.entities.Basket;
import de.budget.entities.BudgetSession;
import de.budget.entities.Category;
import de.budget.entities.Payment;
import de.budget.entities.User;
import de.budget.entities.Vendor;
import de.budget.entities.Item;


/**
 * Interface for DAO
 * @author Marco
 * @author Moritz
 * 
 */
@Local
public interface BudgetOnlineDAOLocal {
	
	public User findUserByName(String userName);
	
	public int createSession(User userObject);
	
	public BudgetSession findSessionById(int sessionId);

	public void closeSession(int sessionId);
	
	public Vendor findVendorById(int vendorId);
	
	public Basket findBasketById(int basketId);
	
	public Category findCategoryById(int categoryId);
	
	public Item findItemById(int itemId);
	
	public Payment findPaymentById(int paymentId);
	
	public User createUser(String username, String password, String email);
	
	public Category createCategory(User user);
	
	public Vendor createVendor(User user);
	
	public Basket createBasket(User user, Payment payment, Vendor vendor);
	
	public Payment createPayment(User user);
	
	public Item createItem(Basket basket, Category category);

}
