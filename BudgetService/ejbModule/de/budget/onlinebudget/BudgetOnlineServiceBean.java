package de.budget.onlinebudget;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;








//Logger-Import
import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.EJB;
//import javax.ejb.Remote;
import javax.ejb.Stateless;


import javax.jws.WebService;



import javax.persistence.EntityExistsException;







//Interface-Import
import de.budget.common.BudgetOnlineService;

//DAO-Import
import de.budget.dao.BudgetOnlineDAOLocal;
//Response-Import @author Moritz

import de.budget.dto.BasketTO;
import de.budget.dto.CategoryTO;
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
import de.budget.Exception.InvalidLoginException;
import de.budget.Exception.InvalidPasswordException;
import de.budget.Exception.ItemNotFoundException;
import de.budget.Exception.NoSessionException;
import de.budget.Exception.PaymentNotFoundException;
import de.budget.Exception.UsernameAlreadyExistsException;
import de.budget.Exception.BasketNotFoundException;
import de.budget.Exception.VendorNotFoundException;
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
//@Remote(BudgetOnlineService.class)
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
			throw new NoSessionException("Please first login");
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
	 * @param Username 
	 * @param password
	 * @return UserLoginResponse
	 * 
	 */
	@Override
	public UserLoginResponse login(String username, String password) {
		UserLoginResponse response = new UserLoginResponse();
		try 
		{
			User user = this.dao.findUserByName(username);		
			if (user != null && user.getPassword().equals(password)) 
			{
				int sessionId = dao.createSession(user);
				
				logger.info("Login erfolgreich. Session=" + sessionId);
				response.setSessionId(sessionId);
				// Request OK zurückschicken
				response.setReturnCode(200);
				int payloadNumber = payloader.getPayload();
				payloader.setPayload(payloadNumber+1);
			}
			else 
			{
				logger.info("Login fehlgeschlagen, da User unbekannt oder Passwort falsch. username=" + username);
				throw new InvalidLoginException("Login fehlgeschlagen, da User unbekannt oder Passwort falsch. username="+ username);
			}
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
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
			response.setReturnCode(404);
			response.setMessage("You must be logged in to log out");
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
					else {
						response.setReturnCode(408);
						response.setMessage("Could not create a user");
					}
				}
				else {
					throw new InvalidPasswordException("Your password is too short. It must be at least 8 symbols.");
				}
			}
			else {
				throw new UsernameAlreadyExistsException("Username has already been taken. Please try again.");
			}
		}
		catch(InvalidPasswordException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(BudgetOnlineException be) {
			response.setReturnCode(be.getErrorCode());
			response.setMessage(be.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Could not create a user");
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
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
				userResp.setUserTo(dtoAssembler.makeDto(user));	
				userResp.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			userResp.setReturnCode(e.getErrorCode());
			userResp.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			userResp.setReturnCode(404);
			userResp.setMessage("Could not find username");
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("User to delete not found.");
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
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				
				for(Basket b : basketsOfUser) {
					if(vendorId == b.getVendor().getId()){
						basketList.add(b);
					}
				}
				response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;	
	}
	
	/**
	 * Gets all baskets of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BasketListResponse getBasketsOfActualMonth(int sessionId) {
		BasketListResponse response = new BasketListResponse();
		int actualMonth = new Timestamp(System.currentTimeMillis()).getMonth();
		int actualYear = new Timestamp(System.currentTimeMillis()).getYear();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Basket> basketList = user.getBaskets();
				ArrayList<Basket> resultList = new ArrayList<>();
				for (Basket b : basketList) {
					int basketMonth = b.getPurchaseDate().getMonth();
					int basketYear = b.getPurchaseDate().getYear();
					if(basketMonth == actualMonth && basketYear == actualYear) {
						resultList.add(b);
					}
				}
				response.setBasketList(dtoAssembler.makeBasketListDto(resultList));
				response.setReturnCode(200);
			} 
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				
				for(Basket b : basketsOfUser) {
					if(paymentId == b.getPayment().getId()){
						basketList.add(b);
					}
				}
				response.setBasketList(dtoAssembler.makeBasketListDto(basketList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
					throw new BasketNotFoundException("Coundn't find basket for this payment");
				}
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Basket to delete not found.");
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
	public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String name, String notice, double amount, Timestamp purchaseDate, int paymentId, int vendorId, List<ItemTO> items) {
		BasketResponse response = new BasketResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				Basket basket = user.getBasket(basketId);
				Payment payment = user.getPayment(paymentId);
				Vendor vendor = user.getVendor(vendorId);
				//wandle empfangene ItemTOs in ItemObjekte
				ArrayList<Item> itemList = new ArrayList<>();
				for(ItemTO iTo : items) {
					int itemId = iTo.getId();
					String itemName = iTo.getName();	
					double itemQuantity = iTo.getQuantity();	
					double itemPrice = iTo.getPrice();	
					String itemNotice = iTo.getNotice();		
					Timestamp itemReceiptDate = iTo.getReceiptDate();	
					int itemBasketId = iTo.getBasket().getId();
					int itemCategoryId = iTo.getCategory().getId();

					Item item = createOrUpdateItemHelper(sessionId, itemId, itemName, itemQuantity, itemPrice, itemNotice, itemReceiptDate, itemBasketId, itemCategoryId);
					itemList.add(item);
				}
				
				if(basket == null) {
					basket = dao.createBasket(user, name, notice, amount, purchaseDate, payment, vendor, itemList);
				}
				else {
					basket.setName(name);
					basket.setNotice(notice);
					basket.setAmount(amount);
					basket.setPurchaseDate(purchaseDate);
					basket.setPayment(payment);
					basket.setVendor(vendor);
					basket.setItems(itemList);
				
					basket = dao.updateBasket(basket);
				}
				if (basket != null) {
					response.setBasketTo(dtoAssembler.makeDto(basket));
					response.setReturnCode(200);
				}
				else {
					throw new BasketNotFoundException("Basket to update not found");
				}
				
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(BasketNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch (VendorNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				response.setVendorList(dtoAssembler.makeVendorListDto(vendorList));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Vendor to delete not found.");
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
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch (VendorNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
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
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(PaymentNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
	 */
	private Payment getPaymentHelper(int sessionId, int paymentId) throws NoSessionException, IllegalArgumentException {
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
				response.setPaymentList(dtoAssembler.makePaymentListDto(paymentList));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Payment to delete not found.");
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
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch (PaymentNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
		}
		/*Moritz -> Catchblock wird niemals erreicht !
		catch (BudgetOnlineException e) {
			response.setReturnCode(404);
			response.setMessage("Couldn't create a payment.");
		}
		*/
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
					throw new CategoryNotFoundException("Category not found for this id");
				}
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
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
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Category> categoryList = user.getCategories();
				response.setCategoryList(dtoAssembler.makeCategoryListDto(categoryList));	
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Category to delete not found.");
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
					category = dao.createCategory(user, name, notice, income, colour);
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
					throw new CategoryNotFoundException("Category to update not found");
				}
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(CategoryNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
					throw new IncomeNotFoundException("No income found for this id");
				}
			}
			else {
				response.setMessage("Sie sind nicht eingeloggt");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				for (Income i : incomes) {
					if(categoryId == i.getCategory().getId()) {
						incomeList.add(i);
					}
				}
				response.setIncomeList(dtoAssembler.makeIncomeListDto(incomeList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				response.setIncomeList(dtoAssembler.makeIncomeListDto(incomeList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Gets all income of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
	 */
	@Override
	public IncomeListResponse getIncomesOfActualMonth(int sessionId) {
		IncomeListResponse response = new IncomeListResponse();
		int actualMonth = new Timestamp(System.currentTimeMillis()).getMonth();
		int actualYear = new Timestamp(System.currentTimeMillis()).getYear();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				List<Income> incomeList = user.getIncomes();
				ArrayList<Income> resultList = new ArrayList<>();
				for (Income i : incomeList) {
					//TODO launchDate ist nicht ideal (überdenken wie es bei wiederkehrenden Einnahmen behandelt wird
					//Moritz-> nun auf Anlegedatum geändert, 
					int incomeMonth = i.getCreateDate().getMonth(); 
					int incomeYear = i.getCreateDate().getYear();
					if(incomeMonth == actualMonth && incomeYear == actualYear) {
						resultList.add(i);
					}
				}
				response.setIncomeList(dtoAssembler.makeIncomeListDto(resultList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Income to delete not found.");
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
			double quantity, double amount, String notice, int period,
			Timestamp launchDate, Timestamp finishDate,
			int categoryId) {
		
		
		IncomeResponse response = new IncomeResponse();
		
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
			//Hole User Objekt
			if(session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				//Lege Payment Objekt an
				Income income = user.getIncome(incomeId);
				//Suche Category
				Category category = dao.findCategoryById(categoryId);
				
				if(income == null) {	
					income = dao.createIncome(user, name, notice, quantity, amount, period, launchDate, finishDate, category);
				}
				else {
					income.setName(name);
					income.setNotice(notice);
					income.setAmount(amount);
					income.setQuantity(quantity);
					income.setPeriod(period);
					income.setLaunchDate(launchDate);
					income.setFinishDate(finishDate);
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
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IncomeNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Income to delete not found.");
		}
		/*Moritz-> Catchblock wird niemals erreicht, da alle anderen alles abhandeln
		catch (BudgetOnlineException e) {
			response.setReturnCode(404);
			response.setMessage("Couldn't create a income.");
		}
		*/
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
					throw new ItemNotFoundException("No Item found with the id "+ itemId + " in the basket " + basketId);
				}
			}
			else {
				response.setMessage("Sie sind nicht angemeldet");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(ItemNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				List<Item> itemList = basket.getItems();
				response.setItemList(dtoAssembler.makeItemListDto(itemList));
				response.setReturnCode(200);
			}
			else {
				response.setReturnCode(400);
				response.setMessage("Keine Session gefunden");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
				response.setItemList(dtoAssembler.makeItemListDto(itemList));
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
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
			response.setMessage(e.getMessage());
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Item to delete not found.");
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
					throw new ItemNotFoundException("Item could not be updated");
				}
			}
			else {
				throw new NoSessionException("Please first login");
			}
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
			double price, String notice, Timestamp receiptDate, int basketId, int categoryId) {
		
		ItemResponse response = new ItemResponse();
		
		try {
			Item item = createOrUpdateItemHelper(sessionId, itemId, name, quantity, price, notice, receiptDate, basketId, categoryId);
			
			response.setItemTo(dtoAssembler.makeDto(item));
			response.setReturnCode(200);
			
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch (ItemNotFoundException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(404);
			response.setMessage("Couldn't create a item.");
		}
		catch (IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage("Item not found.");
		}
		catch(EntityExistsException e) {
			response.setReturnCode(600);
			response.setMessage("Entity allready exists");
		} catch (Exception e) {
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
		catch(Exception e) {
			logger.info("FehlergetLossbyPeriod:" + e.getMessage());
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
				for (Income i : incomeList) {
					if(i.getPeriod() == daysOfPeriod) {
						sum += (i.getAmount() * i.getQuantity());
					}
				}
				response.setValue(sum);
				response.setReturnCode(200);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info("FehlergetIncomebyPeriod:" + e.getMessage());
		}
		return response;
		
	}


}
