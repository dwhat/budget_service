package de.budget.dto;

import java.util.List;

/**
 * @date 19.05.2015
 * @author Marco
 *Klasse für eine Liste von PaymentTO Objecten als Antwort auf Anfragen
 */
public class PaymentListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<PaymentTO> paymentList;
			
		/**
		 * DefaultConstructor
		 * @author Marco
		 * @date 19.05.2015
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
