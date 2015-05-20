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
	public User updateUser(User user);
	public User findUserByName(String userName);
	public void deleteUser(String username);
	
	public int createSession(User userObject);	
	public BudgetSession findSessionById(int sessionId);
	public void closeSession(int sessionId);
	
	public Vendor createVendor(Vendor vendor);	
	public Vendor updateVendor(Vendor vendor);
	public Vendor findVendorById(int vendorId);
	public void deleteVendor(int vendorId);
	
	public Basket createBasket(User user, Payment payment, Vendor vendor);
	public Basket updateBaseket(Basket basket);
	public Basket findBasketById(int basketId);
	public void deleteBasket(int basketId);
	
	public Category createCategory(User user);
	public Category updateCategory(Category category);
	public Category findCategoryById(int categoryId);
	public void deleteCategory(int categoryId);
	
	public Item createItem(Basket basket, Category category);
	public Item updateItem(Item item);
	public Item findItemById(int itemId);
	public void deleteItem(int itemId);

	public Payment createPayment(User user, String name, String number, String bic);
	public Payment updatePayment(Payment payment);
	public Payment findPaymentById(int paymentId);
	public void deletePayment(int paymentId);
	
}
