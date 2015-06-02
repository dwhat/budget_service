package de.budget.onlinebudget;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;





//Logger-Import
import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;


import javax.jws.WebService;



//Interface-Import
import de.budget.common.BudgetOnlineService;

//DAO-Import
import de.budget.dao.BudgetOnlineDAOLocal;
//Response-Import @author Moritz

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
import de.budget.Exception.InvalidLoginException;


import de.budget.Exception.NoSessionException;
import de.budget.Exception.UsernameAlreadyExistsException;
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


//TODO überall Session auf null prüfen um nullpointer zu vermeiden, siehe getItemByLossCategory

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
	
	/**
	 * @author Marco
	 * @param sessionId
	 * @return BudgetSession Object
	 * @throws NoSessionException
	 */
	private BudgetSession getSession(int sessionId) throws NoSessionException {
		BudgetSession session = dao.findSessionById(sessionId);
		if (session==null) {
			throw new NoSessionException("Bitte zunächst ein Login durchführen.");
		}
		else {
			return session;
		}
	}

	/*#################      USER - SECTION     ##############*/
	
	/**
	 * Session anhand username und password erstellen und zurückliefern
	 * 
	 * @author Moritz
	 * @param Username 
	 * @param password
	 * @return UserSession
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
			}
			else 
			{
				logger.info("Login fehlgeschlagen, da Kunde unbekannt oder Passwort falsch. username=" + username);
				response.setReturnCode(404);
				throw new InvalidLoginException("Login fehlgeschlagen, da Kunde unbekannt oder Passwort falsch. username="+ username);
			}
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
			//evtl darauf einigen einen 404(Not-Found) zurückzuschicken 
		}
		return response;
	}

	/**
	 * UserSession zerstören
	 * 
	 * @author Moritz
	 * 
	 */
	@Override
	public ReturnCodeResponse logout(int sessionId)  {
		
		ReturnCodeResponse response = new ReturnCodeResponse();
		try {
			dao.closeSession(sessionId);	
			logger.info("Logout erfolgreich. Session=" + sessionId);
			response.setReturnCode(200);
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
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
			
			// TODO prüfen ob Username bereits verwendet wurde
			logger.info("Versuche neuen User anzulegen. Name=" + username);
			User user = dao.createUser(username, password, email);
			if (user != null) {
				int sessionId = dao.createSession(user);
				response.setSessionId(sessionId);
				logger.info("User angelegt. Session=" + sessionId);
				response.setReturnCode(200);
			}
			else {
				response.setReturnCode(409);
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
	 * Method to find an user by name
	 * @author Moritz
	 * @author Marco
	 * @date 30.05.2015
	 */
	@Override
	public UserResponse getUserByName(int sessionId, String userName) {
		UserResponse UserResp = new UserResponse();	
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			UserResp.setUserTo(dtoAssembler.makeDto(user));	
		}
		catch(NoSessionException e) {
			UserResp.setReturnCode(500);
			UserResp.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			UserResp.setReturnCode(500);
			UserResp.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		
		return UserResp;
		
	}


	/**
	 * Method to delete an user
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
					logger.info("User erfolgreich gelöscht");
				}
				else {
					response.setMessage("Sie können keinen fremden User löschen");
				}
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
	 * @param numberOfBaskets   Anzahl der geünschten letzten Baskets
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
				response.setReturnCode(0);
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				List<Basket> basketList = new ArrayList<>(); //List with all baskets of the vendor, wird später befüllt
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
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				response.setReturnCode(0);
			} 
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				List<Basket> basketList = new ArrayList<>(); //List with all baskets of the payment, wird später befüllt
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
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				response.setBasketTo(dtoAssembler.makeDto(basket));	
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
			User user = this.dao.findUserByName(session.getUsername());
			List<Basket> basketList = user.getBaskets();
			response.setBasketList(dtoAssembler.makeBasketListDto(basketList));	
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				logger.info("Basket erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
	public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String notice,
			double amount, Timestamp purchaseDate, int paymentId, int vendorId, List<Item> items) {
		BasketResponse basketResp = new BasketResponse();
		
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			Basket basket = user.getBasket(basketId);
			Payment payment = user.getPayment(paymentId);
			Vendor vendor = user.getVendor(vendorId);

			if(basket == null) {
				basket = dao.createBasket(user, notice, amount,purchaseDate,payment,vendor,items);
			}
			else {
				basket.setNotice(notice);
				basket.setAmount(amount);
				basket.setPurchaseDate(purchaseDate);
				basket.setPayment(user.getPayment(paymentId));
				basket.setVendor(user.getVendor(vendorId));
				basket.setItems(items);
				
				basket = dao.updateBasket(basket);
			}
			basketResp.setBasketTo(dtoAssembler.makeDto(basket));
		}
		catch(NoSessionException e) {
			basketResp.setReturnCode(500);
			basketResp.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			basketResp.setReturnCode(500);
			basketResp.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return basketResp;
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
			User user = this.dao.findUserByName(session.getUsername());
			Vendor vendor = user.getVendor(vendorId);
			response.setVendorTo(dtoAssembler.makeDto(vendor));	
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
			User user = this.dao.findUserByName(session.getUsername());
			List<Vendor> vendorList = user.getVendors();
			response.setVendorList(dtoAssembler.makeVendorListDto(vendorList));	
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				logger.info("Vendor erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
	public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo) {
		VendorResponse vendorResp = new VendorResponse();
		
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			Vendor vendor = user.getVendor(vendorId);
			
			if(vendor == null) {
				vendor = dao.createVendor(user, name, logo);
			}
			else {
				vendor.setName(name);
				vendor.setLogo(logo);
				vendor = dao.updateVendor(vendor);
			}
			vendorResp.setVendorTo(dtoAssembler.makeDto(vendor));
		}
		catch(NoSessionException e) {
			vendorResp.setReturnCode(500);
			vendorResp.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			vendorResp.setReturnCode(500);
			vendorResp.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return vendorResp;
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
			response.setPaymentTo(dtoAssembler.makeDto(payment));
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
		BudgetSession session = getSession(sessionId);
		if (session != null) {
			User user = this.dao.findUserByName(session.getUsername());
			Payment payment = user.getPayment(paymentId);
			return payment;
		}
		else {		
			return null;
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
			User user = this.dao.findUserByName(session.getUsername());
			List<Payment> paymentList = user.getPayments();
			response.setPaymentList(dtoAssembler.makePaymentListDto(paymentList));	
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				logger.info("Payment erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
	 * @param paymentId alte Id zum finden des Update Datensatzes nötig, bei Neuanlage negativen Wert benutzen
	 * @param name
	 * @param number
	 * @param bic
	 * @param active
	 * @return PaymentResponse
	 */
	@Override
	public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active) {
		PaymentResponse paymentResp = new PaymentResponse();
		
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
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
			paymentResp.setPaymentTo(dtoAssembler.makeDto(payment));
		}
		catch (BudgetOnlineException e) {
			paymentResp.setReturnCode(404);
			paymentResp.setMessage("Couldn't create a payment.");
		}
		return paymentResp;
		
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
			User user = this.dao.findUserByName(session.getUsername());
			Category category = user.getCategory(categoryId);
			response.setCategoryTo(dtoAssembler.makeDto(category));	
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
			User user = this.dao.findUserByName(session.getUsername());
			List<Category> categoryList = user.getCategories();
			response.setCategoryList(dtoAssembler.makeCategoryListDto(categoryList));	
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				logger.info("Category erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
			String name, String notice) {
		CategoryResponse categoryResp = new CategoryResponse();
		
		try {
			// Hole SessionObjekt
			BudgetSession session = getSession(sessionId);
			//Hole User Objekt
			User user = this.dao.findUserByName(session.getUsername());
			//Lege Payment Objekt an
			Category category = user.getCategory(categoryId);
			
			if(category == null) {
				category = dao.createCategory(user, name, notice, income);
			}
			else {
				category.setName(name);
				category.setNotice(notice);
				category.setActive(active);
				category.setIncome(income);
				category = dao.updateCategory(category);
			}
			// Response befüllen
			categoryResp.setCategoryTo(dtoAssembler.makeDto(category));
		}
		catch(NoSessionException e) {
			categoryResp.setReturnCode(500);
			categoryResp.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			categoryResp.setReturnCode(500);
			categoryResp.setMessage(e.getMessage());
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		return categoryResp;
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
				response.setIncomeTo(dtoAssembler.makeDto(income));	
			}
			else {
				response.setMessage("Sie sind nicht eingeloggt");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				List<Income> incomeList = new ArrayList<Income>(); // List with all Incomes with the category. wird später befüllt
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
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
	 * @param numberOfIncome   Anzahl der gewünschten letzten Incomes
	 */
	@Override
	public IncomeListResponse getLastIncomes(int sessionId, int numberOfIncome) {
		IncomeListResponse response = new IncomeListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				//TODO SQL Abfrage
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
		try {
			BudgetSession session = getSession(sessionId);
			if (session != null) {
				User user = this.dao.findUserByName(session.getUsername());
				//TODO SQL Abfrage
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				logger.info("Income erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
			double quantity, double price, String notice, int period,
			Timestamp launchDate, Timestamp finishDate, int basketId,
			int categoryId) {
		// TODO Auto-generated method stub
		return null;
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
				response.setItemTo(dtoAssembler.makeDto(item));	
			}
			else {
				response.setMessage("Sie sind nicht angemeldet");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
			}
			else {
				response.setReturnCode(400);
				response.setMessage("Keine Session gefunden");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				List<Item> itemList = new ArrayList<Item>(); // List with all Items with the category. wird später befüllt
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
			response.setReturnCode(500);
			response.setMessage(e.getMessage());
		}
		catch(IllegalArgumentException e) {
			response.setReturnCode(500);
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
				logger.info("Item erfolgreich gelöscht");
			}
		}
		catch(NoSessionException e) {
			response.setReturnCode(500);
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
	 * Method to create or update an item
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double quantity,
			double price, String notice, int period, Timestamp launchDate,
			Timestamp finishDate, int basketId, int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}


	

	/*#################      XYZ - SECTION     ##############*/

	
	

	/**
	 * Method to find all loss of a time period
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public double getLossByPeriod(int sessionId, int daysOfPeriod) {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * Method to find all incomes of a time period
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public double getIncomeByPeriod(int sessionId, int daysOfPeriod) {
		// TODO Auto-generated method stub
		return 0;
	}


}
