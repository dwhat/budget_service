package de.budget.onlinebudget;

import java.sql.Timestamp;
import java.util.List;









//Logger-Import
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;











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


/**
 * Stateless-Beanimplementierung von BudgetOnlineService 
 * @author Moritz
 * @author Marco
 *
 */
@Stateless
@Remote(BudgetOnlineService.class)
public class BudgetOnlineServiceBean implements BudgetOnlineService {

	private static final Logger logger = Logger.getLogger(BudgetOnlineServiceBean.class);
	
	/**
	 * EJB zur Abfrage von DatensÃ¤tzen
	 * Referenz auf die EJB wird per Dependency Injection gefÃ¼llt. 
	 */
	@EJB(beanName = "BudgetOnlineDAO", beanInterface = de.budget.dao.BudgetOnlineDAOLocal.class)
	private BudgetOnlineDAOLocal dao;
	
	@EJB
	private DtoAssembler dtoAssembler;
	
	/**
	 * EJB zur Beauftragung von Nachrichtenversand
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
		//try {
			dao.closeSession(sessionId);	
			logger.info("Logout erfolgreich. Session=" + sessionId);
			response.setReturnCode(200);
		//}
		//catch(BudgetOnlineException e) {
		//	response.setReturnCode(500);
		//	response.setMessage(e.getMessage());
		//}
		
		
		return response;
		
	}

	/**
	 * @author Marco
	 * @date 18.05.2015
	 * Method to register a new user
	 */
	@Override
	public UserLoginResponse setUser(String username, String password, String email) {
		UserLoginResponse response = new UserLoginResponse();
		try {
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
	 * @author Moritz
	 * @date 30.05.2015
	 */
	@Override
	public UserResponse getUserByName(int sessionId, int userName) {
		// 
		UserResponse UserResp = new UserResponse();	
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			//TODO
			//UserResp.setUserTo(dtoAssembler.makeDto(user));	
		}
		catch (BudgetOnlineException e) {
			UserResp.setReturnCode(e.getErrorCode());
			UserResp.setMessage(e.getMessage());
		}
		return UserResp;
		
	}


	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteUser(int sessionId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	/*#################      BASKET - SECTION     ##############*/
	


	/**
	 * @author Marco
	 * @date 28.05.2015
	 */
	@Override
	public BasketListResponse getLastBaskets(int sessionId, int numberOfBaskets) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param vendorId
	 * @return a list with all baskets of a vendor
	 */
	@Override
	public BasketListResponse getBasketsByVendor(int sessionId, int vendorId){
		return null;
	}
	/**
	 * gets all baskets of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
	 */
	@Override
	public BasketListResponse getBasketsByMonth(int sessionId) {
		return null;
	}
	

	
	/**
	 * gets all baskets of a specific payment
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param paymentId
	 * @return
	 */
	@Override
	public BasketListResponse getBasketsByPayment(int sessionId, int paymentId) {
		return null;
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
			User user = this.dao.findUserByName(session.getUsername());
			Basket basket = user.getBasket(basketId);
			response.setBasketTo(dtoAssembler.makeDto(basket));	
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
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
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteBasket(int sessionId, int basketID) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/**
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
		catch (BudgetOnlineException e) {
			basketResp.setReturnCode(404);
			basketResp.setMessage("Couldn't create Basket.");
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
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
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
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	

	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteVendor(int sessionId, int vendorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
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
		catch (BudgetOnlineException e) {
			vendorResp.setReturnCode(404);
			vendorResp.setMessage("Couldn't create Vendor.");
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
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			//Zur Übersichtlichkeit noch die lange Form gelassen
			Payment payment = user.getPayment(paymentId);
			response.setPaymentTo(dtoAssembler.makeDto(payment));
		}
		catch(BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
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
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
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
		PaymentResponse paymentResp = getPayment(sessionId, paymentId);
		ReturnCodeResponse response = new ReturnCodeResponse();
		
		if(paymentResp.getReturnCode() == 0) {
			dao.deletePayment(paymentResp.getPaymentTo().getId());
			logger.info("Payment erfolgreich gelöscht");
		}
		else {
			response.setReturnCode(404);
			response.setMessage("Payment to delete not found.");
		}
		return response;	
	}
	
	
	
	/**
	 * method to create or update a payment
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
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
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
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteCategory(int sessionId, int categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
	


	/**
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
		catch (BudgetOnlineException e) {
			categoryResp.setReturnCode(404);
			categoryResp.setMessage("Couldn't create a Category.");
		}
		return categoryResp;
	}


	


	/*#################      INCOME - SECTION     ##############*/


	


	/**
	 * @author Moritz
	 * @date 30.05.2015
	 */
	@Override
	public IncomeResponse getIncome(int sessionId, int itemId) {
		IncomeResponse response = new IncomeResponse();
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			Income income = user.getIncome(itemId);
			response.setIncomeTo(dtoAssembler.makeDto(income));	
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * gets all Incomes of a specific category for incomes
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param categoryId
	 * @return
	 */
	@Override
	public IncomeListResponse getIncomesByCategory(int sessionId, int categoryId) {
		return null;
	}


	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public IncomeListResponse getIncomesByBasket(int sessionId, int basketId) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/**
	 * @author Marco
	 * @date 28.05.2015
	 */
	@Override
	public IncomeListResponse getLastIncomes(int sessionId, int numberOfIncome) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * gets all income of the actual month
	 * @author Marco
	 * @param sessionId
	 * @return
	 */
	@Override
	public IncomeListResponse getIncomesByMonth(int sessionId) {
		return null;
	}
	


	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteIncome(int sessionId, int itemID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
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
	 * @author Marco
	 * @date 26.05.2015
	 */
	// Eventuell auch noch basketID als Parameter 
	@Override
	public ItemResponse getItemByBasket(int sessionId, int itemId, int basketId) {
		ItemResponse response = new ItemResponse();
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			Basket basket = user.getBasket(basketId);
			Item item = basket.getItem(itemId);
			response.setItemTo(dtoAssembler.makeDto(item));	
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}


	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ItemListResponse getItemsByBasket(int sessionId, int basketId) {
		ItemListResponse response = new ItemListResponse();
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			Basket basket = user.getBasket(basketId);
			List<Item> itemList = basket.getItems();
			response.setItemList(dtoAssembler.makeItemListDto(itemList));
		}
		catch (BudgetOnlineException e) {
			response.setReturnCode(e.getErrorCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * gets all Items of a specific category for losses
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param categoryId
	 * @return
	 */
	@Override
	public ItemListResponse getItemsByLossCategory(int sessionId, int categoryId) {
		return null;
	}
	
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public ReturnCodeResponse deleteItem(int sessionId, int itemId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
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
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public double getLossByPeriod(int sessionId, int daysOfPeriod) {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * @author Marco
	 * @date 26.05.2015
	 */
	@Override
	public double getIncomeByPeriod(int sessionId, int daysOfPeriod) {
		// TODO Auto-generated method stub
		return 0;
	}


}
