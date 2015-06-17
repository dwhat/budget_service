package de.budget.common;

import java.util.concurrent.Future;

/**
 * Asynchron Report Sender 
 * @author Marco
 *
 */
public interface ReportSender {
	
	/**
	 * Asynchron Report Sender
	 * 
	 * @author Marco
	 * @param subject
	 * @param body
	 * @return
	 */
	public Future<Boolean> send(String subject, String body);

}
