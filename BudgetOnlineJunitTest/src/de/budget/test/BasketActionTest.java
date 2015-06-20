package de.budget.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.budget.onlinebudget.BasketListResponse;
import de.budget.onlinebudget.BasketResponse;
import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.CategoryResponse;
import de.budget.onlinebudget.ItemResponse;
import de.budget.onlinebudget.PaymentListResponse;
import de.budget.onlinebudget.PaymentResponse;
import de.budget.onlinebudget.ReturnCodeResponse;
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
	private static int baskId1;
	private static int baskId2;
	private static int baskId3;
	private static int baskId4;
	private long dateLong = System.currentTimeMillis();
	
	/**
	 * Baut einmalig Server Verbindung auf und legt eine Test Kategorie an
	 */
	@BeforeClass
	public static void initTestCase() {
		BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		remoteSystem = service.getBudgetOnlineServiceBeanPort();
		sessionId = remoteSystem.login("JunitTestUser", "25d55ad283aa400af464c76d713c07ad").getSessionId();
		
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, false, true, "UnitTestLoss12CategoryXYZ", "test", "FFFFFFF");
		assertEquals(200, catResp.getReturnCode());
		testCatId = catResp.getCategoryTo().getId();
		
		VendorResponse venResp = remoteSystem.createOrUpdateVendor(sessionId, 0, "ReweBasket123", "BILD", "Straße1", "Stadt1", 48691, 22);
		testVenId = venResp.getVendorTo().getId();
		
		PaymentResponse payResp = remoteSystem.createOrUpdatePayment(sessionId, 0, "VBBasket", "DE123456789", "BIC123", true);
		testPayId = payResp.getPaymentTo().getId();
	}
	
	/**
	 * Testet das anlegen eines Baskets
	 */
	@Test
	public void aTestCreateBasket() {
		BasketResponse baskResp = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp.getReturnCode());
		assertEquals("EinkaufHeute", baskResp.getBasketTo().getName());
		baskId1 = baskResp.getBasketTo().getId();
		BasketResponse baskResp1 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute1", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp1.getReturnCode());
		baskId2 = baskResp1.getBasketTo().getId();
		BasketResponse baskResp2 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute2", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp2.getReturnCode());
		baskId3 = baskResp2.getBasketTo().getId();
		BasketResponse baskResp3 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute3", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp3.getReturnCode());
		baskId4 = baskResp3.getBasketTo().getId();
		BasketResponse baskResp4 = remoteSystem.createOrUpdateBasket(sessionId, 0, "EinkaufHeute4", "Heute", 2.25, dateLong, testPayId, testVenId);
		assertEquals(200, baskResp4.getReturnCode());
	}
	
	/**
	 * Testet das Anlegen der Items im Basket
	 */
	@Test
	public void bTestCreateItem() {
		ItemResponse resp = remoteSystem.createOrUpdateItem(sessionId, 0, "Shampoo", 2.00, 2.99, "Für die Haare", dateLong, baskId1, testCatId);
		assertEquals(200, resp.getReturnCode());
		ItemResponse resp1 = remoteSystem.createOrUpdateItem(sessionId, 0, "Shampoo2", 6.00, 2.99, "Für die Haare", dateLong, baskId1, testCatId);
		assertEquals(200, resp1.getReturnCode());
		ItemResponse resp2 = remoteSystem.createOrUpdateItem(sessionId, 0, "Shampoo3", 8.00, 2.99, "Für die Haare", dateLong, baskId1, testCatId);
		assertEquals(200, resp2.getReturnCode());
		ItemResponse resp3 = remoteSystem.createOrUpdateItem(sessionId, 0, "Shampoo45", 1.00, 2.99, "Für die Haare", dateLong, baskId1, testCatId);
		assertEquals(200, resp3.getReturnCode());
		ItemResponse resp4 = remoteSystem.createOrUpdateItem(sessionId, 0, "Shampoo5", 1.00, 2.99, "Für die Haare", dateLong, baskId1, testCatId);
		assertEquals(200, resp4.getReturnCode());
		
		ItemResponse resp5 = remoteSystem.createOrUpdateItem(sessionId, 0, "Brot", 1.00, 1.99, "Einkauf", dateLong, baskId2, testCatId);
		assertEquals(200, resp5.getReturnCode());
		ItemResponse resp6 = remoteSystem.createOrUpdateItem(sessionId, 0, "Käse", 1.00, 1.99, "Einkauf", dateLong, baskId2, testCatId);
		assertEquals(200, resp6.getReturnCode());
		ItemResponse resp7 = remoteSystem.createOrUpdateItem(sessionId, 0, "Milch", 1.00, 1.99, "Einkauf", dateLong, baskId2, testCatId);
		assertEquals(200, resp7.getReturnCode());
		
		ItemResponse resp8 = remoteSystem.createOrUpdateItem(sessionId, 0, "Diesel", 1.00, 1.99, "Tanken", dateLong, baskId3, testCatId);
		assertEquals(200, resp8.getReturnCode());	
	}
	
	/**
	 * Testet ob ein Basket mit einer bestimmten Id gefunden werden kann
	 */
	@Test
	public void cTestGetOneBasket() {
		BasketResponse baskResp = remoteSystem.getBasket(sessionId, baskId1);
		assertEquals(200, baskResp.getReturnCode());
		assertEquals(baskId1, baskResp.getBasketTo().getId());
		assertEquals(testPayId, baskResp.getBasketTo().getPayment().getId());
		assertEquals(5, baskResp.getBasketTo().getItems().size());
	}
	
	/**
	 * Testet ob ein Basket mit einer unbekannten Id nicht gefunden wird. Erwaretet Fehlerfall
	 */
	@Test
	public void dTestGetOneBasketError() {
		BasketResponse baskResp = remoteSystem.getBasket(sessionId, 123456789);
		assertEquals(404, baskResp.getReturnCode());
	}
		
	/**
	 * Testet ob alle Baskets als Liste zurück gegeben werden können
	 */
	@Test
	public void eTestGetAllBaskets() {
		BasketListResponse baskListResp = remoteSystem.getBaskets(sessionId);
		assertEquals(200, baskListResp.getReturnCode());
		assertEquals(5, baskListResp.getBasketList().size()); // Es wurden zuvor 5 Baskets angelegt
	}
	
	/**
	 * Testet ob ein Basket geupdatet werden kann
	 */
	@Test
	public void fTestUpdateBasket() {
		BasketResponse resp = remoteSystem.createOrUpdateBasket(sessionId, 0, "Geändert", "Gestern", 2.30, dateLong, testPayId, testVenId);
		assertEquals(200, resp.getReturnCode());
		assertEquals("Geändert", resp.getBasketTo().getName());
		assertEquals("Gestern", resp.getBasketTo().getNotice());
	}
	
	/**
	 * Testet ob ein Basket gelöscht werden kann
	 */
	@Test
	public void gTestDeleteBasket() {
		ReturnCodeResponse resp = remoteSystem.deleteBasket(sessionId,  baskId3);
		assertEquals(200, resp.getReturnCode());
		BasketResponse baskResp = remoteSystem.getBasket(sessionId, baskId3);
		assertEquals(404, baskResp.getReturnCode());
	}
	
	/**
	 * Testet ob ein Basket gelöscht werden kann, obwohl falsche Id übergeben wird, erwartet Fehlerfall
	 */
	@Test
	public void hTestDeletePaymentError() {
		ReturnCodeResponse resp = remoteSystem.deleteBasket(sessionId,  123456789);
		assertNotEquals(200, resp.getReturnCode());
	}
	
	/**
	 * Löscht alle erstellten Datensätze wieder nach beenden der Tests
	 */
	@AfterClass
	public static void endTestCase() {
		remoteSystem.deleteBasket(sessionId,  baskId1);
		remoteSystem.deleteBasket(sessionId,  baskId2);
		remoteSystem.deleteBasket(sessionId,  baskId3);
		remoteSystem.deleteBasket(sessionId,  baskId4);
		remoteSystem.deletePayment(sessionId,  testPayId);
		remoteSystem.deleteCategory(sessionId,  testCatId);
		remoteSystem.deleteVendor(sessionId,  testVenId);
		remoteSystem.logout(sessionId);
	}
	
}
