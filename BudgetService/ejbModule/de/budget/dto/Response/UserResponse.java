package de.budget.dto.Response;


import de.budget.dto.UserTO;

/**
 * Response Object für Useranfragen
 * @author Marco
 *
 */
public class UserResponse extends ReturnCodeResponse {


	private static final long serialVersionUID = 1L;

	private UserTO userTo;
	
	/**
	 * Default Constructor
	 */
	public UserResponse() {
		
	}
	
	public void setUserTo(UserTO userTo) {
		this.userTo = userTo;
	}
	
	public UserTO getUserTo () {
		return this.userTo;
	}
}
