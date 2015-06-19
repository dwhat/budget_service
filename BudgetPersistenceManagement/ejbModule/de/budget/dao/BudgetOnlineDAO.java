package de.budget.dao;




//EJB Imports
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
//Peristence Imports
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.jboss.logging.Logger;















//Interface Import
import de.budget.dao.BudgetOnlineDAOLocal;
import de.budget.entities.Basket;
import de.budget.entities.Category;
import de.budget.entities.Income;
import de.budget.entities.Payment;
//Entities-Import 
import de.budget.entities.User;
import de.budget.entities.BudgetSession;
import de.budget.entities.Vendor;
import de.budget.entities.Item;

/**********************************************/

/**
 * 
 * AusImplementierung des DAOLocal Interfaces für den Datenzugriff über JPA
 * 
 * @author Marco
 * @author Moritz
 *
 */
@Stateless
public class BudgetOnlineDAO implements BudgetOnlineDAOLocal {
	
	private static final Logger logger = Logger.getLogger(BudgetOnlineDAO.class);
	
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Finde User by Name 
	 * <p> Author: Moritz </p>
	 */
	@Override
	public User findUserByName(String userName)  throws IllegalArgumentException{
		return em.find(User.class, userName);
	}

	/**
	 * New SessionInstanz anschließende Rückgabe der ID
	 * <p> Author: Moritz </p>
	 */
	@Override
	public int createSession(User userObject) throws EntityExistsException, IllegalArgumentException{
		BudgetSession session = new BudgetSession(userObject);
		em.persist(session);
		return session.getId();
	}

	
	/**
	 * Schließen bzw löschen einer Session, 
	 * <p> Author: Moritz </p>
	 */
	@Override
	public void closeSession(int sessionId) throws IllegalArgumentException {
			BudgetSession session = em.find(BudgetSession.class, sessionId);
			em.remove(session);
	}
	
	/**
	 * Alle Sessions die älter als 2 Tage und noch existent werden gelöscht
	 * 
	 * <p> Author: Moritz </p> 
	 * 
	 */
	@Override
	public List<BudgetSession> getOldSessions(Timestamp dayBefore) throws IllegalArgumentException {
		Query q = em.createNamedQuery("findOldSessions", BudgetSession.class);
		q.setParameter("date", dayBefore);
		@SuppressWarnings("unchecked")
		ArrayList<BudgetSession> list = (ArrayList<BudgetSession>) q.getResultList();
		return list;
	}

	/**
	 * Finde Session by ID und gebe SessionObjekt zurück
	 * <p> Author: Moritz </p>
	 */
	@Override
	public BudgetSession findSessionById(int sessionId)  throws IllegalArgumentException{
		return em.find(BudgetSession.class, sessionId);
	}

	/**
	 * Finde Vendor mit id
	 * <p> Author: Marco </p>
	 */
	@Override
	public Vendor findVendorById(int vendorId)  throws IllegalArgumentException{
		return em.find(Vendor.class, vendorId);
	}

	/**
	 * Method to get a basket with id 
	 * <p> Author: Marco </p>
	 */
	@Override
	public Basket findBasketById(int basketId)  throws IllegalArgumentException{
		return em.find(Basket.class,  basketId);
	}

	
	/**
	 * Method to find all basket of the actual month
	 * <p> Author: Marco </p>
	 */
	@Override
	public List<Basket> getBasketsOfActualMonth(String username){
		Timestamp date = new Timestamp(System.currentTimeMillis());
		Timestamp firstOfMonth = new Timestamp(date.getYear(), date.getMonth(), 1, 0, 0, 0, 0); //erster des Monats
		Query q = em.createNamedQuery("findBasketsOfMonth", Basket.class);
		q.setParameter("username", username);
		q.setParameter("date", firstOfMonth);
		@SuppressWarnings("unchecked")
		ArrayList<Basket> list = (ArrayList<Basket>) q.getResultList();
		return list;
	}
	
	/**
	 * Methode zum nachladen von Incomes. Gibt incomes von Posistion start bis end an
	 * <p> Author: Marco </p>
	 */
	@Override
	public List<Basket> getLastBaskets(String username, int start, int end) {
		Query q = em.createNamedQuery("findLastBaskets", Basket.class);
		q.setParameter("username", username);
		q.setFirstResult(start);
		q.setMaxResults(end);
		@SuppressWarnings("unchecked")
		ArrayList<Basket> list = (ArrayList<Basket>) q.getResultList();
		return list;
	}
		
