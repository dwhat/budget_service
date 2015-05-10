package de.budget.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private HashMap<Integer,Account> accounts;
	
	public Customer(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.accounts = new HashMap<>();
	}
	
	public void addNewAccount(Account newAccount) {
		this.accounts.put(newAccount.getId(), newAccount);
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Account getAccountById(int kontoNr) {
		return accounts.get(kontoNr);
	}

	public Set<Account> getAccounts() {
		return new HashSet<Account>(accounts.values());
	}
	
}