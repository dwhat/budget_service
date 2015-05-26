package de.budget.common;



//Response-Imports
import de.budget.dto.Response.BasketListResponse;
import de.budget.dto.Response.BasketResponse;
import de.budget.dto.Response.CategoryListResponse;
import de.budget.dto.Response.CategoryResponse;
import de.budget.dto.Response.PaymentListResponse;
import de.budget.dto.Response.PaymentResponse;
import de.budget.dto.Response.ReturnCodeResponse;
import de.budget.dto.Response.UserLoginResponse;
import de.budget.dto.Response.UserResponse;
import de.budget.dto.Response.VendorListResponse;
import de.budget.dto.Response.VendorResponse;



/*
 * Interface BudgetOnlineService
 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
 */
public interface BudgetOnlineService {

		
	/* Session - SECTION */
	
	/**
	 * Method to Register a new User
	 * @param username
	 * @param password
	 * @param email
	 * @return UserLoginResponse
	 * @date 18.05.2015
	 * @author Marco
	 */
	public UserLoginResponse registerNewUser(String username, String password, String email);
	
	
	/**
	* Method to login with Username and Password
	* @param username
	* @param password
	* @return UserLoginResponse
	* @author Marco
	* @date 08.05.2015
	*/
	public UserLoginResponse login(String username, String password);
		
	
	/**
	* Method to log out
	* @throws NoSessionException
	* @author Marco
	* @date 08.05.2015
	*/
	public ReturnCodeResponse logout(int sessionID);
	
	//Kommentare der Funktionen erstmal auﬂen vorgelassen da sonst nur doppelte Arbeit. Unbedingte absprache der Update/Create Methoden notwendig
	
	
	/*########################################################*/
	
	/* Customer - SECTION */

	/**
	 * Method to get a User by name
	 * @author Marco
	 * @param sessionId
	 * @param userName
	 * @return
	 */
	public UserResponse getUserByName(int sessionId, int userName);
	

	//public int createOrUpdateUser(User user);
	
	public ReturnCodeResponse deleteUser(int sessionId, String username);
	
	/*########################################################*/
		
	/* Vendor - SECTION */
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return VendorListResponse Object
	 */
	public VendorListResponse getMyVendors(int sessionId);

	/**
	 * Method to get a Vendor with the SessionId and the vendorId
	 * @author Marco
	 * @date 18.05.2015
	 * @param sessionId
	 * @param vendorId
	 * @return VendorResponse Object
	 */
	public VendorResponse getVendor(int sessionId, int vendorId);
	
	/**
	 * method to create a vendor
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param name
	 * @param logo (base64 String)
	 * @return
	 */
	public VendorResponse createVendor(int sessionId, String name, String logo);

	/**
	 * Method to delete a vendor
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param vendorId
	 * @return
	 */
	public ReturnCodeResponse deleteVendor(int sessionId, int vendorId);
	
	/*########################################################*/
	
	/* Payment - SECTION */
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return PaymentListResponse Object
	 */
	public PaymentListResponse getMyPayments(int sessionId);
	
	/**
	 * Method to get a payment with the SessionId and the paymentId
	 * @author Marco
	 * @date 18.05.2015
	 * @param sessionId
	 * @param paymentId
	 * @return PaymentResponse Object
	 */
	public PaymentResponse getPayment(int sessionId, int paymentId);
	
	/**
	 * Method to delete a payment
	 * @author Marco
	 * @param sessionId
	 * @param paymentId
	 * @return ReturnCodeResponse Object
	 */
	public ReturnCodeResponse deletePayment(int sessionId, int paymentId);
	

	public PaymentResponse createPayment(int sessionId, String name, String number, String bic);

	/*########################################################*/
	
	/* Category - SECTION */
	
	/**
	 * @author Marco
	 * @param sessionId
	 * @param categoryId
	 * @return
	 */
	public CategoryResponse getCategory(int sessionId, int categoryId);

	
	
	/**
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return CategoryListResponse Object
	 */
	public CategoryListResponse getMyCategorys(int sessionId);

	
	

	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param income
	 * @param name
	 * @param notice
	 * @return
	 */
	public CategoryResponse createCategory(int sessionId, boolean income, String name, String notice);

	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param categoryId
	 * @return
	 */
	public ReturnCodeResponse deleteCategory(int sessionId, int categoryId);
	
	/*########################################################*/
	
	/* Basket - SECTION */
	
	/**
	 * Gives a Response Object with all Baskets in a list
	 * @author Marco
	 * @date 19.05.2015
	 * @param sessionId
	 * @return BasketListResponse Object
	 */
	public BasketListResponse getMyBaskets(int sessionId);
	
	/**
	 * @author Marco
	 * @date 18.05.2015
	 * @param sessionId
	 * @param basketID
	 * @return Basket Object
	 */
	public BasketResponse getBasket(int sessionId, int basketID);
	
	/*
	public int createBasket(Customer basket);
	
	//F‰llt evtl weg da gleich wie create 
	public int updateBasket(Customer basket);
	
	
	public int deleteBasket(int basketID);
	
	/*########################################################*/
	
	/* Charts/Balance - SECTION */
/*	
	public Map<Integer,Integer> getChart(int customerID);
	
	public int getBalance(int customerID);
	
	/*########################################################*/
	
	/* Incomes - SECTION */
/*	
	public int createIncome(Map<Integer,Customer> incomes);
	
	public int updateIncome(Customer income,int incomeID);
	
	public int deleteIncome(int incomeID);
	
	/*########################################################*/
	
	/* Losses - SECTION */
/*	
	public int createLoss(Map<Integer,Customer> losses);
	
	public int updateLoss(Customer loss,int lossID);
	
	public int deleteLoss(int lossID);
	
*/	
	

}
