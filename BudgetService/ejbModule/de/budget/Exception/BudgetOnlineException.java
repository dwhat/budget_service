package de.budget.Exception;

import org.jboss.logging.Logger;



public class BudgetOnlineException extends Exception {
	
	private static final long serialVersionUID = -1658425297634781761L;
	
	private static final Logger logger = Logger.getLogger(BudgetOnlineException.class);
	
	private int errorCode;
	
	public BudgetOnlineException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		logger.error("Fehlerfall. Code=" + errorCode + "Message=" + message);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
