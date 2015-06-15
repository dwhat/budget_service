/**
 * Package for Exceptions
 */
package de.budget.Exception;

import org.jboss.logging.Logger;

/**
 * Mutterklasse für alle checked Exceptions
 * @author Marco
 * @author Moritz
 */
public class BudgetOnlineException extends Exception {
	
	private static final long serialVersionUID = -1658425297634781761L;
	
	private static final Logger logger = Logger.getLogger(BudgetOnlineException.class);
	
	private int errorCode;
	
	private String errorMessage;
	
	private String errorString;
	
	public BudgetOnlineException(int errorCode, String errorString, String additionalMessage) {
		super();
		this.errorCode = errorCode;
		this.errorString = errorString;
		switch(errorString){
		case "SESSION_NOT_FOUND_EXCEPTION":
			//this.errorCode = XYZ falls für android alles 404 sein muss. ErrorCode anpassung
			this.errorMessage = "Bitte loggen Sie sich zunächst ein.";
			logger.error("BudgetOnline | Message: " + errorMessage + " | ExtraInfo: " + additionalMessage);
			break;
		case "USER_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Ihre Anmedung ist fehl geschlagen";
			logger.error("");
			break;
		case "PASSWORD_INVALID_EXCEPTION":
			this.errorMessage = "Ihr Passwort ist ungültig. Mindestens 8 höchstens 250 Zeichen.";
			logger.error("");
			break;
		case "USER_EXISTS_EXCEPTION":
			this.errorMessage = "Username existiert bereits.";
			logger.error("");
			break;
		case "ILLEGAL_ARGUMENT_EXCEPTION":
			this.errorMessage = "Illegale Eingabe.";
			logger.error("");
			break;
		case "ENTITY_EXISTS_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		case "ITEM_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		case "INCOME_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		case "CATEGORY_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		case "VENDOR_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		case "PAYMENT_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		case "BASKET_NOT_FOUND_EXCEPTION":
			this.errorMessage = "Objekt existiert bereits.";
			logger.error("");
			break;
		default:
			this.errorMessage = additionalMessage;
			logger.error("");
			break;
		}
		
	}
/*
	private String getXMLContent(String errorString) {
		
	}
*/
	
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorString
	 */
	public String getErrorString() {
		return errorString;
	}

	/**
	 * @param errorString the errorString to set
	 */
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

}
