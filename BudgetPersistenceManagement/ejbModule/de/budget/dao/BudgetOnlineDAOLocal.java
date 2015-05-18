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
	
	public User createUser(String username, String password, String email);	
	public User findUserByName(String userName);
	public void deleteUser(String username);
	
	public int createSession(User userObject);	
	public BudgetSession findSessionById(int sessionId);
	public void closeSession(int sessionId);
	
	public Vendor createVendor(User user);	
	public Vendor findVendorById(int vendorId);
	public void deleteVendor(int vendorId);
	
	public Basket createBasket(User user, Payment payment, Vendor vendor);
	public Basket findBasketById(int basketId);
	public void deleteBasket(int basketId);
	
	public Category createCategory(User user);
	public Category findCategoryById(int categoryId);
	public void deleteCategory(int categoryId);
	
	public Item createItem(Basket basket, Category category);
	public Item findItemById(int itemId);
	public void deleteItem(int itemId);

	public Payment createPayment(User user);
	public Payment findPaymentById(int paymentId);
	public void deletePayment(int paymentId);
	
}
