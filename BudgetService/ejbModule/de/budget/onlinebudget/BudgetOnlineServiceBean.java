package de.budget.onlinebudget;

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
import de.budget.dto.BasketTO;
import de.budget.dto.PaymentTO;
//Response-Import @author Moritz
import de.budget.dto.VendorTO;
import de.budget.dto.Response.BasketListResponse;
import de.budget.dto.Response.BasketResponse;
import de.budget.dto.Response.CategoryListResponse;
import de.budget.dto.Response.PaymentListResponse;
import de.budget.dto.Response.PaymentResponse;
import de.budget.dto.Response.ReturnCodeResponse;
import de.budget.dto.Response.UserLoginResponse;
import de.budget.dto.Response.VendorListResponse;
import de.budget.dto.Response.VendorResponse;
//Exception-Import
import de.budget.Exception.BudgetOnlineException;
import de.budget.Exception.InvalidLoginException;


import de.budget.Exception.NoSessionException;
import de.budget.Exception.UsernameAlreadyExistsException;
import de.budget.entities.Basket;
import de.budget.entities.BudgetSession;
import de.budget.entities.Category;
import de.budget.entities.Payment;
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
			}
			else 
			{
				logger.info("Login fehlgeschlagen, da Kunde unbekannt oder Passwort falsch. username=" + username);
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
		logger.info("Logout erfolgreich. Session=" + sessionId);
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
			logger.info("Versuche neuen User anzulegen. Name=" + username);
			User user = dao.createUser(username, password, email);
			if (user != null) {
				int sessionId = dao.createSession(user);
				response.setSessionId(sessionId);
				logger.info("User angelegt. Session=" + sessionId);
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
			response.setVendorList(dtoAssembler.makeVendorListDto(vendorList));	
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
	public CategoryListResponse getMyCategorys(int sessionId) {
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
	 * Gives a Response Object with all Payments in a list
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return CategoryListResponse Object
	 */
	@Override
	public PaymentListResponse getMyPayments(int sessionId) {
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
	 * Gives a Response Object with all Baskets in a list
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return BasketListResponse Object
	 */
	@Override
	public BasketListResponse getMyBaskets(int sessionId) {
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
	 * Method to get a Basket with the SessionId and the basketId
	 * @author Marco
	 * @date 18.05.2015
	 * @param sessionId
	 * @param basketId
	 * @return BasketResponse Object
	 */
	public BasketResponse getBasket(int sessionId, int basketId) {
		BasketResponse response = new BasketResponse();

		BasketListResponse basketListResponse = getMyBaskets(sessionId);
		if (basketListResponse.getReturnCode() == 0) {
			for (BasketTO b : basketListResponse.getBasketList()) {
				if (b.getId() == basketId) {
					response.setBasketTo(b);
				}
			}
		}
		return response;
	}
	
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

		VendorListResponse vendorListResponse = getMyVendors(sessionId);
		if (vendorListResponse.getReturnCode() == 0) {
			for (VendorTO v : vendorListResponse.getVendorList()) {
				if (v.getId() == vendorId) {
					response.setVendorTo(v);
				}
			}
		}
		return response;
	}
	
	/**
	 * Method to get a payment with the SessionId and the paymentId
	 * @author Marco
	 * @date 18.05.2015
	 * @param sessionId
	 * @param paymentId
	 * @return PaymentResponse Object
	 */
	@Override
	public PaymentResponse getPayment(int sessionId, int paymentId) {
		PaymentResponse response = new PaymentResponse();

		PaymentListResponse paymentListResponse = getMyPayments(sessionId);
		if (paymentListResponse.getReturnCode() == 0) {
			for (PaymentTO p : paymentListResponse.getPaymentList()) {
				if (p.getId() == paymentId) {
					response.setPaymentTo(p);
				}
			}
		}
		return response;
	}
	
	/**
	 * @author Marco
	 * @param sessionId
	 * @param paymentName
	 * @return PaymentResponseObject
	 */
	private PaymentResponse getPaymentByName(int sessionId, String paymentName) {
		PaymentResponse response = new PaymentResponse();

		PaymentListResponse paymentListResponse = getMyPayments(sessionId);
		if (paymentListResponse.getReturnCode() == 0) {
			for (PaymentTO p : paymentListResponse.getPaymentList()) {
				if (p.getName().equals(paymentName)) {
					response.setPaymentTo(p);
				}
			}
		}
		else {
			response = null;
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
	 * Method to create a new Payment
	 * @author Marco
	 * @date 20.05.2015
	 * @param sessionId
	 * @param name
	 * @param number
	 * @param bic
	 * @return Payment Object
	 */
	@Override
	public Payment createPayment(int sessionId, String name, String number, String bic) {
		Payment payment = null;
		try {
			BudgetSession session = getSession(sessionId);
			User user = this.dao.findUserByName(session.getUsername());
			payment = dao.createPayment(user, name, number, bic);
			return payment;
		}
		catch (BudgetOnlineException e) {

		}
		return null;
	}
}
