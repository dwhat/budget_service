/**
 * 
 */
package de.budget.onlinebudget;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;


/**
 * Class to check how much user are actually logged in
 * @author Marco
 * @date 09.06.2015
 */
@Startup
@Singleton
public class Payload {
	
	private static final Logger logger = Logger.getLogger(Payload.class);
	
	private int payload;
	
	/**
	 * Method to set the payload to 0 after the server start
	 * @author Marco
	 * @date 09.06.2015
	 */
	@PostConstruct
	private void init() {
		this.payload = 0;
	}

	/**
	 * @return the payload
	 */
	public int getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(int payload) {
		this.payload = payload;
	}

}
