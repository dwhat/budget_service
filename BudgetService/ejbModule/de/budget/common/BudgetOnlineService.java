package de.budget.common;


import java.util.Map;

//Response-Imports
import de.budget.dto.ReturnCodeResponse;
import de.budget.dto.UserLoginResponse;



/*
 * Interface BudgetOnlineService
 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
 */
public interface BudgetOnlineService {

		
	/* Session - SECTION */
	
	/**
	* Method to login with Username and Password
	* @param username
	* @param password
	* @return
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

	//public User getUser(String username);
	

	//public int createOrUpdateUser(User user);
	
	//public int deleteUser(String username);
	
	/*########################################################*/
		
	/* Vendor - SECTION */
	
	
	//public Vendor getVendorByName(String name);
	
	
	//public int createOrUpdateVendor(Customer vendor);
	
	
	
	//public int deleteVendor(int vendorID);
	
	/*########################################################*/
	
	/* Payment - SECTION */
	
/*	
	public Customer getPayment(int paymentID);
	
	
	public int createPayment(Customer payment);
	
	//F‰llt evtl weg da gleich wie create 
	public int updatePayment(Customer payment);
	
	
	public int deletePayment(int paymentID);
*/	
	/*########################################################*/
	
	/* Category - SECTION */
	
/*	
	public Customer getCategory(int categoryID);
	
	public int createCategory(Customer category);
	
	//F‰llt evtl weg da gleich wie create 
	public int updateCategory(Customer category);
	
	
	public int deleteCategory(int categoryID);
	
	/*########################################################*/
	
	/* Basket - SECTION */
	
/*	
	public Customer getBasket(int basketID);
	
	
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
