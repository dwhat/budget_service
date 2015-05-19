package de.budget.onlinebudget;

import java.util.List;

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
import de.budget.dto.VendorListResponse;
//Exception-Import
import de.budget.Exception.BudgetOnlineException;
import de.budget.Exception.InvalidLoginException;


import de.budget.Exception.NoSessionException;
import de.budget.Exception.UsernameAlreadyExistsException;
import de.budget.entities.BudgetSession;
//Entities-Import 
import de.budget.entities.User;
import de.budget.entities.Vendor;
/**************************************************/
import de.budget.util.DtoAssembler;


/**
 * Stateless-Beanimplementierung von BudgetOnlineService 
 * @author Moritz
 * @author Marco
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
	
	@EJB
	private DtoAssembler dtoAssembler;
	
	/**
	 * @author Marco
	 * @param sessionId
	 * @return BudgetSession Object
	 * @throws NoSessionException
	 */
	private BudgetSession getSession(int sessionId) throws NoSessionException {
		BudgetSession session = dao.findSessionById(sessionId);
		if (session==null) {
			throw new NoSessionException("Bitte zun�chst ein Login durchf�hren.");
		}
		else {
			return session;
		}
	}
	
	
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

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * Method to register a new user
	 */
	@Override
	public UserLoginResponse registerNewUser(String username, String password, String email) {
		UserLoginResponse response = new UserLoginResponse();
		try {
			User user = dao.createUser(username, password, email);
			if (user != null) {
				int sessionId = dao.createSession(user);
				response.setSessionId(sessionId);
			}
			else {
				throw new UsernameAlreadyExistsException("Username has already been taken. Please try again.");
			}
		}
		catch(BudgetOnlineException be) {
			response.setReturnCode(be.getErrorCode());
			response.setMessage(be.getMessage());
		}
		return response;
	}
	
	/**
	 * Gives a Response Object with all Vendors in a list
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return VendorListResponse Object
	 */
	@Override
	public VendorListResponse getMyVendors(int sessionId) {
		VendorListResponse response = new VendorListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			List<Vendor> vendorList = user.getVendors();
			response.setVendorList(dtoAssembler.makeDto(vendorList));	
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}

}
