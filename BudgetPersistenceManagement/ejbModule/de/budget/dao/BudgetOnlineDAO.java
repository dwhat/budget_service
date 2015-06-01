package de.budget.dao;




//EJB Imports
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;

//Peristence Imports
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



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
 * AusImplementierung des DAOLocal Interfaces
 * 
 * @author Marco
 * @author Moritz
 * 
 * Querys:
 * ebenfalls denkbar wäre 
 * NativeQuery
 * em.executeQuery("Select c FROM Customer c where c.name Like :name", Customer.class);
 * query.setParemter("name", "%meier%";
 * 
 * List<customer> result = query.getResultList();
 * 
 * nicht mit SQL zu verwechseln !!
 * 
 * NamedQuery:
 * 
 * 
 * Update:
 * em.merge(customer); gebe Instanz in DB rein und update somit
 * em.refresh(customer); DB überschreibt meine Instanz 
 * 
 * Remove:
 *
 * em.remove
 * 
 * 
 * TODO
 * Überlegen ob TryCatch Blöcke für einzelnen EntityManagerAufrufe notwendig ?!
 *
 */
@Stateless
public class BudgetOnlineDAO implements BudgetOnlineDAOLocal {
	
	//TODO ExceptionHandling
	private static final Logger logger = Logger.getLogger(BudgetOnlineDAO.class);
	
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Finde User by Name 
	 * @author Moritz
	 * @param UserName(String)
	 * @return UserObjekt
	 */
	@Override
	public User findUserByName(String userName)  throws IllegalArgumentException{
		return em.find(User.class, userName);
	}

	/**
	 * New SessionInstanz anschließende Rückgabe der ID
	 * @author Moritz
	 * @param UserObjekt
	 * @return SessionID
	 * 
	 */
	@Override
	public int createSession(User userObject) {
		BudgetSession session = new BudgetSession(userObject);
		em.persist(session);
		return session.getId();
	}

	
	/**
	 * Schließen bzw löschen einer Session, 
	 * @author Moritz
	 * @param sessionID
	 */
	@Override
	public void closeSession(int sessionId) throws IllegalArgumentException {

			BudgetSession session = em.find(BudgetSession.class, sessionId);
			em.remove(session);
		
	}

	/**
	 * Finde Session by ID und gebe SessionObjekt zurück
	 * 
	 * @author Moritz
	 * @param sessionID
	 * @return SessionObjekt
	 */
	@Override
	public BudgetSession findSessionById(int sessionId)  throws IllegalArgumentException{
		return em.find(BudgetSession.class, sessionId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param vendorID
	 * @return Vendor Object
	 */
	@Override
	public Vendor findVendorById(int vendorId)  throws IllegalArgumentException{
		return em.find(Vendor.class, vendorId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param basketID
	 * @return Basket Object
	 */
	@Override
	public Basket findBasketById(int basketId)  throws IllegalArgumentException{
		return em.find(Basket.class,  basketId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param categoryID
	 * @return Category Object
	 */
	@Override
	public Category findCategoryById(int categoryId)  throws IllegalArgumentException{
		return em.find(Category.class, categoryId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param itemID
	 * @return Item Object
	 */
	@Override
	public Item findItemById(int itemId)  throws IllegalArgumentException{
		return em.find(Item.class, itemId);
	}
	
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param incomeId
	 * @return Income Object
	 */
	@Override
	public Income findIncomeById(int incomeId)  throws IllegalArgumentException{
		return em.find(Income.class, incomeId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param paymentID
	 * @return Payment Object
	 */
	@Override
	public Payment findPaymentById(int paymentId)  throws IllegalArgumentException{
		return em.find(Payment.class, paymentId);
	}
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param username
	 * @param password
	 * @param email
	 * @return created userobject or null if there was a failure
	 */
	@Override
	public User createUser(String username, String password, String email) {
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
	 * @author Marco
	 * @date 18.05.2015
	 * @param User Object
	 * @return Category Object
	 */
	@Override
	public Category createCategory(User user, String name, String notice, boolean income) {
		Category category = new Category(user, income, name, notice);
		if (category != null) {
			em.persist(category);
		}
		return category;
	}
	


	/**
	 * @author Moritz
	 * @author Marco
	 * @date  19.05.2015
	 * @param VendorObjekt
	 * @return VendorKopieObjekt
	 */
	@Override
	public Vendor createVendor(User user, String name, String logo) {
		Vendor vendor = new Vendor(user, name, logo);
		if(vendor != null) {
			em.persist(vendor);;
		}
		return vendor;
	}
	
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param User Object
	 * @return Payment Object
	 */
	@Override
	public Payment createPayment(User user, String name, String number, String bic) {
		logger.info("createPayment Methode aufgerufen");
		Payment payment = new Payment(user, name, number, bic);
		logger.info("PaymentObject angelegt");
		if (payment != null) {
			logger.info("Payment Object ungleich null");
			em.persist(payment);
			logger.info("PaymentObject persistiert");
		}
		return payment;
	}
	
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param User Object
	 * @param Payment Object
	 * @param Vendor Object
	 * @return Basket Object
	 */
	@Override
	public Basket createBasket(User user, String notice, double amount, Timestamp purchaseDate,Payment payment, Vendor vendor, List<Item> items) {
		if(user != null && payment != null && vendor != null) {
			Basket basket = new Basket(user, notice, amount, purchaseDate, payment, vendor, items);
			if (basket != null){
				em.persist(basket);
				return basket;
			}
		}
		return null;
	}
	
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param Basket Object
	 * @param Category Object
	 * @return Item Object
	 */
	@Override
	public Item createItem(String name, double quantity, double price, String notice, int period, Timestamp launchDate, Timestamp finishDate, Basket basket, Category category) {
		if(basket != null && category != null) {
			Item item = new Item(name, quantity, price, notice, period, launchDate, finishDate, basket, category);
			if (item != null) {
				em.persist(item);
				return item;
			}
		}
		return null;
	}
	
	/**
	 * create an income
	 * @author Marco 
	 * @date 29.05.2015
	 */
	@Override
	public Income createIncome(User user, String name, String notice, double quantity, double amount, int period, Timestamp launchDate, Timestamp finishDate, Category category) {
		if(user != null && category != null) {
			Income income = new Income(name, notice, quantity, amount, period, launchDate, finishDate, category);
			if (income != null) {
				em.persist(income);
				return income;
			}
		}
		return null;
	}
	
	/**
	 * update an income Object
	 * @author Marco
	 * @date 29.05.2015
	 */
	@Override
	public Income updateIncome(Income income) throws IllegalArgumentException{
		em.merge(income);
		return income;
		
	}
	
	/**
	 * remove an income Object
	 * @author Marco
	 * @date 29.05.2015
	 */
	@Override
	public void deleteIncome(int incomeId) throws IllegalArgumentException{
		Income income = em.find(Income.class, incomeId);
		em.remove(income);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param username
	 */
	@Override
	public void deleteUser(String username) throws IllegalArgumentException{
		User user = em.find(User.class, username);
		em.remove(user);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param vendorId
	 */
	@Override
	public void deleteVendor(int vendorId) throws IllegalArgumentException{
		Vendor vendor = em.find(Vendor.class, vendorId);
		em.remove(vendor);	
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param basketId
	 */
	@Override
	public void deleteBasket(int basketId) throws IllegalArgumentException{
		Basket basket = em.find(Basket.class,  basketId);
		//Hier prüfen ob dann auch autoatisch alle Items mitgelöscht werden. 
		em.remove(basket);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param categoryId
	 */
	@Override
	public void deleteCategory(int categoryId) throws IllegalArgumentException {
		Category category = em.find(Category.class,  categoryId);
		em.remove(category);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param itemId
	 */
	@Override
	public void deleteItem(int itemId) throws IllegalArgumentException {
		Item item = em.find(Item.class, itemId);
		em.remove(item);
		
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param paymentId
	 */
	@Override
	public void deletePayment(int paymentId) throws IllegalArgumentException {
		Payment payment = em.find(Payment.class, paymentId);
		em.remove(payment);
	}

	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * @param Userobjekt
	 * @return UserobjektKopie
	 */
	@Override
	public User updateUser(User user) throws IllegalArgumentException {
		return em.merge(user);
	}

	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * @param VendorObjekt
	 * @return VendorobjektKopie
	 */
	@Override
	public Vendor updateVendor(Vendor vendor) throws IllegalArgumentException {
		return em.merge(vendor);
	}

	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * @param BasketObjekt
	 * @return BasketObjektKopie
	 */
	@Override
	public Basket updateBasket(Basket basket) throws IllegalArgumentException {
		return em.merge(basket);
	}

	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * @param CategoryObjekt
	 * @return CategoryObjektKopie
	 */
	@Override
	public Category updateCategory(Category category) throws IllegalArgumentException {
		return em.merge(category);
	}

	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * @param ItemObjekt
	 * @return ItemObjektKopie
	 */
	@Override
	public Item updateItem(Item item) throws IllegalArgumentException {
		return em.merge(item);
	}

	/**
	 * @author Moritz
	 * @date 19.05.2015
	 * @param PaymentObjekt
	 * @return PaymentObjektKopie
	 */
	@Override
	public Payment updatePayment(Payment payment) throws IllegalArgumentException {
		return em.merge(payment);
	}
}
