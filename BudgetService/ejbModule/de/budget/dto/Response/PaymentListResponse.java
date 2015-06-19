package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.PaymentTO;

/**
 * Klasse für eine Liste von PaymentTO Objecten als Antwort auf Anfragen
 * @author Marco
 *
 */
public class PaymentListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<PaymentTO> paymentList;
			
		/**
		 * DefaultConstructor
		 */
		public PaymentListResponse() {

		}

		public List<PaymentTO> getPaymentList() {
			return paymentList;
		}

		public void setPaymentList(List<PaymentTO> paymentList) {
			this.paymentList = paymentList;
		}

}
