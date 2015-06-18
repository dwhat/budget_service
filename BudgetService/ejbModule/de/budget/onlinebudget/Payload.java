/**
 * 
 */
package de.budget.onlinebudget;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
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
		logger.info("PAYLOAD| Server gestartet. Payload = " + payload);
	}

	/**
	 * @return the payload
	 */
	@Lock(LockType.READ)
	public int getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	@Lock(LockType.WRITE)
	public void setPayload(int payload) {
		this.payload = payload;
		logger.info("PAYLOAD| Es sind aktuell " + this.payload + " Benutzer angemeldet");
	}

}
