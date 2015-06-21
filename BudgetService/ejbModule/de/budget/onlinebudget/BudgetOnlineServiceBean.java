package de.budget.onlinebudget;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;







//Logger-Import
import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Remote;
//import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;







//Interface-Import
import de.budget.common.BudgetOnlineService;

//DAO-Import
import de.budget.dao.BudgetOnlineDAOLocal;
//Response-Import @author Moritz

import de.budget.dto.AmountTO;
import de.budget.dto.ItemTO;
import de.budget.dto.Response.AmountResponse;
import de.budget.dto.Response.BasketListResponse;
import de.budget.dto.Response.BasketResponse;
import de.budget.dto.Response.CategoryListResponse;
import de.budget.dto.Response.CategoryResponse;
import de.budget.dto.Response.IncomeListResponse;
import de.budget.dto.Response.IncomeResponse;
import de.budget.dto.Response.ItemListResponse;
import de.budget.dto.Response.ItemResponse;
import de.budget.dto.Response.PaymentListResponse;
import de.budget.dto.Response.PaymentResponse;
import de.budget.dto.Response.ReturnCodeResponse;
import de.budget.dto.Response.UserLoginResponse;
import de.budget.dto.Response.UserResponse;
import de.budget.dto.Response.VendorListResponse;
import de.budget.dto.Response.VendorResponse;
import de.budget.dto.Response.AmountListResponse;

//Exception-Import
import de.budget.Exception.BudgetOnlineException;
import de.budget.Exception.CategoryNotFoundException;
import de.budget.Exception.IncomeNotFoundException;
import de.budget.Exception.InvalidPasswordException;
import de.budget.Exception.ItemNotFoundException;
import de.budget.Exception.NoSessionException;
import de.budget.Exception.NoTransactionException;
import de.budget.Exception.PaymentNotFoundException;
import de.budget.Exception.RollbackException;
import de.budget.Exception.UserNotFoundException;
import de.budget.Exception.UsernameAlreadyExistsException;
import de.budget.Exception.BasketNotFoundException;
import de.budget.Exception.VendorNotFoundException;
import de.budget.Exception.WrongPasswordException;
//Entities-Import
import de.budget.entities.Basket;
import de.budget.entities.BudgetSession;
import de.budget.entities.Category;
import de.budget.entities.Income;
import de.budget.entities.Item;
import de.budget.entities.Payment; 
import de.budget.entities.User;
import de.budget.entities.Vendor;
/**************************************************/
import de.budget.util.DtoAssembler;
import de.budget.onlinebudget.QueueMessageSender;


/**
 * Stateless-Beanimplementierung von BudgetOnlineService 
 * Beinhaltet alle Methoden zum erzeugen, finden, ändern und löschen von allen Entities, sowie weiter Funktionen zum
 * sortieren und gruppieren der daten für die spätere Darstellung in der App
 * @author Moritz
 * @author Marco
 */
@Stateless
@Remote(BudgetOnlineService.class)
@WebService
@WebContext(contextRoot = "/budget")
public class BudgetOnlineServiceBean implements BudgetOnlineService {

	private static final Logger logger = Logger.getLogger(BudgetOnlineServiceBean.class);
	
	/**
	 * EJB zur Abfrage von Datensätzen
	 * Referenz auf die EJB wird per Dependency Injection gefüllt. 
	 */
	@EJB(beanName = "BudgetOnlineDAO", beanInterface = de.budget.dao.BudgetOnlineDAOLocal.class)
	private BudgetOnlineDAOLocal dao;
	
	
	/**
	 * EJB zum Erstellen von Data-Transfer-Objects
	 * Referenz auf die EJB wird per Dependency Injection gefüllt.
	 */
	@EJB
	private DtoAssembler dtoAssembler;
	
	/**
	 * EJB zur Beauftragung des Nachrichtenversand
	 * Referenz auf die EJB wird per Dependency Injection gefüllt.
	 */
	@EJB
	private QueueMessageSender outputSender;
	
	@EJB
	private Payload payloader;
	
	
	/**
	 * private HilfsMethode zum Laden der Session aus der Datenbank
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return BudgetSessionObject with a sessionId and a return code
	 * @throws NoSessionException
	 */
	private BudgetSession getSession(int sessionId) throws NoSessionException {
		BudgetSession session = dao.findSessionById(sessionId);
		if (session==null) {
			throw new NoSessionException("ErrorSessionID:" + sessionId + "|");
		
		}
		else {
			return session;
		}
	}

	/*#################      USER - SECTION     ##############*/
	
