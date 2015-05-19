package de.budget.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.budget.entities.User;

/**
 * dataBuilder to write testdata in db
 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
 */
@Startup
@Singleton
public class DataBuilder {
	
	@PersistenceContext
	EntityManager em;
	
	private static final Logger logger = Logger.getLogger(DataBuilder.class);
	
	@Resource
	private String username1, password1, username2, password2, email1, email2;

	/**
	 * Method to write Test Data in Database
	 */
	@PostConstruct
	private void init() {
		logger.info("Databuilder init");
		User user1 = em.find(User.class, username1);
		if (user1 == null) {
			user1 = new User(username1, password1, email1);
			em.persist(user1);
			logger.info("User1 angelegt");
		}
		else
		{
			logger.info("User1 existiert schon");
		}

		User user2 = em.find(User.class, username2);
		if (user2 == null) {
			user2 = new User(username2, password2, email2);
			em.persist(user2);		
			logger.info("User2 angelegt");
		}
		else
		{
			logger.info("User2 existiert schon");
		}
	}
	
}