	/**
	 * Method to find a category with id
	 * <p> Author: Marco </p>
	 */
	@Override
	public Category findCategoryById(int categoryId)  throws IllegalArgumentException{
		return em.find(Category.class, categoryId);
	}

	/**
	 * Method to find an item with id
	 * <p> Author: Marco </p>
	 */
	@Override
	public Item findItemById(int itemId)  throws IllegalArgumentException{
		return em.find(Item.class, itemId);
	}
	
	/**
	 * Method to find an income with id
	 * <p> Author: Marco </p>
	 */
	@Override
	public Income findIncomeById(int incomeId)  throws IllegalArgumentException{
		return em.find(Income.class, incomeId);
	}

	/**
	 * Method to find a payment with id 
	 * <p> Author: Marco </p>
	 */
	@Override
	public Payment findPaymentById(int paymentId)  throws IllegalArgumentException{
		return em.find(Payment.class, paymentId);
	}
	/**
	 * Method to create a user
	 * <p> Author: Marco </p>
	 */
	@Override
	public User createUser(String username, String password, String email) throws EntityExistsException, IllegalArgumentException{
		if(findUserByName(username) == null) {
			User user = new User(username, password, email);
			em.persist(user);
			return user;
		}
		else {
			return null;
		}
	}

	/**
	 * Method to create a new category
	 * <p> Author: Marco </p>
	 */
	@Override
	public Category createCategory(User user, String name, String notice, boolean income, String colour) throws EntityExistsException, IllegalArgumentException{
		Category category = new Category(user, income, name, notice, colour);
		if (category != null) {
			em.persist(category);
		}
		return category;
	}
	

	/**
	 * Method to create a new vendor
	 * <p> Author: Moritz </p>
	 * <p> Author: Marco </p>
	 */
	@Override
	public Vendor createVendor(User user, String name, String logo, String street, String city, int PLZ, int houseNumber) throws EntityExistsException, IllegalArgumentException{
		Vendor vendor = new Vendor(user, name, logo, street, city, PLZ, houseNumber);
		if(vendor != null) {
			em.persist(vendor);;
		}
		return vendor;
	}
	
	/**
	 * Method to create a new payment
	 * <p> Author: Marco </p>
	 */
	@Override
	public Payment createPayment(User user, String name, String number, String bic) throws EntityExistsException, IllegalArgumentException{
		Payment payment = new Payment(user, name, number, bic);
		if (payment != null) {
			em.persist(payment);
		}
		return payment;
	}
	
	/**
	 * Method to create a new basket
	 * <p> Author: Marco </p>
	 */
	@Override
	public Basket createBasket(User user, String name, String notice, double amount, Timestamp purchaseDate,Payment payment, Vendor vendor, List<Item> items) throws EntityExistsException, IllegalArgumentException{
		if(user != null && payment != null && vendor != null) {
			Basket basket = new Basket(user, name, notice, amount, purchaseDate, payment, vendor, items);
			if (basket != null){
				em.persist(basket);
				return basket;
			}
		}
		return null;
	}
	
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
	@Override
	public Basket createBasket(User user, String name, String notice, double amount, Timestamp purchaseDate,Payment payment, Vendor vendor) throws EntityExistsException, IllegalArgumentException {
		if(user != null && payment != null && vendor != null) {
			Basket basket = new Basket(user, name, notice, amount, purchaseDate, payment, vendor);
			if (basket != null){
				em.persist(basket);
				return basket;
			}
		}
		return null;
	}
	
