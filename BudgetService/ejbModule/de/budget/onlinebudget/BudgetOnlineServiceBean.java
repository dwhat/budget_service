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

//Exception-Import
import de.budget.Exception.BudgetOnlineException;
import de.budget.Exception.CategoryNotFoundException;
import de.budget.Exception.IncomeNotFoundException;
import de.budget.Exception.InvalidPasswordException;
import de.budget.Exception.ItemNotFoundException;
import de.budget.Exception.NoSessionException;
import de.budget.Exception.PaymentNotFoundException;
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
import de.budget.onlinebudget.OutputRequesterBean;


/**
 * Stateless-Beanimplementierung von BudgetOnlineService 
 * @author Moritz
 * @author Marco
 *
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
	private OutputRequesterBean outputRequester;
	
	@EJB
	private Payload payloader;
	
	/**
	 * Private HilfsMethode zum Laden der Session aus der Datenbank
	 * @author Marco
	 * @param sessionId
	 * @return BudgetSession Object
	 * @throws NoSessionException
	 */
	private BudgetSession getSession(int sessionId) throws NoSessionException {
		BudgetSession session = dao.findSessionById(sessionId);
		if (session==null) {
			throw new NoSessionException();
		}
		else {
			return session;
		}
	}

	/*#################      USER - SECTION     ##############*/
	
	/**
	 * Session anhand username und password erstellen und in ResponseObject zurückliefern
	 * 
	 * @author Moritz
	 * @author Marco
	 * @param Username 
	 * @param password
	 * @return UserLoginResponse
	 * 
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
		return response;
	}

	/**
	 * Method to logout. Deletes a session
	 * 
	 * @author Moritz
	 * @date 19.05.2015
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
		return response;	
	}

	
	/**
	 * Method to register a new user
	 * @author Marco
	 * @date 18.05.2015
	 * Method to register a new user
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
		return response;
	}
	

	/**
	 * Method to find an user by name
	 * @author Moritz
	 * @author Marco
	 * @date 30.05.2015
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return userResp;
	}


	/**
	 * Method to delete the own user
	 * @author Marco
	 * @date 26.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteUser | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}

	/*#################      BASKET - SECTION     ##############*/

	/**
	 * Method to get the last baskets of a user
	 * @author Marco
	 * @date 28.05.2015
	 * @param sessionId
	 * @param numberOfBaskets   Anzahl der geï¿½nschten letzten Baskets
	 * @return BasketListResponse
	 */
	@Override
	public BasketListResponse getLastBaskets(int sessionId, int numberOfBaskets) {
		BasketListResponse response = new BasketListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				String username = user.getUserName();
				ArrayList<Basket> basketList = (ArrayList<Basket>) this.dao.getLastBaskets(username, numberOfBaskets);
				response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;	
	}

	/**
	 * Method to get all baskets of a specific vendor
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param vendorId
	 * @return a list with all baskets of a vendor
	 */
	@Override
	public BasketListResponse getBasketsByVendor(int sessionId, int vendorId){
		BasketListResponse response = new BasketListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> basketList = new ArrayList<>(); //List with all baskets of the vendor, wird spï¿½ter befï¿½llt
				List<Basket> basketsOfUser = user.getBaskets(); //List with all baskets of the user
				
				if(basketList.size() == 0){
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
					response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
					response.setReturnCode(200);
				}
				else {
					throw new BasketNotFoundException("Not baskets found for this vendor");
				}
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;	
	}
	
	/**
	 * Method to get all incomes of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
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
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gets all baskets of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}

	/**
	 * Gets all baskets of a specific payment
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param paymentId
	 * @return
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;		
	}
	
	/**
	 * Method to get a Basket with the SessionId and the basketId
	 * @author Marco
	 * @date 18.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBasket | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Gives a Response Object with all Baskets in a list
	 * @author Marco
	 * @date 19.05.2015
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
				response.setBasketList(dtoAssembler.makeBasketListDto(basketList));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getBaskets | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Method to delete a basket
	 * @author Marco
	 * @date 26.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteBasket | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	

	/**
	 * Method to create or update a basket
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, long purchaseDate, int paymentId, int vendorId, List<ItemTO> items) {
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
					//TODO
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
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateBasket | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateBasket | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}


	/*#################      VENDOR - SECTION     ##############*/
	
	
	/**
	 * Method to get a Vendor with the SessionId and the vendorId
	 * @author Marco
	 * @date 18.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getVendor | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
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
	public VendorListResponse getVendors(int sessionId) {
		VendorListResponse response = new VendorListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Vendor> vendorList = user.getVendors();
				if(vendorList.size() == 0) {
					throw new VendorNotFoundException("Not vendors found of this user");
				}
				else {
					response.setVendorList(dtoAssembler.makeVendorListDto(vendorList));	
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException | VendorNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getVendors | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	

	/**
	 * Method to delete a vendor
	 * @author Marco
	 * @date 26.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteVendor | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Method to create oder update a vendor
	 * @author Marco
	 * @date 26.05.2015
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
					vendor = dao.createVendor(user, name, logo, street, city, PLZ, houseNumber);
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateVendor | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateVendor | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}

	
	/*#################      PAYMENT - SECTION     ##############*/
	
	
	/**
	 * Method to get a payment with the SessionId and the paymentId
	 * @author Moritz
	 * @date 30.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getPayment | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Helper method, to find a payment and 
	 * @author Marco
	 * @date 01.06.2015
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
		catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gives a Response Object with all Payments in a list
	 * @author Marco
	 * @date 19.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getPayments | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * Method to delete a payment
	 * @author Marco
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deletePayment | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;	
	}

	/**
	 * Method to create or update a payment
	 * @author Marco
	 * @author Moritz
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
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdatePayment | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		return response;
	}

	/*#################      CATEGORY - SECTION     ##############*/

	/**
	 * Method to get a category of the user
	 * @author Moritz
	 * @date 30.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategory | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Method to find all categories of a user
	 * @author Marco
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
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Gives a Response Object with all Categories in a list
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return CategoryListResponse Object
	 */
	@Override
	public CategoryListResponse getCategorys(int sessionId) {
		CategoryListResponse response = new CategoryListResponse();
		try {
			List<Category> categoryList = getCategoriesHelper(sessionId);
			response.setCategoryList(dtoAssembler.makeCategoryListDto(categoryList));	
			response.setReturnCode(200);
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategories | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Method to get all Categories of a use where income is true
	 * @author Marco
	 * @date 09.06.2015
	 * @param sessionId
	 * @return
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getCategoriesOfIncome | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * Method to get all Categories of a use where income is false
	 * @author Marco
	 * @date 09.06.2015
	 * @param sessionId
	 * @return
	 */
	@Override
	public CategoryListResponse getCategorysOfLoss(int sessionId){
		CategoryListResponse response = new CategoryListResponse();
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
				response.setCategoryList(dtoAssembler.makeCategoryListDto(lossCategories));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException | CategoryNotFoundException e) {
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * Method to delete a category
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteCategory(int sessionId, int categoryId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteCategory(categoryId);
				response.setReturnCode(200);
				logger.info("Category erfolgreich gelï¿½scht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteCategory | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	


	/**
	 * Method to create oder update a category
	 * @author Marco
	 * @date 26.05.2015
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
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateCategory | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateCategory | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}

	/*#################      INCOME - SECTION     ##############*/

	/**
	 * Method to get a special income
	 * @author Moritz
	 * @date 30.05.2015
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Method to get all incomes of a user
	 * @author Marco
	 * @date 09.06.2015
	 * @param sessionId
	 * @return
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomes | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	/**
	 * Gets all Incomes of a specific category for incomes
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param categoryId
	 * @return
	 */
	@Override
	public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId) {
		IncomeListResponse response = new IncomeListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Income> incomeList = new ArrayList<Income>(); // List with all Incomes with the category. wird spï¿½ter befï¿½llt
				List<Income> incomes = user.getIncomes(); // List with all Items of the user
				if(incomes.size()==0) {
					throw new IncomeNotFoundException("No incomes found for this user");
				}
				else {
					for (Income i : incomes) {
						if(categoryId == i.getCategory().getId()) {
							incomeList.add(i);
						}
					}
					if(incomeList.size()==0){
						throw new IncomeNotFoundException("No incomes found for this category");
					}
					else {
						response.setIncomeList(dtoAssembler.makeIncomeListDto(incomeList));
						response.setReturnCode(200);
					}
				}
			}
		}
		catch(NoSessionException | IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getIncomesByCategory | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	

	/**
	 * Method to get the last Incomes of a user
	 * @author Marco
	 * @date 28.05.2015
	 * @param sessionId
	 * @param numberOfIncome   Anzahl der gewï¿½nschten letzten Incomes
	 */
	@Override
	public IncomeListResponse getLastIncomes(int sessionId, int numberOfIncome) {
		IncomeListResponse response = new IncomeListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				String username = user.getUserName();
				ArrayList<Income> incomeList = (ArrayList<Income>) this.dao.getLastIncome(username, numberOfIncome);
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	/**
	 * Method to get all incomes of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
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
		catch(Exception e) {
			throw e;
		}
	}
	/**
	 * Gets all income of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	


	/**
	 * Method to delete an income
	 * @author Marco
	 * @date 26.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteIncome | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Method to create or update an income
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name,
			double quantity, double amount, String notice,  long receiptDate, int categoryId) {
		
		
		IncomeResponse response = new IncomeResponse();
		logger.info("CreateOrUpdateIncome aufger---------------------------------------------");
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
			//Hole User Objekt
			if(session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				
				Category category = user.getCategory(categoryId);
				
				Income income = user.getIncome(incomeId);
				
				Date recDate = new Date(receiptDate);
				logger.info(sessionId + " " + incomeId +" " + name +" " + quantity +" " + amount +" " + notice +" " + recDate +" " + categoryId);
				
				if(income == null) {
					logger.info("Income gleich null -----------------------------");
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
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateIncome | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateIncome | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (TransactionRequiredException e) {
			logger.info("xyz/TranscationException- " + e.getMessage());
		}
		catch (EJBTransactionRolledbackException e) {
			logger.info("xyz/EJBTransactionException- " + e.getMessage());
		}
		return response;	
	}
	
	/*#################      ITEM - SECTION     ##############*/


	/**
	 * Method to get a special item of a special basket
	 * @author Marco
	 * @date 26.05.2015
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
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "getItemByBasket | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}


	/**
	 * Method to gett all items of a basket
	 * @author Marco
	 * @date 26.05.2015
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}

	/**
	 * Gets all Items of a specific category for losses
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param categoryId
	 * @return
	 */
	@Override
	public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId) {
		ItemListResponse response = new ItemListResponse();
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
					throw new ItemNotFoundException("No items found for this category");
				}
				else {
					response.setItemList(dtoAssembler.makeItemListDto(itemList));
					response.setReturnCode(200);
				}
			}
		}
		catch(NoSessionException | ItemNotFoundException e) {
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
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * Method to delete an item
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteItem(int sessionId, int itemId) {
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				dao.deleteItem(itemId);
				response.setReturnCode(200);
				logger.info("Item erfolgreich gelï¿½scht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "deleteItem | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Helper method to create or update an item
	 * @author Marco
	 * @param sessionId
	 * @param itemId
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param receiptDate
	 * @param basketId
	 * @param categoryId
	 * @return ItemObjekt
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
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateItem | " + e.getStackTrace().toString());

		}
		catch(EntityExistsException e) {
			throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateItem | " + e.getStackTrace().toString());
		} 
		catch (Exception e) {
			throw e;
		}	
	}
	
	/**
	 * Method to create or update an item
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double quantity,
			double price, String notice, long receiptDate, int basketId, int categoryId) {
		
		ItemResponse response = new ItemResponse();
		
		try {
			Item item = createOrUpdateItemHelper(sessionId, itemId, name, quantity, price, notice, new Timestamp(receiptDate), basketId, categoryId);
			
			response.setItemTo(dtoAssembler.makeDto(item));
			response.setReturnCode(200);
			
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getErrorMessage());
		}
		catch(IllegalArgumentException e) {
			try {
				throw new BudgetOnlineException(500, "ILLEGAL_ARGUMENT_EXCEPTION", "createOrUpdateItem | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch(EntityExistsException e) {
			try {
				throw new BudgetOnlineException(600, "ENTITY_EXISTS_EXCEPTION", "createOrUpdateItem | " + e.getStackTrace().toString());
			} catch(BudgetOnlineException be) {
				response.setReturnCode(be.getErrorCode());
				response.setMessage(be.getErrorMessage());
			}
		}
		catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setReturnCode(404);
		}		
		return response;
		
	}


	/*#################      XYZ - SECTION     ##############*/

	/**
	 * Method to find all loss of a time period
	 * @author Moritz
	 * @date 07.06.2015
	 */
	@Override
	public AmountResponse getLossByPeriod(int sessionId, int daysOfPeriod) {
		AmountResponse response = new AmountResponse();
		try {
			double sum = 0;
			//TODO
			response.setValue(sum);
			response.setReturnCode(200);
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
		catch(Exception e) {
			logger.info("FehlergetIncomebyPeriod:" + e.getMessage());
		}
		return response;
	}


	/**
	 * Method to find all incomes of a time period
	 * @author Moritz
	 * @date 07.06.2015
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
				//TODO
				
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
		catch(Exception e) {
			logger.info("Fehler getIncomebyPeriod:" + e.getMessage());
		}
		return response;
		
	}


}
