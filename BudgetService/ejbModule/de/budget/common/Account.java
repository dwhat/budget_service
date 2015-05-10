package de.budget.common;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static int lastID = 0;
	
	private int id;
	private BigDecimal amount;
	private Customer owner;
	
	public Account(Customer owner) {
		this.id = ++lastID;
		this.amount = BigDecimal.ZERO;
		this.owner = owner;
		this.owner.addNewAccount(this);
	}
	
	public int getId() {
		return id;
	}
	
	public BigDecimal getBalance() {
		return amount;
	}
	
	public Customer getOwner() {
		return owner;
	}
	
	public void increase(BigDecimal amount) {
		this.amount = this.amount.add(amount);
	}
	
	public void decrease(BigDecimal amount) {
		this.amount = this.amount.subtract(amount);
	}
	
	public String toString() {
		return "Account " + this.id + " (Balance=" + this.amount + ", Owner=" + this.getOwner().getUserName() + ")";
	}
	
}