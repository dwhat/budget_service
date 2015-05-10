package de.budget.session;

import de.budget.common.Customer;

public class UserSession {

private static int lastID = 0;
	
	private final String sessionID;
	private final Customer user;
	
	public UserSession(Customer user) {
		lastID++;
		this.sessionID = "" + lastID;
		this.user = user;
		SessionRegistry.getInstance().addSession(this);
	}

	public String getSessionID() {
		return sessionID;
	}

	public Customer getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return "["+this.sessionID+","+this.getUser().getUserName()+"]";
	}
}
