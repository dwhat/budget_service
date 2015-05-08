package de.budget.common;


/*
 * Interface BudgetOnlineService
 * @author Marco
 * @date 08.05.2015
 * @version Beta 1
 */
public interface BudgetOnlineService {

		
	/**
	* Method to login with Username and Password
	* @param username
	* @param password
	* @return
	* @author Marco
	* @date 08.05.2015
	*/
	public boolean login(String username, String password);
		
	
	/**
	* Method to log out
	* @throws NoSessionException
	* @author Marco
	* @date 08.05.2015
	*/
	public void logout();
		

}
