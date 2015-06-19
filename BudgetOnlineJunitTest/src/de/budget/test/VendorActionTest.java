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
import de.budget.onlinebudget.VendorListResponse;
import de.budget.onlinebudget.VendorResponse;
import de.budget.onlinebudget.ReturnCodeResponse;

/**
 * Test Klasse zum Testen von Vendor
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VendorActionTest {

	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private int testVenId;
	
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
	 * Testet ob ein Vendor angelegt werden kann. Erwartet den OK-Code 200 und den Namen des zuvor angelegten Vendors
	 */
	@Test
	public void aTestCreateVendor() {
		VendorResponse resp = remoteSystem.createOrUpdateVendor(sessionId, 0, "Rewe", "BILD", "Straße1", "Stadt1", 48691, 22);
		assertEquals(200, resp.getReturnCode());
		assertEquals("Rewe", resp.getVendorTo().getName());
		
		VendorResponse resp1 = remoteSystem.createOrUpdateVendor(sessionId, 0, "Rewe1", "BILD1", "Straße1", "Stadt1", 48691, 22);
		assertEquals(200, resp1.getReturnCode());
		assertEquals("Rewe1", resp1.getVendorTo().getName());
		testVenId = resp1.getVendorTo().getId();
		
		VendorResponse resp2 = remoteSystem.createOrUpdateVendor(sessionId, 0, "Rewe2", "BILD2", "Straße1", "Stadt1", 48691, 22);
		assertEquals(200, resp2.getReturnCode());
		assertEquals("Rewe2", resp2.getVendorTo().getName());
		testVenId = resp2.getVendorTo().getId();
	}
	
	/**
	 * Testet ob ein Vendor angelegt werden kann, obwohl schon ein Vendor mit gleichem Namen existiert. Erwartet Fehlerfall
	 */
	@Test
	public void bTestCreateVendorError() {
		VendorResponse resp = remoteSystem.createOrUpdateVendor(sessionId, 0, "Rewe", "BILD", "Straße1", "Stadt1", 48691, 22);
		assertNotEquals(200, resp.getReturnCode());
		assertEquals(, resp.getReturnCode());
	}
	
	/**
	 * Testet ob ein Vendor mit einer bestimmten Id gefunden werden kann
	 */
	@Test
	public void cTestGetOneVendor() {
		VendorResponse venResp = remoteSystem.getVendor(sessionId, testVenId);
		assertEquals(200, venResp.getReturnCode());
		assertEquals(testVenId, venResp.getVendorTo().getId());
	}
	
	/**
	 * Testet ob ein Vendor geupdatet werden kann
	 */
	@Test
	public void dTestUpdateVendor() {
		VendorResponse resp = remoteSystem.createOrUpdateVendor(sessionId, 0, "geändert", "BILD", "Straße1", "Stadt1", 48691, 22);
		assertEquals(200, resp.getReturnCode());
		assertEquals("geändert", resp.getVendorTo().getName());
	}
	
	/**
	 * Testet ob ein Vendor mit einer unbekannten Id nicht gefunden wird. Erwaretet Fehlerfall
	 */
	@Test
	public void dTestGetOneVendorError() {
		VendorResponse venResp = remoteSystem.getVendor(sessionId, 123456789);
		assertEquals(404, venResp.getReturnCode());
	}
		
	/**
	 * Testet ob alle Vendors als Liste zurück gegeben werden können
	 */
	@Test
	public void eTestGetAllVendors() {
		VendorListResponse venListResp = remoteSystem.getVendors(sessionId);
		assertEquals(200, venListResp.getReturnCode());
		assertEquals(3, venListResp.getVendorList().size()); // Es wurden zuvor 3 Vendors angelegt
	}
	
	/**
	 * Testet ob ein Vendor gelöscht werden kann
	 */
	@Test
	public void fTestDeleteVendor() {
		ReturnCodeResponse resp = remoteSystem.deleteVendor(sessionId,  testVenId);
		assertEquals(200, resp.getReturnCode());
		VendorResponse payResp = remoteSystem.getVendor(sessionId, testVenId);
		assertEquals(404, payResp.getReturnCode());
	}
	
	/**
	 * Testet ob ein Vendor gelöscht werden kann, obwohl falsche Id übergeben wird, erwartet Fehlerfall
	 */
	@Test
	public void gTestDeleteVendorError() {
		ReturnCodeResponse resp = remoteSystem.deleteVendor(sessionId,  123456789);
		assertNotEquals(200, resp.getReturnCode());
	}
}
	