package de.budget.common;



//Response-Imports
import java.sql.Timestamp;
import java.util.List;

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
import de.budget.entities.Basket;
import de.budget.entities.Category;
import de.budget.entities.Item;



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
	
	//Kommentare der Funktionen erstmal außen vorgelassen da sonst nur doppelte Arbeit. Unbedingte absprache der Update/Create Methoden notwendig
	
	
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
	
	/**
	 * @author Marco
	 * @param sessionId
	 * @param username
	 * @return
	 */
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
	 * @param vendorId only necessary for update 
	 * @param name
	 * @param logo (base64 String)
	 * @return
	 */
	public VendorResponse createOrUpdateVendor(int sessionId, int vendorId, String name, String logo);

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
	

	/**
	 * method to create or update a payment
	 * @author Marco
	 * @author Moritz
	 * @param sessionId
	 * @param paymentId
	 * @param name
	 * @param number
	 * @param bic
	 * @param active
	 * @return PaymentResponse
	 */
	public PaymentResponse createOrUpdatePayment(int sessionId, int paymentId, String name, String number, String bic, boolean active);

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
	 * @param categoryId only necessary for update
	 * @param income
	 * @param active
	 * @param name
	 * @param notice
	 * @return
	 */
	public CategoryResponse createOrUpdateCategory(int sessionId, int categoryId, boolean income, boolean active, String name, String notice);

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
	
	/**
	 * Method to create a basket
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param basketId
	 * @param notice
	 * @param amount
	 * @param purchaseDate
	 * @param paymentId
	 * @param vendorId
	 * @param items   List with items to add to the basket
	 * @return
	 */
	public BasketResponse createOrUpdateBasket(int sessionId, int basketId, String notice, double amount, Timestamp purchaseDate, int paymentId, int vendorId, List<Item> items);
	
	/**
	 * Method to delete a basket
	 * @author marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param basketID
	 * @return
	 */
	public ReturnCodeResponse deleteBasket(int sessionId, int basketID);
	

	/* Incomes - SECTION */
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param incomeId    only necessary for update
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param period
	 * @param launchDate
	 * @param finishDate
	 * @param basket
	 * @param category
	 * @return
	 */
	public IncomeResponse createOrUpdateIncome(int sessionId, int incomeId, String name, double  quantity, double price, String notice, int period, Timestamp launchDate, Timestamp finishDate, int basketId, int categoryId);
	
	//public int updateIncome(Customer income,int incomeID);
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param itemId
	 * @return
	 */
	public IncomeResponse getIncome(int sessionId, int itemId);
	
	/**
	 * method to get all incomes of a basket
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param basketId
	 * @return
	 */
	public IncomeListResponse getListOfIncomesOfBasket(int sessionId, int basketId);
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param itemID
	 * @return
	 */
	public ReturnCodeResponse deleteIncome(int sessionId, int itemID);
	
	/*########################################################*/
	
	/* Items - SECTION */

	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param itemId
	 * @param name
	 * @param quantity
	 * @param price
	 * @param notice
	 * @param period
	 * @param launchDate
	 * @param finishDate
	 * @param basketId
	 * @param categoryId
	 * @return
	 */
	public ItemResponse createOrUpdateItem(int sessionId, int itemId, String name, double  quantity, double price, String notice, int period, Timestamp launchDate, Timestamp finishDate, int basketId, int categoryId);
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param lossId
	 * @return
	 */
	public ReturnCodeResponse deleteItem(int sessionId, int lossId);
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param itemId
	 * @return
	 */
	public ItemResponse getItem(int sessionId, int itemId);
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param basketId
	 * @return
	 */
	public ItemListResponse getListOfItemsOfBasket(int sessionId, int basketId);

	/*########################################################*/
	
	/* Charts/Balance - SECTION */
	
	//public Map<Integer,Integer> getChart(int customerID);
	
	//public int getBalance(int customerID);
	
	/*########################################################*/
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param daysOfPeriod
	 * @return
	 */
	public double getLossesOfPeriod(int sessionId, int daysOfPeriod);
	
	/**
	 * @author Marco
	 * @date 26.05.2015
	 * @param sessionId
	 * @param daysOfPeriod
	 * @return
	 */
	public double getIncomesOfPeriod(int sessionId, int daysOfPeriod);

	/**
	 * Gibt die letzten Baskets als Liste zurück
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param numberOfBaskets Anzahl der letzten auszugebenen Baskets
	 * @return BasketListResponse Object
	 */
	public BasketListResponse getLastBaskets(int sessionId, int numberOfBaskets);
	
	/**
	 * Gibt die letzten Incomes als Liste zurück
	 * @author Marco
	 * @date 29.05.2015
	 * @param sessionId
	 * @param numberOfIncome
	 * @return IncomeListResponse
	 */
	public IncomeListResponse getLastIncome(int sessionId, int numberOfIncome);
}
