package de.budget.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.budget.onlinebudget.BasketResponse;
import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.CategoryResponse;
import de.budget.onlinebudget.PaymentResponse;
import de.budget.onlinebudget.VendorResponse;

/**
 * Test Klasse zum Testen von Baskets
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasketActionTest {
	
	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private static int testCatId;
	private static int testPayId; 
	private static int testVenId;
	private long dateLong = System.currentTimeMillis();
	
	/**
	 * Baut einmalig Server Verbindung auf und legt eine Test Kategorie an
	 */
	@BeforeClass
	public static void initTestCase() {
		BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		remoteSystem = service.getBudgetOnlineServiceBeanPort();
		sessionId = remoteSystem.login("JunitTestUser", "25d55ad283aa400af464c76d713c07ad").getSessionId();
		
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, false, true, "UnitTestLossCategoryXYZ", "test", "FFFFFFF");
		assertEquals(200, catResp.getReturnCode());
		testCatId = catResp.getCategoryTo().getId();
		
		VendorResponse venResp = remoteSystem.createOrUpdateVendor(sessionId, 0, "ReweBasket", "BILD", "Straﬂe1", "Stadt1", 48691, 22);
		testVenId = venResp.getVendorTo().getId();
		
		PaymentResponse payResp = remoteSystem.createOrUpdatePayment(sessionId, 0, "VBBasket", "DE123456789", "BIC123", true);
		testPayId = payResp.getPaymentTo().getId();
	}
	
	@Test
	private void aTestCreateBasket() {
		BasketResponse baskResp = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp.getReturnCode());
		assertEquals("EinkaufHeute", baskResp.getBasketTo().getName());
		BasketResponse baskResp1 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute1", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp1.getReturnCode());
		BasketResponse baskResp2 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute2", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp2.getReturnCode());
		BasketResponse baskResp3 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute3", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp3.getReturnCode());
		BasketResponse baskResp4 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute4", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp4.getReturnCode());
	}
}
