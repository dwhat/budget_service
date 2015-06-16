/**
 * Package for Junit Test
 */
package de.budget.test;

//Import jUnit
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import de.budget.dto.Response.PaymentResponse;
import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.dto.Response.PaymentListResponse;
import de.budget.dto.PaymentTO;

/**
 * Test class for JunitTest for the service
 * @author Marco
 * 01.06.2015
 */
public class BudgetOnlineTest {
	
	private BudgetOnlineServiceBean service;
	private int sessionId;
	
	public BudgetOnlineTest() {
		
	}
	
	@Before
	public void startUp() {
		service = new BudgetOnlineServiceBean();
		sessionId = service.login("emma", "25d55ad283aa400af464c76d713c07ad").getSessionId();
	}
	
	@Test
	public void paymentTest(){
		//anlegen
		PaymentResponse resp = service.createOrUpdatePayment(sessionId, 0, "VB", "DE123456789", "BIC123", true);
		assertEquals(200, resp.getReturnCode());
		assertEquals("VB", resp.getPaymentTo().getName());
		assertEquals(true, resp.getPaymentTo().isActive());
		int paymentId = resp.getPaymentTo().getId();
		//bestimmtes Payment suchen
		resp = service.getPayment(sessionId, paymentId);
		assertEquals(paymentId, resp.getPaymentTo().getId());
		assertEquals("DE123456789", resp.getPaymentTo().getNumber());
		//Update
		resp = service.createOrUpdatePayment(sessionId, paymentId, "VB_Change", "DE123456789", "BIC123", false);
		assertEquals("VB_Change", resp.getPaymentTo().getName());
		assertEquals(false, resp.getPaymentTo().isActive());
		//Liste aller Payments laden
		PaymentListResponse payListResp = service.getPayments(sessionId);
		assertEquals(200, payListResp.getReturnCode());
		ArrayList<PaymentTO> payList = (ArrayList<PaymentTO>) payListResp.getPaymentList();
		int oldSize = payList.size();
		//ein Payment hinzufügen
		service.createOrUpdatePayment(sessionId, 0, "VB1", "DE12345678910", "BIC123456", true);
		PaymentListResponse payListResp1 = service.getPayments(sessionId);
		assertEquals(200, payListResp1.getReturnCode());
		ArrayList<PaymentTO> payList1 = (ArrayList<PaymentTO>) payListResp1.getPaymentList();
		assertEquals(oldSize+1, payList1.size());
		
	}
	
}
