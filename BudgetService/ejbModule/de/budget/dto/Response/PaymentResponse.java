package de.budget.dto.Response;

import de.budget.dto.PaymentTO;

/**
 * Class for the Payment response
 * @author Marco
 */
public class PaymentResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
	
	private PaymentTO paymentTo;
	
	/**
	 * Default Constructor
	 */
	public PaymentResponse() {
		
	}

	/**
	 * @return the paymentTo
	 */
	public PaymentTO getPaymentTo() {
		return paymentTo;
	}

	/**
	 * @param paymentTo the paymentTo to set
	 */
	public void setPaymentTo(PaymentTO paymentTo) {
		this.paymentTo = paymentTo;
	}

}
