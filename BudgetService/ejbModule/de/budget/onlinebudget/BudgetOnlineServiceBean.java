package de.budget.onlinebudget;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

//Interface-Import
import de.budget.common.BudgetOnlineService;

//DAO-Import
import de.budget.dao.BudgetOnlineDAOLocal;

//Response-Import @author Moritz
import de.budget.dto.UserLoginResponse;
import de.budget.dto.ReturnCodeResponse;

//Exception-Import
import de.budget.Exception.BudgetOnlineException;
import de.budget.Exception.InvalidLoginException;


//Entities-Import 
import de.budget.entities.User;
/**************************************************/


/**
 * Stateless-Beanimplementierung von BudgetOnlineService 
 * @author Moritz
 *
 */
@Stateless
@Remote(BudgetOnlineService.class)
public class BudgetOnlineServiceBean implements BudgetOnlineService {

	//private static final Logger logger = Logger.getLogger(BudgetOnlineServiceBean.class);
	
	/**
	 * EJB zur Abfrage von Datensätzen
	 * Referenz auf die EJB wird per Dependency Injection gefüllt. 
	 */
	@EJB(beanName = "BudgetOnlineDAO", beanInterface = de.budget.dao.BudgetOnlineDAOLocal.class)
	private BudgetOnlineDAOLocal dao;
	
	
	@Override
	public UserLoginResponse login(String username, String password) {
		UserLoginResponse response = new UserLoginResponse();
		try 
		{
			User user = this.dao.findUserByName(username);		
			if (user != null && user.getPassword().equals(password)) 
			{
				int sessionId = dao.createSession(user);
				//logger.info("Login erfolgreich. Session=" + sessionId);
				response.setSessionId(sessionId);
			}
			else 
			{
				//logger.info("Login fehlgeschlagen, da Kunde unbekannt oder Passwort falsch. username=" + username);
				throw new InvalidLoginException("Login fehlgeschlagen, da Kunde unbekannt oder Passwort falsch. username="+user.getUserName());
			}
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public ReturnCodeResponse logout(int sessionId) {
		dao.closeSession(sessionId);
		ReturnCodeResponse response = new ReturnCodeResponse();
		//logger.info("Logout erfolgreich. Session=" + sessionId);
		return response;
		
	}

}
