package de.budget.dao;




//EJB Imports
import javax.ejb.Stateless;

//Peristence Imports
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


//Interface Import
import de.budget.dao.BudgetOnlineDAOLocal;

import de.budget.entities.Basket;
import de.budget.entities.Category;
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
 */
@Stateless
public class BudgetOnlineDAO implements BudgetOnlineDAOLocal {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Finde User by Name 
	 * @author Moritz
	 * @param UserName(String)
	 * @return UserObjekt
	 */
	@Override
	public User findUserByName(String userName) {
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
	public void closeSession(int sessionId) {
		BudgetSession session = em.find(BudgetSession.class,sessionId);
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
	public BudgetSession findSessionById(int sessionId) {
		return em.find(BudgetSession.class, sessionId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param vendorID
	 * @return Vendor Object
	 */
	@Override
	public Vendor findVendorById(int vendorId) {
		return em.find(Vendor.class, vendorId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param basketID
	 * @return Basket Object
	 */
	@Override
	public Basket findBasketById(int basketId) {
		return em.find(Basket.class,  basketId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param categoryID
	 * @return Category Object
	 */
	@Override
	public Category findCategoryById(int categoryId) {
		return em.find(Category.class, categoryId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param itemID
	 * @return Item Object
	 */
	@Override
	public Item findItemById(int itemId) {
		return em.find(Item.class, itemId);
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param paymentID
	 * @return Payment Object
	 */
	@Override
	public Payment findPaymentById(int paymentId) {
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
	public Category createCategory(User user) {
		Category category = new Category(user);
		if (category != null) {
			em.persist(category);
		}
		return category;
	}
	
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param User Object
	 * @return Vendor Object
	 */
	@Override
	public Vendor createVendor(User user) {
		Vendor vendor = new Vendor(user);
		if (vendor != null) {
			em.persist(vendor);
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
	public Payment createPayment(User user) {
		Payment payment = new Payment(user);
		if (payment != null) {
			em.persist(payment);
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
	public Basket createBasket(User user, Payment payment, Vendor vendor) {
		if(user != null && payment != null && vendor != null) {
			Basket basket = new Basket(user, payment, vendor);
			if (basket != null){
				em.persist(basket);
				return basket;
			}
		}
		return null;
	}
	
	@Override
	public Item createItem(Basket basket, Category category) {
		if(basket != null && category != null) {
			Item item = new Item(basket, category);
			if (item != null) {
				em.persist(item);
				return item;
			}
		}
		return null;
	}
}
