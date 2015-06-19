/**
 * Package for Unit Tests
 */
package de.budget.test;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.PaymentListResponse;
import de.budget.onlinebudget.PaymentResponse;
import de.budget.onlinebudget.ReturnCodeResponse;

/**
 * Test Klasse zum Testen von Payments
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentActionTest {

	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private int testPayId;
	
	/**
	 * Baut einmalig Server Verbindung auf
	 */
	@BeforeClass
	public static void initTestCase() {
		BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		remoteSystem = service.getBudgetOnlineServiceBeanPort();
		sessionId = remoteSystem.login("JunitTestUser", "25d55ad283aa400af464c76d713c07ad").getSessionId();
	}
	
	/**
	 * Testet ob ein Payment angelegt werden kann. Erwartet den OK-Code 200 und den Namen der zuvor angelegten Payment
	 */
	@Test
	public void aTestCreatePayment() {
		PaymentResponse resp = remoteSystem.createOrUpdatePayment(sessionId, 0, "VB", "DE123456789", "BIC123", true);
		assertEquals(200, resp.getReturnCode());
		assertEquals("VB", resp.getPaymentTo().getName());
		assertEquals(true, resp.getPaymentTo().isActive());
		
		PaymentResponse resp1 = remoteSystem.createOrUpdatePayment(sessionId, 0, "VB123", "DE1234567", "BIC123234", true);
		assertEquals(200, resp1.getReturnCode());
		assertEquals("VB", resp1.getPaymentTo().getName());
		assertEquals(true, resp1.getPaymentTo().isActive());
		testPayId = resp1.getPaymentTo().getId();
		
		PaymentResponse resp2 = remoteSystem.createOrUpdatePayment(sessionId, 0, "VB123", "DE1234567", "BIC123234", false);
		assertEquals(200, resp2.getReturnCode());
		assertEquals("VB", resp2.getPaymentTo().getName());
		assertEquals(true, resp2.getPaymentTo().isActive());
		testPayId = resp2.getPaymentTo().getId();
		assertTrue(resp2.getPaymentTo().isActive()); //Da neue Payments immer als aktive angelegt werden
	}
	
	/**
	 * Testet ob eine Payment mit einer bestimmten Id gefunden werden kann
	 */
	@Test
	public void cTestGetOnePayment() {
		PaymentResponse payResp = remoteSystem.getPayment(sessionId, testPayId);
		assertEquals(200, payResp.getReturnCode());
		assertEquals(testPayId, payResp.getPaymentTo().getId());
	}
	
	/**
	 * Testet ob ein Payment geupdatet werden kann
	 */
	@Test
	public void dTestUpdatePayment() {
		PaymentResponse resp = remoteSystem.createOrUpdatePayment(sessionId, testPayId, "geändert", "DE123456789", "BIC123", true);
		assertEquals(200, resp.getReturnCode());
		assertEquals("geändert", resp.getPaymentTo().getName());
	}
	
	/**
	 * Testet ob eine Payment mit einer unbekannten Id nicht gefunden wird. Erwaretet Fehlerfall
	 */
	@Test
	public void dTestGetOnePaymentError() {
		PaymentResponse payResp = remoteSystem.getPayment(sessionId, 123456789);
		assertEquals(404, payResp.getReturnCode());
	}
		
	/**
	 * Testet ob alle Payments als Liste zurück gegeben werden können
	 */
	@Test
	public void eTestGetAllPayments() {
		PaymentListResponse payListResp = remoteSystem.getPayments(sessionId);
		assertEquals(200, payListResp.getReturnCode());
		assertEquals(3, payListResp.getPaymentList().size()); // Es wurden zuvor 3 Payments angelegt
	}
	
	/**
	 * Testet ob ein Payment gelöscht werden kann
	 */
	@Test
	public void fTestDeletePayment() {
		ReturnCodeResponse resp = remoteSystem.deletePayment(sessionId,  testPayId);
		assertEquals(200, resp.getReturnCode());
		PaymentResponse payResp = remoteSystem.getPayment(sessionId, testPayId);
		assertEquals(404, payResp.getReturnCode());
	}
	
	/**
	 * Testet ob ein Payment gelöscht werden kann, obwohl falsche Id übergeben wird, erwartet Fehlerfall
	 */
	@Test
	public void gTestDeletePaymentError() {
		ReturnCodeResponse resp = remoteSystem.deletePayment(sessionId,  123456789);
		assertNotEquals(200, resp.getReturnCode());
	}
}
