package de.budget.common;

// Um Asynchron laufen zu lassen 
import java.util.concurrent.Future;

import de.budget.entities.User;

/**
 * 
 * 
 * 
 * @date 09.06.2015
 * @author Moritz
 *
 */
public interface MessageSender {
	
	/**
	 * Asynchron Message Sender Function
	 * 
	 * @author Moritz
	 * @param subject
	 * @param body
	 * @param user
	 * @return
	 */
	public Future<Boolean> send(String subject, String body, User user);

}