	/**
	 * Session anhand username und password erstellen und in ResponseObject zurückliefern
	 * 
	 * <p> Author: Moritz </p>
	 * <p> Author: Marco </p>
	 * @param username
	 * @param password
	 * @return UserLoginResponse
	 */
	@Override
	public UserLoginResponse login(String username, String password) {
		UserLoginResponse response = new UserLoginResponse();
		try {
			User user = this.dao.findUserByName(username);		
			if (user != null) {
				if (user.getPassword().equals(password)){
					int sessionId = dao.createSession(user);
				
					logger.info("Login erfolgreich. Session=" + sessionId);
					response.setSessionId(sessionId);
					// Request OK zurückschicken
					response.setReturnCode(200);
					int payloadNumber = payloader.getPayload();
					int newPayload = payloadNumber + 1;
					payloader.setPayload(newPayload);
				}
				else {
					logger.info("Login fehlgeschlagen, da password falsch ");
					throw new WrongPasswordException();
				}
			}
			else 
			{
				logger.info("Login fehlgeschlagen, da User unbekannt username: " + username);
				throw new UserNotFoundException(username);
			}
		}
		catch (UserNotFoundException | WrongPasswordException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("login | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("login | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline |User| Exception-" + e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	/**
	 * Method to logout. Deletes a session
	 * 
	 * <p> Author: Moritz </p>
	 * @return ReturnCodeResponse
	 */
	@Override
	public ReturnCodeResponse logout(int sessionId)  {
		
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			dao.closeSession(sessionId);	
			logger.info("Logout erfolgreich. Session=" + sessionId);
			response.setReturnCode(200);
			int payloadNumber = payloader.getPayload();
			payloader.setPayload(payloadNumber-1);
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "logout | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("logout | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("logout | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline |User| Exception-" + e.getMessage());
			response.setReturnCode(800);
		}
		return response;	
	}

	
	/**
	 * Method to register a new user
	 * <p> Author: Marco </p>
	 * Method to register a new user
	 * @param username
	 * @param password
	 * @param email
	 * @return UserLoginResponse
	 */
	@Override
	public UserLoginResponse setUser(String username, String password, String email) {
		UserLoginResponse response = new UserLoginResponse();
		try {
			//prüfen ob Username noch verwendbar ist
			if(dao.findUserByName(username) == null) {
				logger.info("Versuche neuen User anzulegen. Name=" + username);
				if(password.toCharArray().length > 7){
					User user = dao.createUser(username, password, email);
					if (user != null) {
						int sessionId = dao.createSession(user);
						response.setSessionId(sessionId);
						logger.info("User angelegt. Session=" + sessionId);
						response.setReturnCode(200);
						//Bei Text leer und sujbect Erfolgreiche Registierung bauen wir im Mail Teil automatisch eine Willkommensnachricht 
						outputSender.sendMapMessage(email, "Erfolgreiche-Registrierung", "Das ist ein Testtext", username);
					}
				}
				else {
					throw new InvalidPasswordException(password);
				}
			}
			else {
				throw new UsernameAlreadyExistsException(username);
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "setUser | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "setUser | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(InvalidPasswordException | UsernameAlreadyExistsException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline |User| TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("setUser | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline |User| EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("setUser | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline |User| Exception-" + e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	

	/**
	 * Method to find an user by name
	 * <p> Author: Moritz </p>
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param userName
	 * @return UserResponse
	 */
	@Override
	public UserResponse getUserByName(int sessionId, String userName) {
		UserResponse userResp = new UserResponse();	
		try {
			BudgetSession session = getSession(sessionId);
			if(session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				if (user != null) {
					userResp.setUserTo(dtoAssembler.makeDto(user));	
					userResp.setReturnCode(200);
				}
				else {
					throw new UserNotFoundException(userName);
				}
			}
		}
		catch(NoSessionException | UserNotFoundException e) {
			userResp.setReturnCode(e.getErrorCode());
			userResp.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "setUser | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				userResp.setReturnCode(be.getErrorCode());
				userResp.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline |User| TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getUserByName | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				userResp.setReturnCode(be.getErrorCode());
				userResp.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline |User| EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getUserByName | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				userResp.setReturnCode(be.getErrorCode());
				userResp.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline |User| Exception-" + e.getMessage());
			userResp.setReturnCode(800);
		}
		return userResp;
	}


	/**
	 * Method to delete the own user
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param username
	 * @return ReturnCodeResponse
	 */
	@Override
	public ReturnCodeResponse deleteUser(int sessionId, String username) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				if(session.getUsername().equals(username)) { //prüft ob man den eigenen user Löscht
					dao.deleteUser(username);
					response.setReturnCode(200);
					logger.info("User erfolgreich gelöscht");
				}
				else {
					response.setMessage("Sie können keinen fremden User löschen");
				}
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteUser | ID:" + sessionId + " username:" + username + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deleteUser | ID:" + sessionId + " username:" + username + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deleteUser | ID:" + sessionId + " username:" + username + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	/*#################      BASKET - SECTION     ##############*/

	/**
	 * Method to get the last baskets of a user
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param startPosition  startPosition of the incomes sorted by date
	 * @param endPosition  endPosition of the incomes sorted by date
	 * @return BasketListResponse
	 */
	@Override
	public BasketListResponse getLastBaskets(int sessionId, int startPosition, int endPosition) {
		BasketListResponse response = new BasketListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				String username = session.getUsername();
				ArrayList<Basket> basketList = (ArrayList<Basket>) this.dao.getLastBaskets(username, startPosition, endPosition);
				if(basketList.size()==0){
					throw new BasketNotFoundException("no baskets found for this user.");
				}
				else {
					response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getLastBaskets | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getLastBaskets | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getLastBaskets | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;	
	}

	/**
	 * Helper Method to get all baskets of a specific vendor
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param vendorId
	 * @return a list with all baskets of a vendor
	 */
	public List<Basket> getBasketsByVendorHelper(int sessionId, int vendorId) throws BudgetOnlineException, Exception {
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> basketList = new ArrayList<>(); //List with all baskets of the vendor, wird spï¿½ter befï¿½llt
				List<Basket> basketsOfUser = user.getBaskets(); //List with all baskets of the user
				if(basketsOfUser.size() == 0){
					throw new BasketNotFoundException("Not Baskets were found for this user");
				}
				else {
					for(Basket b : basketsOfUser) {
						if(vendorId == b.getVendor().getId()){
							basketList.add(b);
						}
					}
				}
				if(basketList.size() > 0) {
					return basketList;
				}
				else {
					return null;
				}
			}
			else {
				throw new NoSessionException();
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategories | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			throw new NoTransactionException("getCategories | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getCategories  | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			throw e;
		}
	}
	

	
	/**
	 * Method to get all baskets of a specific vendor
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param vendorId
	 * @return a list with all baskets of a vendor
	 */
	@Override
	public BasketListResponse getBasketsByVendor(int sessionId, int vendorId){
		BasketListResponse response = new BasketListResponse();
		try {
			List<Basket> basketList = getBasketsByVendorHelper(sessionId, vendorId); //List with all baskets of the vedor
				
			if(basketList.size() > 0) {
				response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
				response.setReturnCode(200);
			}
			else {
				throw new BasketNotFoundException("Not baskets found for this vendor");
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBasketsByVendor | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getbasketsByVendor | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getBasketsByVendor | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;	
	}
	
	/**
	 * Helper Method to sum the amount of all baskets, which are assigned to a special vendor
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param vendorId
	 * @return double value with sum of all amounts
	 * @throws BudgetOnlineException
	 * @throws Exception
	 */
	private double getAmountByVendorHelper(int sessionId, int vendorId) throws BudgetOnlineException, Exception {
		double sum = 0.0;
		try {
			List<Basket> basketList = getBasketsByVendorHelper(sessionId, vendorId);
			if(basketList != null) {
				for(Basket b : basketList) {
					sum = sum + b.getAmount();
				}
				return sum;
			}
			else {
				return 0.0;
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			throw e;
		}
		catch(BudgetOnlineException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getAmountByVendor | ID:" + sessionId + "vendorId:" + vendorId + "|");
		}
		catch (TransactionRequiredException e) {
			throw new NoTransactionException("getgetAmountByVendor | ID:" + sessionId + "vendorId:" + vendorId + "|");
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getgetAmountByVendor  | ID:" + sessionId + "vendorId:" + vendorId + "|");
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Methode um die Beträge pro Vendor zurück zugeben
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return AmountListResponse
	 */
	@Override
	public AmountListResponse getAmountForVendors(int sessionId) {
		AmountListResponse response = new AmountListResponse();
		List<AmountTO> amountList= new ArrayList<>();
		try {
			List<Vendor> vendorList = getVendorsHelper(sessionId);
			for(Vendor v : vendorList) {
				double value = getAmountByVendorHelper(sessionId, v.getId());
				amountList.add(dtoAssembler.makeDto(v.getName(), value));
			}
			response.setReturnCode(200);
			response.setAmountList(amountList);
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getAmountForVendors| ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getAmountForVendors | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getAmountForVendors | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		
		return response;
	}
	
	/**
	 * Method to get all incomes of the actual month
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return List with all Baskets of the month
	 */
	private List<Basket> getBasketsOfActualMonthHelper(int sessionId) throws NoSessionException, IllegalArgumentException, Exception{
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> list = this.dao.getBasketsOfActualMonth(user.getUserName());
				return list;
			}
			else {
				return null;
			}
		}
		catch(NoSessionException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBasketsOfActualMonth | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getBasketsOfActualMonth | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getBasketsOfActualMonth | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gets all baskets of the actual month
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return AmountResponse
	 */
	@Override
	public AmountResponse getBasketsOfActualMonth(int sessionId) {
		AmountResponse response = new AmountResponse();
		try {
			List<Basket> basketList = getBasketsOfActualMonthHelper(sessionId);
			double amount = 0;
			for (Basket b : basketList) {
				amount = amount + b.getAmount();
			}
			response.setValue(amount);
			response.setReturnCode(200);
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBasketsOfActualMonth | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getBasketsOfActualMonth | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getBasketsOfActualMonth | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	/**
	 * Gets all baskets of a specific payment
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param paymentId
	 * @return BasketListResponse
	 */
	@Override
	public BasketListResponse getBasketsByPayment(int sessionId, int paymentId) {
		BasketListResponse response = new BasketListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> basketList = new ArrayList<>(); //List with all baskets of the payment, wird spï¿½ter befï¿½llt
				List<Basket> basketsOfUser = user.getBaskets(); //List with all baskets of the user
				if(basketsOfUser.size() == 0){
					for(Basket b : basketsOfUser) {
						if(paymentId == b.getPayment().getId()){
							basketList.add(b);
						}
					}
				}
				else {
					throw new BasketNotFoundException("No baskets found of this user");
				}
				if(basketList.size() > 0) {
					response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
					response.setReturnCode(200);
				}
				else {
					throw new BasketNotFoundException("No basket found for this payment.");
				}
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBasketsByPayment | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getBasketsByPayment | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getBasketsByPayment | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;		
	}
	
	/**
	 * Method to get a Basket with the SessionId and the basketId
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param basketId
	 * @return BasketResponse Object
	 */
	public BasketResponse getBasket(int sessionId, int basketId) {
		BasketResponse response = new BasketResponse();
		
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Basket basket = user.getBasket(basketId);
				if(basket != null) {
					response.setBasketTo(dtoAssembler.makeDto(basket));	
					response.setReturnCode(200);
				}
				else {
					throw new BasketNotFoundException("BasketID: " + basketId);
				}
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBasket | ID:" + sessionId + " baskedId:" + basketId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getBasket | ID:" + sessionId + " baskedId:" + basketId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getBasket | ID:" + sessionId + " baskedId:" + basketId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Gives a Response Object with all Baskets in a list
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return BasketListResponse Object
	 */
	@Override
	public BasketListResponse getBaskets(int sessionId) {
		BasketListResponse response = new BasketListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> basketList = user.getBaskets();
				if(basketList.size()==0){
					throw new BasketNotFoundException("No baskets found for this user");
				}
				else {
					response.setBasketList(dtoAssembler.makeBasketListDto(basketList));	
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBaskets | ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getBaskets | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getBaskets | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to delete a basket
	 * <p> Author: Marco </p>
	 */
	@Override
	public ReturnCodeResponse deleteBasket(int sessionId, int basketId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteBasket(basketId);
				response.setReturnCode(200);
				logger.info("Basket erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteBasket | ID:" + sessionId + " basketId:" + basketId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deleteBasket | ID:" + sessionId + " basketId:" + basketId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deleteBasket | ID:" + sessionId + " basketId:" + basketId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	

	/**
	 * Method to create or update a basket
	 * <p> Author: Marco </p>
	 */
	public BasketResponse createOrUpdateBasketList(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, List<ItemTO> items) {
		BasketResponse response = new BasketResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Basket basket = user.getBasket(basketId);
				Payment payment = user.getPayment(paymentId);
				Vendor vendor = user.getVendor(vendorId);
				ArrayList<Item> itemList = new ArrayList<>();

				if(basket == null) {
					basket = dao.createBasket(user, name, notice, amount, new Timestamp(purchaseDate), payment, vendor, itemList);
				}
				else {
					basket.setName(name);
					basket.setNotice(notice);
					basket.setAmount(amount);
					basket.setPurchaseDate(new Timestamp(purchaseDate));
					basket.setPayment(payment);
					basket.setVendor(vendor);
					basket.setItems(itemList);
				
					basket = dao.updateBasket(basket);
				}
				//wandle empfangene ItemTOs in ItemObjekte
				for(ItemTO iTo : items) {
					String itemName = iTo.getName();	
					double itemQuantity = iTo.getQuantity();	
					double itemPrice = iTo.getPrice();	
					String itemNotice = iTo.getNotice();		
					Timestamp itemReceiptDate = new Timestamp(iTo.getReceiptDate());	
					int itemCategoryId = iTo.getCategoryId();
					Category itemCategory = user.getCategory(itemCategoryId);
					Item item = new Item(itemName, itemQuantity, itemPrice, itemNotice, itemReceiptDate, basket, itemCategory);
					//itemList.add(item);
					//basket.addNewItem(item);
				}

				if (basket != null) {
					//basket.setItems(itemList);
					//basket = dao.updateBasket(basket);
					response.setBasketTo(dtoAssembler.makeDto(basket));
					response.setReturnCode(200);
				}
				else {
					throw new BasketNotFoundException("Basket to update not found");
				}
				
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to create or update a basket
	 * <p> Author: Marco </p>
	 */
	@Override
	public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId) {
		BasketResponse response = new BasketResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Basket basket = user.getBasket(basketId);
				Payment payment = user.getPayment(paymentId);
				Vendor vendor = user.getVendor(vendorId);

				if(basket == null) {
					basket = dao.createBasket(user, name, notice, amount, new Timestamp(purchaseDate), payment, vendor);
				}
				else {
					basket.setName(name);
					basket.setNotice(notice);
					basket.setAmount(amount);
					basket.setPurchaseDate(new Timestamp(purchaseDate));
					basket.setPayment(payment);
					basket.setVendor(vendor);
				
					basket = dao.updateBasket(basket);
				}
				//wandle empfangene ItemTOs in ItemObjekte
				/*
				for(ItemTO iTo : items) {
					String itemName = iTo.getName();	
					double itemQuantity = iTo.getQuantity();	
					double itemPrice = iTo.getPrice();	
					String itemNotice = iTo.getNotice();		
					Timestamp itemReceiptDate = new Timestamp(iTo.getReceiptDate());	
					int itemCategoryId = iTo.getCategoryId();
					Category itemCategory = user.getCategory(itemCategoryId);
					Item item = new Item(itemName, itemQuantity, itemPrice, itemNotice, itemReceiptDate, basket, itemCategory);
					//itemList.add(item);
					//basket.addNewItem(item);
				}
				*/
				if (basket != null) {
					//basket.setItems(itemList);
					//basket = dao.updateBasket(basket);
					response.setBasketTo(dtoAssembler.makeDto(basket));
					response.setReturnCode(200);
				}
				else {
					throw new BasketNotFoundException("Basket to update not found");
				}
				
			}
		}
		catch(NoSessionException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("createOrUpdateBasket | ID:" + sessionId  + " basketId:" + basketId + " paymentId:" +paymentId + " vendorId:" + vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}


	/*#################      VENDOR - SECTION     ##############*/
	
	
	/**
	 * Method to get a Vendor with the SessionId and the vendorId
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param vendorId
	 * @return VendorResponse Object
	 */
	public VendorResponse getVendor(int sessionId, int vendorId) {
		VendorResponse response = new VendorResponse();
		
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Vendor vendor = user.getVendor(vendorId);
				if(vendor != null) {
					response.setVendorTo(dtoAssembler.makeDto(vendor));	
					response.setReturnCode(200);
				}
				else {
					throw new VendorNotFoundException("Vendor not found for this id");
				}
			}
		}
		catch(NoSessionException | VendorNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getVendor | ID:" + sessionId + " vendorId:" + vendorId+ "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getVendor | ID:" + sessionId + " vendorId:" + vendorId+ "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getVendor | ID:" + sessionId + " vendorId:" + vendorId+ "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Helper Method, to Gives a Response Object with all Vendors in a list
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return VendorListResponse Object
	 */
	private List<Vendor> getVendorsHelper(int sessionId) throws BudgetOnlineException, Exception {
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Vendor> vendorList = user.getVendors();
				if(vendorList.size() == 0) {
					throw new VendorNotFoundException("Not vendors found of this user");
				}
				else {
					return vendorList;
				}
			}
			else {
				throw new NoSessionException();
			}
		}
		catch(NoSessionException | VendorNotFoundException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getVendors | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getVendors | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getVendors | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Gives a Response Object with all Vendors in a list
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return VendorListResponse Object
	 */
	@Override
	public VendorListResponse getVendors(int sessionId) {
		VendorListResponse response = new VendorListResponse();
		try {
			List<Vendor> vendorList = getVendorsHelper(sessionId);
			if(vendorList.size() == 0) {
				throw new VendorNotFoundException("Not vendors found of this user");
			}
			else {
				response.setVendorList(dtoAssembler.makeVendorListDto(vendorList));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException | VendorNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getVendors | ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getVendors | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getVendors | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	

	/**
	 * Method to delete a vendor
	 * <p> Author: Marco </p>
	 */
	@Override
	public ReturnCodeResponse deleteVendor(int sessionId, int vendorId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteVendor(vendorId);
				response.setReturnCode(200);
				logger.info("Vendor erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteVendor | ID:" + sessionId + " vendorID:" + vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deleteVendor | ID:" + sessionId + " vendorID:" + vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deleteVendor | ID:" + sessionId + " vendorID:" + vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " + e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to create oder update a vendor
	 * <p> Author: Marco </p>
	 */
	@Override
	public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo, String street, String city, int PLZ, int houseNumber) {
		VendorResponse response = new VendorResponse();
		
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Vendor vendor = user.getVendor(vendorId);
			
				if(vendor == null) {
					ArrayList<String> nameList = new ArrayList<>();
					for (Vendor v : user.getVendors()) {
						nameList.add(v.getName());
					}
					if(!nameList.contains(name)) {
						vendor = dao.createVendor(user, name, logo, street, city, PLZ, houseNumber);
					}
					else {
						response.setReturnCode(400);
						response.setMessage("Vendor already Exists with this name");
						return response;
					}
					
				}
				else {
					vendor.setName(name);
					vendor.setLogo(logo);
					vendor.setStreet(street);
					vendor.setPLZ(PLZ);
					vendor.setCity(city);
					vendor.setHouseNumber(houseNumber);
					vendor = dao.updateVendor(vendor);
				}
				if(vendor != null) {
					response.setVendorTo(dtoAssembler.makeDto(vendor));
					response.setReturnCode(200);
				}
				else {
					throw new VendorNotFoundException("Vendor to update not found");
				}
			}
		}
		catch(NoSessionException | VendorNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateVendor | ID:" + sessionId + " vendorId:" +vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateVendor | ID:" + sessionId + " vendorId:" +vendorId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("createOrUpdateVendor | ID:" + sessionId + " vendorId:" +vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("createOrUpdateVendor | ID:" + sessionId + " vendorId:" +vendorId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	
	/*#################      PAYMENT - SECTION     ##############*/
	
	
	/**
	 * Method to get a payment with the SessionId and the paymentId
	 * <p> Author: Moritz </p>
	 * @param sessionId
	 * @param paymentId
	 * @return PaymentResponse Object
	 */
	@Override
	public PaymentResponse getPayment(int sessionId, int paymentId) {
		PaymentResponse response = new PaymentResponse();
		try {
			Payment payment = getPaymentHelper(sessionId, paymentId);
			if(payment != null) {
				response.setPaymentTo(dtoAssembler.makeDto(payment));
				response.setReturnCode(200);
			}
			else {
				throw new PaymentNotFoundException("Payment not found for this id");
			}
		}
		catch(NoSessionException | PaymentNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getPayment | ID:" + sessionId + " paymentId:" + paymentId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getPayment | ID:" + sessionId + " paymentId:" + paymentId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getPayment | ID:" + sessionId + " paymentId:" + paymentId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Helper method, to find a payment and 
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param paymentId
	 * @return a Payment Object
	 * @throws BudgetOnlineException 
	 */
	private Payment getPaymentHelper(int sessionId, int paymentId) throws  BudgetOnlineException, Exception {
		try {	
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Payment payment = user.getPayment(paymentId);
				return payment;
			}
			else {		
				throw new NoSessionException("Please first login");
			}
		}
		catch (IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getPayment | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getPayment | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getPayment | " + e.getStackTrace().toString());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gives a Response Object with all Payments in a list
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return CategoryListResponse Object
	 */
	@Override
	public PaymentListResponse getPayments(int sessionId) {
		PaymentListResponse response = new PaymentListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Payment> paymentList = user.getPayments();
				if(paymentList.size() == 0) {
					throw new PaymentNotFoundException("No Payments found for this user");
				}
				else {
					response.setPaymentList(dtoAssembler.makePaymentListDto(paymentList));	
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException | PaymentNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getPayments | ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getPayments | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getPayments | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	
	/**
	 * Method to delete a payment
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param paymentId
	 * @return ReturnCodeResponse Object
	 */
	@Override
	public ReturnCodeResponse deletePayment(int sessionId, int paymentId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deletePayment(paymentId);
				response.setReturnCode(200);
				logger.info("Payment erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deletePayment | ID:" + sessionId + " paymentID:" + paymentId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deletePayment | ID:" + sessionId + " paymentID:" + paymentId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deletePayment | ID:" + sessionId + " paymentID:" + paymentId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;	
	}

	/**
	 * Method to create or update a payment
	 * <p> Author: Marco </p>
	 * <p> Author: Moritz </p>
	 * @param sessionId
	 * @param paymentId alte Id zum finden des Update Datensatzes nï¿½tig, bei Neuanlage negativen Wert benutzen
	 * @param name
	 * @param number
	 * @param bic
	 * @param active
	 * @return PaymentResponse
	 */
	@Override
	public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active) {
		PaymentResponse response = new PaymentResponse();
		
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
			
			if (session != null) {
				//Hole User Objekt
				User user = this.dao.findUserByName(session.getUsername());
				//Lege Payment Objekt an
				Payment payment = user.getPayment(paymentId);
			
				if(payment == null) {
					payment = dao.createPayment(user, name, number, bic);
				}
				else {
					payment.setName(name);
					payment.setNumber(number);
					payment.setActive(active);
					payment.setBic(bic);
					payment = dao.updatePayment(payment);
				}
				// Response befüllen
				if( payment != null) {
					response.setPaymentTo(dtoAssembler.makeDto(payment));
					response.setReturnCode(200);
				}
				else {
					throw new PaymentNotFoundException("Payment to update not found");
				}
			}
		}
		catch(NoSessionException | PaymentNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdatePayment | ID:" + sessionId + " paymentId:" +paymentId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("createOrUpdatePayment | ID:" + sessionId + " paymentId:" +paymentId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("createOrUpdatePayment | ID:" + sessionId + " paymentId:" +paymentId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	/*#################      CATEGORY - SECTION     ##############*/

	/**
	 * Method to get a category of the user
	 * <p> Author: Moritz </p>
	 */
	@Override
	public CategoryResponse getCategory(int sessionId, int categoryId) {
		CategoryResponse response = new CategoryResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Category category = user.getCategory(categoryId);
				if(category != null) {
					response.setCategoryTo(dtoAssembler.makeDto(category));	
					response.setReturnCode(200);
				}
				else {
					throw new CategoryNotFoundException("CategoryId: " + categoryId);
				}
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to find all categories of a user
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return List with all Categories
	 * @throws NoSessionException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	private List<Category> getCategoriesHelper(int sessionId) throws NoSessionException, BudgetOnlineException, Exception {
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				return user.getCategories();
			}
			else {
				throw new NoSessionException("Please first login");
			}
		}
		catch(NoSessionException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategories | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			throw new NoTransactionException("getCategories | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getCategories  | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gives a Response Object with all Categories in a list
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return CategoryListResponse Object
	 */
	@Override
	public CategoryListResponse getCategorys(int sessionId) {
		CategoryListResponse response = new CategoryListResponse();
		try {
			List<Category> categoryList = getCategoriesHelper(sessionId);
			if(categoryList.size()==0){
				throw new CategoryNotFoundException("No categories found for this user");
			}
			else {
				response.setCategoryList(dtoAssembler.makeCategoryListDto(categoryList));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategories | ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to get all Categories of a use where income is true
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return CategoryListResponse
	 */
	@Override
	public CategoryListResponse getCategorysOfIncome(int sessionId){
		CategoryListResponse response = new CategoryListResponse();
		try {
			List<Category> categoryList = getCategoriesHelper(sessionId);
			ArrayList<Category> incomeCategories = new ArrayList<>();
			for(Category c : categoryList) {
				if(c.isIncome()) {
					incomeCategories.add(c);
				}
			}
			if(incomeCategories.size() == 0){
				throw new CategoryNotFoundException("No categories found for this user");
			}
			else {
				response.setCategoryList(dtoAssembler.makeCategoryListDto(incomeCategories));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategoriesOfIncome | ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getCategoryOfIncome | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getCategoriesOfIncome | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Helper Method to get all loss categories of a user
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return list with all losscategories of the user
	 */
	private List<Category> getLossCategoriesHelper(int sessionId) throws BudgetOnlineException, Exception{
		try {
			List<Category> categoryList = getCategoriesHelper(sessionId);
			ArrayList<Category> lossCategories = new ArrayList<>();
			for(Category c : categoryList) {
				if(!c.isIncome()) {
					lossCategories.add(c);
				}
			}
			if(lossCategories.size()==0){
				throw new CategoryNotFoundException("No LossCategory found for this user");
			}
			else {
				return lossCategories;
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			throw e;
		}
		catch(BudgetOnlineException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategoriesOfLoss | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getCategoriesOfLoss | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getcategoriesOfLoss | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Helper Method to get all loss categories of a user
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return List with all categories of this user
	 */
	private List<Category> getIncomeCategoriesHelper(int sessionId) throws BudgetOnlineException, Exception{
		try {
			List<Category> categoryList = getCategoriesHelper(sessionId);
			ArrayList<Category> incomeCategories = new ArrayList<>();
			for(Category c : categoryList) {
				if(c.isIncome()) {
					incomeCategories.add(c);
				}
			}
			if(incomeCategories.size()==0){
				throw new CategoryNotFoundException("No IncomeCategory found for this user");
			}
			else {
				return incomeCategories;
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			throw e;
		}
		catch(BudgetOnlineException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategoriesOfIncome | ID:" + sessionId  + "|");
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getCategoriesOfIncome | ID:" + sessionId  + "|");
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getcategoriesOfIncome| ID:" + sessionId  + "|");
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Method to get all Categories of a use where income is false
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return CategoryListResponse
	 */
	@Override
	public CategoryListResponse getCategorysOfLoss(int sessionId){
		CategoryListResponse response = new CategoryListResponse();
		try {
			List<Category> lossCategories = getLossCategoriesHelper(sessionId);
			if(lossCategories.size()==0){
				throw new CategoryNotFoundException("No LossCategory found for this user");
			}
			else {
				response.setCategoryList(dtoAssembler.makeCategoryListDto(lossCategories));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategoriesOfLoss | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getCategoriesOfLoss | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getcategoriesOfLoss | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	
	/**
	 * Method to delete a category
	 * <p> Author: Marco </p>
	 */
	@Override
	public ReturnCodeResponse deleteCategory(int sessionId, int categoryId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteCategory(categoryId);
				response.setReturnCode(200);
				logger.info("Category erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deleteCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deleteCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	


	/**
	 * Method to create oder update a category
	 * <p> Author: Marco </p>
	 */
	@Override
	public CategoryResponse createOrUpdateCategory(int sessionId, int categoryId, boolean income, boolean active,
			String name, String notice, String colour) {
		CategoryResponse response = new CategoryResponse();
		
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
			
			if (session != null) {
				//Hole User Objekt
				User user = this.dao.findUserByName(session.getUsername());
				//Lege Payment Objekt an
				Category category = user.getCategory(categoryId);
				
				
				if(category == null) {
					ArrayList<String> nameList = new ArrayList<>();
					for (Category c : user.getCategories()) {
						nameList.add(c.getName());
					}
					if(!nameList.contains(name)) {
						category = dao.createCategory(user, name, notice, income, colour);
					}
					else {
						response.setReturnCode(400);
						response.setMessage("Category already Exists with this name");
						return response;
					}
				}
				else {
					category.setName(name);
					category.setNotice(notice);
					category.setActive(active);
					category.setIncome(income);
					category.setColour(colour);
					category = dao.updateCategory(category);
				}
				// Response befüllen
				if(category != null) {
					response.setCategoryTo(dtoAssembler.makeDto(category));
					response.setReturnCode(200);
				}
				else {
					throw new CategoryNotFoundException("CategoryId: " + categoryId);
				}
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("createOrUpdateCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("createOrUpdateCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	/*#################      INCOME - SECTION     ##############*/

	/**
	 * Method to get a special income
	 * <p> Author: Moritz </p>
	 */
	@Override
	public IncomeResponse getIncome(int sessionId, int incomeId) {
		IncomeResponse response = new IncomeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if(session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Income income = user.getIncome(incomeId);
				if(income != null) {
					response.setIncomeTo(dtoAssembler.makeDto(income));	
					response.setReturnCode(200);
				}
				else {
					throw new IncomeNotFoundException("IncomeId: " + incomeId);
				}
			}
			else {
				throw new NoSessionException();
			}
		}
		catch(NoSessionException | IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncome | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getIncome | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getIncome | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to get all incomes of a user
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return IncomeListResponse
	 */
	@Override
	public IncomeListResponse getIncomes(int sessionId){
		IncomeListResponse response = new IncomeListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Income> incomes = user.getIncomes(); // List with all Items of the user
				if(incomes.size()==0){
					throw new IncomeNotFoundException("No incomes found for this user");
				}
				else {
					response.setIncomeList(dtoAssembler.makeIncomeListDto(incomes));
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException |IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomes | ID:" + sessionId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getIncomes | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getIncomes | ID:" + sessionId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Helper Method to find incomes with a special category
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param categoryId
	 * @return List with all Incomes, which are assigned to the category
	 */
	private List<Income> getIncomesByCategoryHelper(int sessionId, int categoryId)throws BudgetOnlineException {
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Income> incomeListOfCategory = new ArrayList<Income>(); // List with all Incomes with the category. wird spï¿½ter befï¿½llt
				List<Income> incomes = user.getIncomes(); // List with all Items of the user
				if(incomes.size()==0) {
					throw new IncomeNotFoundException("No incomes found for this user");
				}
				else {
					for (Income i : incomes) {
						if(categoryId == i.getCategory().getId()) {
							incomeListOfCategory.add(i);
						}
					}
				}
				if(incomeListOfCategory.size()>0){
					return incomeListOfCategory;
				}
				else {
					return null;
				}
			}
			else{
				throw new NoSessionException();
			}
		}
		catch(NoSessionException e) {
			throw e;
		}
		catch(BudgetOnlineException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomesByCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
		}
		catch (TransactionRequiredException e) {
			throw new NoTransactionException("getIncomesByCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getIncomesByCategory  | ID:" + sessionId  + "categoryId" + categoryId + "|");
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gets all Incomes of a specific category for incomes
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param categoryId
	 * @return IncomeListResponse
	 */
	@Override
	public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId) {
		IncomeListResponse response = new IncomeListResponse();
		try {
			List<Income> incomeList = getIncomesByCategoryHelper(sessionId, categoryId);
			if(incomeList.size()==0){
				throw new IncomeNotFoundException("No incomes found for this category");
			}
			else {
				response.setIncomeList(dtoAssembler.makeIncomeListDto(incomeList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException | IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomesByCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getIncomeByCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getIncomesByCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Helper Method to get the Amount of all income, which are assigned to a special category
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param categoryId
	 * @return double value of the sum of all items with the category
	 */
	private double getIncomeAmountByCategoryHelper(int sessionId, int categoryId) throws BudgetOnlineException, Exception {
		double sum = 0.0;
		try {
			List<Income> incomeList = getIncomesByCategoryHelper(sessionId, categoryId);
			if(incomeList != null) {
				for(Income i : incomeList) {
					sum = sum + (i.getAmount()*i.getQuantity());
				}
				return sum;
			}
			else {
				return 0.0;
			}
		}
		catch(NoSessionException | ItemNotFoundException e) {
			throw e;
		}
		catch(BudgetOnlineException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemsByLossCategory | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getItemsAmountByLossCategory | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getItemsAmountByLossCategory | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Gets the sum of amounts of the incomes of every single Category
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return AmountListResponse
	 */
	@Override
	public AmountListResponse getIncomesAmountForCategories(int sessionId) {
		AmountListResponse response = new AmountListResponse();
		try {
			List<Category> categoryList = getIncomeCategoriesHelper(sessionId);
			List<AmountTO> amountList = new ArrayList<>();
			for(Category c : categoryList) {
				double value = getIncomeAmountByCategoryHelper(sessionId, c.getId());
				amountList.add(dtoAssembler.makeDto(c.getName(), value));
			}
			response.setReturnCode(200);
			response.setAmountList(amountList);
		}
		catch(NoSessionException | ItemNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomesByCategory | ID:" + sessionId  + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getIncomeAmountForCategories | ID:" + sessionId  + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getIncomesAmountForCategories | ID:" + sessionId  + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
		

	/**
	 * Method to get the last Incomes of a user 
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param startPosition  startPosition of the incomes sorted by date
	 * @param endPosition  endPosition of the incomes sorted by date
	 */
	@Override
	public IncomeListResponse getLastIncomes(int sessionId, int startPosition, int endPosition) {
		IncomeListResponse response = new IncomeListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				String username = session.getUsername();
				ArrayList<Income> incomeList = (ArrayList<Income>) this.dao.getLastIncome(username, startPosition, endPosition);
				if(incomeList.size()==0){
					throw new IncomeNotFoundException("No incomes found for this user");
				}
				else {
					response.setIncomeList(dtoAssembler.makeIncomeListDto(incomeList));
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException | IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getLastIncomes | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getLastIncome | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getLastIncome | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to get all incomes of the actual month
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return List with all Incomes of the actual month
	 */
	private List<Income> getIncomesOfActualMonthHelper(int sessionId) throws NoSessionException, BudgetOnlineException, Exception{
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Income> list = this.dao.getIncomeOfActualMonth(user.getUserName());
				return list;
			}
			else {
				return null;
			}
		}
		catch(NoSessionException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomesOfActualMonth | " + e.getStackTrace().toString());
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getIncomesOfActualMonth | " + e.getStackTrace().toString());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getIncomesOfActualMonth | " + e.getStackTrace().toString());
		}
		catch(Exception e) {
			throw e;
		}
	}
	/**
	 * Gets all income of the actual month
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return AmountResponse
	 */
	@Override
	public AmountResponse getIncomesOfActualMonth(int sessionId) {
		AmountResponse response = new AmountResponse();
		try {
			List<Income> incomeList = getIncomesOfActualMonthHelper(sessionId);
			double amount = 0;
			for (Income i : incomeList) {
				amount = amount + i.getAmount();
			}
			response.setValue(amount);
			response.setReturnCode(200);
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomesOfActualMonth | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getIncomesOfActualMonth | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getIncomesOfActualMonth | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	


	/**
	 * Method to delete an income
	 * <p> Author: Marco </p>
	 */
	@Override
	public ReturnCodeResponse deleteIncome(int sessionId, int incomeId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteIncome(incomeId);
				response.setReturnCode(200);
				logger.info("Income erfolgreich gelï¿½scht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteIncome | ID:" + sessionId + " incomeId:" + incomeId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deleteIncome | ID:" + sessionId + " incomeId:" + incomeId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deleteIncome | ID:" + sessionId + " incomeId:" + incomeId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	/**
	 * Method to create or update an income
	 * <p> Author: Marco </p>
	 */
	@Override
	public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name,
			double quantity, double amount, String notice,  long receiptDate, int categoryId) {
		
		
		IncomeResponse response = new IncomeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if(session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				
				Category category = user.getCategory(categoryId);
				
				Income income = user.getIncome(incomeId);
				
				Date recDate = new Date(receiptDate);
				
				if(income == null) {
					income = dao.createIncome(user, name, notice, quantity, amount, recDate, category);
				}
				else {
					income.setName(name);
					income.setNotice(notice);
					income.setAmount(amount);
					income.setQuantity(quantity);
					income.setReceiptDate(recDate);
					income.setCategory(category);
					
					
					income = dao.updateIncome(income);
				}
				// Response befüllen
				if (income != null) {
					response.setIncomeTo(dtoAssembler.makeDto(income));
					response.setReturnCode(200);
				}
				else {
					throw new IncomeNotFoundException("Income to update not found");
				}
			}
		}
		catch(NoSessionException | IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateIncome | ID:" + sessionId + " incomeId:" +incomeId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateIncome | ID:" + sessionId + " incomeId:" +incomeId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("createOrUpdateIncome | ID:" + sessionId + " incomeId:" +incomeId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("createOrUpdateIncome | ID:" + sessionId + " incomeId:" +incomeId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				logger.error("BudgetOnline | Exception-Fatal - " +be.getMessage());
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		
		return response;	
	}
	
	/*#################      ITEM - SECTION     ##############*/


	/**
	 * Method to get a special item of a special basket
	 * <p> Author: Marco </p>
	 */
	@Override
	public ItemResponse getItemByBasket(int sessionId, int itemId, int basketId) {
		ItemResponse response = new ItemResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Basket basket = user.getBasket(basketId);
				Item item = basket.getItem(itemId);
				if(item != null) {
					response.setItemTo(dtoAssembler.makeDto(item));	
					response.setReturnCode(200);
				}
				else {
					throw new ItemNotFoundException("ItemId: "+ itemId + " | basketId: " + basketId);
				}
			}
			else {
				response.setMessage("Sie sind nicht angemeldet");
			}
		}
		catch(NoSessionException | ItemNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemByBasket | ID:" + sessionId + " itemId:" + itemId + "basketId" + basketId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getItemByBasket | ID:" + sessionId + " itemId:" + itemId + "basketId" + basketId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getItemByBasket | ID:" + sessionId + " itemId:" + itemId + "basketId" + basketId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}


	/**
	 * Method to gett all items of a basket
	 * <p> Author: Marco </p>
	 */
	@Override
	public ItemListResponse getItemsByBasket(int sessionId, int basketId) {
		ItemListResponse response = new ItemListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Basket basket = user.getBasket(basketId);
				if(basket == null){
					throw new BasketNotFoundException("BasketId: " + basketId);
				}
				else {
					List<Item> itemList = basket.getItems();
					if(itemList.size()==0) {
						throw new ItemNotFoundException("Not items in basket with id: " + basketId);
					}
					else {
						response.setItemList(dtoAssembler.makeItemListDto(itemList));
						response.setReturnCode(200);
					}
				}
			}
			else {
				throw new NoSessionException();
			}
		}
		catch(NoSessionException | ItemNotFoundException | BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemsByBasket | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getItemsByBasket | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getItemsByBasket | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}

	/**
	 * Helper Method to find all incomes, which are assigned with a special category
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param categoryId
	 * @return List with all items which are assigned to the category
	 * @throws BudgetOnlineException
	 */
	private List<Item> getItemsByLossCategoryHelper(int sessionId, int categoryId) throws BudgetOnlineException{
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Item> itemList = new ArrayList<Item>(); // List with all Items with the category. wird spï¿½ter befï¿½llt
				List<Basket> baskets = user.getBaskets();
				for (Basket b : baskets) {
					List<Item> items = b.getItems(); // List with all Items of the basket
					for (Item i : items) {
						if(categoryId == i.getCategory().getId()) {
							itemList.add(i);
						}
					}
				}
				if(itemList.size()==0){
					return null;
				}
				else {
					return itemList;
				}
			}
			else {
				throw new NoSessionException();
			}
		}
		catch(NoSessionException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemsByLossCategor | ID:" + sessionId + " categoryId:" + categoryId + "|");
		}
		catch (TransactionRequiredException e) {
			throw new NoTransactionException("getItemsByLossCategor | ID:" + sessionId + " categoryId:" + categoryId + "|");
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getItemsByLossCategor  | ID:" + sessionId + " categoryId:" + categoryId + "|");
		}
		catch(Exception e) {
			throw e;
		}
	}
	/**
	 * Gets all Items of a specific category for losses
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param categoryId
	 * @return ItemListResponse
	 */
	@Override
	public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId) {
		ItemListResponse response = new ItemListResponse();
		try {
			List<Item> itemList = getItemsByLossCategoryHelper(sessionId, categoryId);
			response.setItemList(dtoAssembler.makeItemListDto(itemList));
			response.setReturnCode(200);
		}
		catch(NoSessionException | ItemNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemsByLossCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getItemsByLossCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getItemsBYLossCategory | ID:" + sessionId + " categoryId:" + categoryId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Gets the sum of amounts of the items of every single Category
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @return AmountListResponse
	 */
	@Override
	public AmountListResponse getItemsAmountForCategories(int sessionId) {
		AmountListResponse response = new AmountListResponse();
		try {
			List<Category> categoryList = getLossCategoriesHelper(sessionId);
			List<AmountTO> amountList = new ArrayList<>();
			for(Category c : categoryList) {
				double value = getItemsAmountByLossCategoryHelper(sessionId, c.getId());
				amountList.add(dtoAssembler.makeDto(c.getName(), value));
			}
			response.setReturnCode(200);
			response.setAmountList(amountList);
		}
		catch(NoSessionException | ItemNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemsByLossCategory | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getItemsAmountForCategories | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getItemsAmountForCategories | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Method to get the amount of all items, which are assigned to a special category
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param categoryId
	 * @return double value with the sum of all items which are assigned to the category
	 */
	private double getItemsAmountByLossCategoryHelper(int sessionId, int categoryId) throws BudgetOnlineException {
		double sum = 0;
		try {
			List<Item> itemList = getItemsByLossCategoryHelper(sessionId, categoryId);
			if(itemList != null) {
				for(Item i : itemList) {
					sum = sum + (i.getPrice()* i.getQuantity());
				}
				return sum;
			}
			else {
				return 0.0;
			}
		}
		catch(NoSessionException | ItemNotFoundException e) {
			throw e;
		}
		catch(BudgetOnlineException e) {
			throw e;
		}
		catch(IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemsByLossCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("getItemsAmountByLossCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("getItemsAmountByLossCategory | ID:" + sessionId  + "categoryId" + categoryId + "|");
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Method to delete an item
	 * <p> Author: Marco </p>
	 */
	@Override
	public ReturnCodeResponse deleteItem(int sessionId, int itemId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteItem(itemId);
				response.setReturnCode(200);
				logger.info("Item erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteItem | ID:" + sessionId + " itemId:" + itemId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("deleteItem | ID:" + sessionId + " itemId:" + itemId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("deleteItem | ID:" + sessionId + " itemId:" + itemId + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	/**
	 * Helper method to create or update an item
	 * <p> Author: Marco </p>
	 * @param sessionId
	 * @param itemId
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param receiptDate
	 * @param basketId
	 * @param categoryId
	 * @return ItemObject
	 * @throws Exception
	 */
	private Item createOrUpdateItemHelper(int sessionId, int itemId, String name, double quantity,
			double price, String notice, Timestamp receiptDate, int basketId, int categoryId) throws Exception {
	
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
			//Hole User Objekt
			if(session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				
				//suche basket
				Basket basket = user.getBasket(basketId);
				
				//Lege Item Objekt an
				Item item = basket.getItem(itemId);
				//Suche Category
				Category category = user.getCategory(categoryId);
				
				if(item == null) {	
					item = dao.createItem(name, quantity, price , notice, receiptDate, basket, category);
				}
				else {
					item.setName(name);
					item.setNotice(notice);
					item.setPrice(price);
					item.setQuantity(quantity);
					item.setReceiptDate(receiptDate);
					item.setCategory(category);
					item.setBasket(basket);
					
					
					item = dao.updateItem(item);
				}
				// Response befüllen
				if(item != null) {
					return item;
				}
				else {
					throw new ItemNotFoundException("ItemId: " + itemId);
				}
			}
			else {
				throw new NoSessionException();
			}
		}
		catch(NoSessionException e) {
			throw e;
		}
		catch (ItemNotFoundException e) {
			throw e;
		}
		catch (IllegalArgumentException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateItem | ID:" + sessionId + " itemId:" +itemId + " basketId:" + basketId + " categoryId:" + categoryId + "|");
		}
		catch(EntityExistsException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateItem | ID:" + sessionId + " itemId:" +itemId + " basketId:" + basketId + " categoryId:" + categoryId + "|");
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			throw new NoTransactionException("createOrUpdateItem | ID:" + sessionId + " itemId:" +itemId + " basketId:" + basketId + " categoryId:" + categoryId + "|");
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			throw new RollbackException("createOrUpdateItem | ID:" + sessionId + " itemId:" +itemId + " basketId:" + basketId + " categoryId:" + categoryId + "|");
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			throw e;
		}	
	}
	
	/**
	 * Method to create or update an item
	 * <p> Author: Marco </p>
	 */
	@Override
	public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double quantity,
			double price, String notice, long receiptDate, int basketId, int categoryId) {
		
		ItemResponse response = new ItemResponse();
		
		try {
			Item item = createOrUpdateItemHelper(sessionId, itemId, name, quantity, price, notice, new Timestamp(receiptDate), basketId, categoryId);
			if(item == null) {
				throw new ItemNotFoundException("ItemId: " + itemId);
			}
			else {
				response.setItemTo(dtoAssembler.makeDto(item));
				response.setReturnCode(200);
			}
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateItem | ID:" + sessionId + " itemId:" +itemId + " basketId:" + basketId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateItem | ID:" + sessionId + " itemId:" +itemId + " basketId:" + basketId + " categoryId:" + categoryId + "|");
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.error("BudgetOnline | Exception-Fatal - " +e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(800);
		}		
		return response;
		
	}


	/*#################      XYZ - SECTION     ##############*/

	/**
	 * Method to find all loss of a time period
	 * <p> Author: Moritz </p>
	 * <p> Author: Marco </p>
	 */
	@Override
	public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod) {
		AmountResponse response = new AmountResponse();
		double sum = 0;
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> basketList = user.getBaskets();
				long millisToSubscract = (long) daysOfPeriod*1000*60*60*24;
				Timestamp wishDate = new Timestamp(System.currentTimeMillis() - millisToSubscract);
				for (Basket b : basketList){
					if(b.getPurchaseDate().after(wishDate)){
						sum = sum + b.getAmount();
					}
				}
				response.setValue(sum);
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomeByPeriod | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getLossByPeriod | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getLossByPeriod | " + e.getStackTrace().toString());
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("Fehler getLossbyPeriod:" + e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}


	/**
	 * Method to find all incomes of a time period
	 * <p> Author: Moritz </p>
	 * <p> Author: Marco </p>
	 * @param daysOfPeriod  Anzahl der Tage, die vom heutigen Datum abgezogen werden sollen
	 */
	@Override
	public AmountResponse getIncomeByPeriod(int sessionId, int daysOfPeriod) {
		AmountResponse response = new AmountResponse();
		double sum = 0;
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Income> incomeList = user.getIncomes();
				long millisToSubscract = (long) daysOfPeriod*1000*60*60*24;
				Timestamp wishDate = new Timestamp(System.currentTimeMillis() - millisToSubscract);
				for (Income i : incomeList){
					if(i.getReceiptDate().after(wishDate)){
						sum = sum + i.getAmount();
					}
				}
				response.setValue(sum);
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomeByPeriod | ID:" + sessionId  + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.error("BudgetOnline | TranscationException- " + e.getMessage());
			try {
				throw new NoTransactionException("getIncomeByPeriod | ID:" + sessionId  + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (EJBTransactionRolledbackException e) {
			logger.error("BudgetOnline | EJBTransactionException- " + e.getMessage());
			try {
				throw new RollbackException("getIncomeByPeriod | ID:" + sessionId  + "|");
			} 
			catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.error("Fehler getIncomebyPeriod:" + e.getMessage());
			response.setReturnCode(800);
		}
		return response;
	}
	
	
	public ReturnCodeResponse generateReport(int sessionId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		/*
		try
		{
			BudgetSession session = getSession(sessionId);
			//Nur wenn gültiger User, macht Reportanforderung sinn 
			if (session != null) {
				reportGenerator.build(session);
			}
			else
			{
				response.setReturnCode(404);
			}
			
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(Exception e) {
			logger.error("Fehler generateReport:" + e.getMessage());
			response.setReturnCode(800);
		}
		
		*/
		return response;
	}
}
