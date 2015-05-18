package de.budget.dao;




//EJB Imports
import javax.ejb.Stateless;

//Peristence Imports
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.sun.mail.imap.protocol.Item;

//Interface Import
import de.budget.dao.BudgetOnlineDAOLocal;

import de.budget.entities.Basket;
import de.budget.entities.Category;
import de.budget.entities.Payment;
//Entities-Import 
import de.budget.entities.User;
import de.budget.entities.BudgetSession;
import de.budget.entities.Vendor;

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

}