	/**
	 * Method to create a new Item
	 * <p> Author: Marco </p>
	 */
	@Override
	public Item createItem(String name, double quantity, double price, String notice, Timestamp receiptDate, Basket basket, Category category) throws EntityExistsException, IllegalArgumentException {
		if(basket != null && category != null) {
			Item item = new Item(name, quantity, price, notice, receiptDate, basket, category);
			if (item != null) {
				em.persist(item);
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Method to create a new income
	 * <p> Author: Marco </p> 
	 */
	@Override
	public Income createIncome(User user, String name, String notice, double quantity, double amount, Date receiptDate, Category category) throws EntityExistsException , IllegalArgumentException, EJBTransactionRolledbackException, TransactionRequiredException {
		if(user != null && category != null) {
			logger.info("xyz-createIncomeBeginnDAO");
			Income income = new Income(name, notice, quantity, amount, receiptDate, category, user);
			logger.info("xyz-");
			if (income != null) {
				em.persist(income);
				logger.info("xyz-nach Persist");
				return income;
			}
		}
		return null;
	}
	
	/**
	 * Methode zum nachladen von Incomes. Gibt incomes von Posistion start bis end an
	 * <p> Author: Marco </p>
	 */
	@Override
	public List<Income> getLastIncome(String username, int start, int end) {
		Query q = em.createNamedQuery("findLastIncomes", Income.class);
		q.setParameter("username", username);
		q.setFirstResult(start);
		q.setMaxResults(end);
		@SuppressWarnings("unchecked")
		ArrayList<Income> list = (ArrayList<Income>) q.getResultList();
		return list;
	}
	
	/**
	 * Method to find all income of the actual month
	 * <p> Author: Marco </p>
	 */
	public List<Income> getIncomeOfActualMonth(String username){
		Timestamp date = new Timestamp(System.currentTimeMillis());
		Timestamp firstOfMonth = new Timestamp(date.getYear(), date.getMonth(), 1, 0, 0, 0, 0); //erster des Monats
		Query q = em.createNamedQuery("findIncomeOfMonth", Income.class);
		q.setParameter("username", username);
		q.setParameter("date", firstOfMonth);
		@SuppressWarnings("unchecked")
		ArrayList<Income> list = (ArrayList<Income>) q.getResultList();
		return list;
	}
	
	/**
	 * Method to update an income object
	 * <p> Author: Marco </p>
	 */
	@Override
	public Income updateIncome(Income income) throws IllegalArgumentException{
		em.merge(income);
		return income;
		
	}
	
	/**
	 * Method to remove an income object
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deleteIncome(int incomeId) throws IllegalArgumentException{
		Income income = em.find(Income.class, incomeId);
		em.remove(income);
	}

	/**
	 * Method to delete an user
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deleteUser(String username) throws IllegalArgumentException{
		User user = em.find(User.class, username);
		em.remove(user);
	}

	/**
	 * Method to delete a vendor
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deleteVendor(int vendorId) throws IllegalArgumentException{
		Vendor vendor = em.find(Vendor.class, vendorId);
		em.remove(vendor);	
	}

	/**
	 * Method to delete an basket
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deleteBasket(int basketId) throws IllegalArgumentException{
		Basket basket = em.find(Basket.class,  basketId);
		em.remove(basket);
	}

	/**
	 * Method to delete a category
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deleteCategory(int categoryId) throws IllegalArgumentException {
		Category category = em.find(Category.class,  categoryId);
		em.remove(category);
	}

	/**
	 * Method to delete an item
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deleteItem(int itemId) throws IllegalArgumentException {
		Item item = em.find(Item.class, itemId);
		em.remove(item);
		
	}

	/**
	 * Method to delete a payment
	 * <p> Author: Marco </p>
	 */
	@Override
	public void deletePayment(int paymentId) throws IllegalArgumentException {
		Payment payment = em.find(Payment.class, paymentId);
		em.remove(payment);
	}

	/**
	 * Method to update an user
	 * <p> Author: Moritz </p>
	 */
	@Override
	public User updateUser(User user) throws IllegalArgumentException {
		return em.merge(user);
	}

	/**
	 * Method to update a vendor
	 * <p> Author: Moritz </p>
	 */
	@Override
	public Vendor updateVendor(Vendor vendor) throws IllegalArgumentException {
		return em.merge(vendor);
	}

	/**
	 * Method to update a basket
	 * <p> Author: Moritz </p>
	 */
	@Override
	public Basket updateBasket(Basket basket) throws IllegalArgumentException {
		return em.merge(basket);
	}

	/**
	 * Method to update a category
	 * <p> Author: Moritz </p>e
	 */
	@Override
	public Category updateCategory(Category category) throws IllegalArgumentException {
		return em.merge(category);
	}

	/**
	 * Method to update an item
	 * <p> Author: Moritz </p>
	 */
	@Override
	public Item updateItem(Item item) throws IllegalArgumentException {
		return em.merge(item);
	}

	/**
	 * Method to update a payment
	 * <p> Author: Moritz </p>
	 */
	@Override
	public Payment updatePayment(Payment payment) throws IllegalArgumentException {
		return em.merge(payment);
	}


}
