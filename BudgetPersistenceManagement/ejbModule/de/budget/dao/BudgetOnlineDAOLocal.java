package de.budget.dao;

//Lokales Interface das die Operationen erleichtert 
// wie zb findUser // createSession etc

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Local;











import de.budget.entities.Basket;
import de.budget.entities.BudgetSession;
import de.budget.entities.Category;
import de.budget.entities.Income;
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
	public User updateUser(User user) throws IllegalArgumentException;
	public User findUserByName(String userName) throws IllegalArgumentException;
	public void deleteUser(String username) throws IllegalArgumentException;
	
	public int createSession(User userObject);	
	public BudgetSession findSessionById(int sessionId) throws IllegalArgumentException;
	public void closeSession(int sessionId) throws IllegalArgumentException;
	
	public Vendor createVendor(User user, String name, String logo);	
	public Vendor updateVendor(Vendor vendor) throws IllegalArgumentException;
	public Vendor findVendorById(int vendorId) throws IllegalArgumentException;
	public void deleteVendor(int vendorId) throws IllegalArgumentException;
	
	public Basket createBasket(User user, String notice, double amount, Timestamp purchaseDate,Payment payment, Vendor vendor, List<Item> items);
	public Basket updateBasket(Basket basket) throws IllegalArgumentException;
	public Basket findBasketById(int basketId) throws IllegalArgumentException;
	public void deleteBasket(int basketId) throws IllegalArgumentException;
	public List<Basket> getLastBaskets(String username, int numberOfLastBaskets);
	public List<Basket> getBasketsOfActualMonth(String username);
	
	public Category createCategory(User user, String name, String notice, boolean income);
	public Category updateCategory(Category category) throws IllegalArgumentException;
	public Category findCategoryById(int categoryId) throws IllegalArgumentException;
	public void deleteCategory(int categoryId) throws IllegalArgumentException;
	
	public Item createItem(String name, double quantity, double price, String notice, int period, Timestamp launchDate, Timestamp finishDate, Basket basket, Category category);
	public Item updateItem(Item item) throws IllegalArgumentException;
	public Item findItemById(int itemId) throws IllegalArgumentException;
	public void deleteItem(int itemId) throws IllegalArgumentException;
	
	public Income createIncome(User user, String name, String notice, double quantity, double amount, int period, Timestamp launchDate, Timestamp finishDate, Category category);
	public Income updateIncome(Income income) throws IllegalArgumentException;
	public Income findIncomeById(int incomeId) throws IllegalArgumentException;
	public void deleteIncome(int income) throws IllegalArgumentException;

	public Payment createPayment(User user, String name, String number, String bic);
	public Payment updatePayment(Payment payment) throws IllegalArgumentException;
	public Payment findPaymentById(int paymentId) throws IllegalArgumentException;
	public void deletePayment(int paymentId) throws IllegalArgumentException;

	
}
