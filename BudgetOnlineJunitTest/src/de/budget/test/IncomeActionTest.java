/**
 * Package for Unit Tests
 */
package de.budget.test;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.budget.onlinebudget.AmountResponse;
import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.CategoryResponse;
import de.budget.onlinebudget.IncomeListResponse;
import de.budget.onlinebudget.IncomeResponse;
import de.budget.onlinebudget.ReturnCodeResponse;
import de.budget.onlinebudget.AmountListResponse;


/**
 * Test Klasse zum Testen von Incomes
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IncomeActionTest {

	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private static int testCatId;
	private static int testIncId; 
	private static int testIncId1; 
	private static int testIncId2; 
	private static int testIncId3; 
	private long dateLong = System.currentTimeMillis();
	
	/**
	 * Baut einmalig Server Verbindung auf und legt eine Test Kategorie an
	 */
	@BeforeClass
	public static void initTestCase() {
		BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		remoteSystem = service.getBudgetOnlineServiceBeanPort();
		sessionId = remoteSystem.login("JunitTestUser", "25d55ad283aa400af464c76d713c07ad").getSessionId();
		
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, true, true, "UnitTestCategory", "test", "FFFFFFF");
		assertEquals(200, catResp.getReturnCode());
		testCatId = catResp.getCategoryTo().getId();
		
	}
	
	/**
	 * Testet ob Einnamhen angelegt werden können
	 */
	@Test
	public void aTestCreateIncome() {
		IncomeResponse incResp = remoteSystem.createOrUpdateIncome(sessionId, 0, "testEinnahme", 2.00, 3.00 ,"Notiz", dateLong, testCatId);
		assertEquals(200, incResp.getReturnCode());
		assertEquals("testEinnahme", incResp.getIncomeTo().getName());
		testIncId1 = incResp.getIncomeTo().getId();
		IncomeResponse incResp1 = remoteSystem.createOrUpdateIncome(sessionId, 0, "testEinnahme1", 2.00, 3.00 ,"Notiz1", dateLong, testCatId);
		assertEquals(200, incResp1.getReturnCode());
		assertEquals("testEinnahme1", incResp1.getIncomeTo().getName());
		testIncId2 = incResp1.getIncomeTo().getId();
		IncomeResponse incResp2 = remoteSystem.createOrUpdateIncome(sessionId, 0, "testEinnahme2", 2.00, 3.00 ,"Notiz2", dateLong, testCatId);
		assertEquals(200, incResp2.getReturnCode());
		assertEquals("testEinnahme2", incResp2.getIncomeTo().getName());
		testIncId3 = incResp2.getIncomeTo().getId();
		IncomeResponse incResp3 = remoteSystem.createOrUpdateIncome(sessionId, 0, "testEinnahme3", 2.00, 3.00 ,"Notiz3", dateLong, testCatId);
		assertEquals(200, incResp3.getReturnCode());
		assertEquals("testEinnahme3", incResp3.getIncomeTo().getName());
		testIncId = incResp3.getIncomeTo().getId();
	}
	
	/**
	 * Testet ob eine Einnamhe ohne Namen angelegt werden kann. Erwartet Fehlerfall
	 */
	@Test
	public void bTestCreateIncomeError() {
		//IncomeResponse incResp = remoteSystem.createOrUpdateIncome(sessionId, 0, null, 2.00, 3.00 ,"Notiz", dateLong, testCatId);
		//assertNotEquals(200, incResp.getReturnCode());
	}
	
	/**
	 * Testet ob eine Einnahme mit einer bestimmten Id gefunden werden kann
	 */
	@Test
	public void cTestGetOneIncome() {
		IncomeResponse incResp = remoteSystem.getIncome(sessionId, testIncId);
		assertEquals(200, incResp.getReturnCode());
		assertEquals(testIncId, incResp.getIncomeTo().getId());
		assertEquals("testEinnahme3", incResp.getIncomeTo().getName());
	}
	
	/**
	 * Testet ob eine Einnahme geupdatet werden kann
	 */
	@Test
	public void dTestUpdateIncome() {
		IncomeResponse incResp = remoteSystem.createOrUpdateIncome(sessionId, testIncId, "testEinnahmeGeändert", 2.00, 3.00 ,"Notiz", dateLong, testCatId);
		assertEquals(200, incResp.getReturnCode());
		assertEquals("testEinnahmeGeändert", incResp.getIncomeTo().getName());
	}
	
	/**
	 * Testet ob eine Einnahme mit einer bestimmten Id gefunden werden kann, wenn es die Id nicht gibt, Erwartet Fehlerfall
	 */
	@Test
	public void eTestGetOneIncomeError() {
		IncomeResponse incResp = remoteSystem.getIncome(sessionId, 12345678);
		assertEquals(404, incResp.getReturnCode());
	}
	
	/**
	 * Testet ob alle Incomes als Liste zurück gegeben werden können
	 */
	@Test
	public void fTestGetAllIncomes() {
		IncomeListResponse incListResp = remoteSystem.getIncomes(sessionId);
		assertEquals(200, incListResp.getReturnCode());
		assertEquals(4, incListResp.getIncomeList().size()); // Es wurden zuvor 4 Incomes angelegt
	}
	
	/**
	 * Testet ob ein Income gelöscht werden kann
	 */
	@Test
	public void gTestDeleteIncome() {
		ReturnCodeResponse resp = remoteSystem.deleteIncome(sessionId,  testIncId);
		assertEquals(200, resp.getReturnCode());
		IncomeResponse incResp = remoteSystem.getIncome(sessionId, testIncId);
		assertEquals(404, incResp.getReturnCode());
	}
	
	/**
	 * Testet ob ein Income gelöscht werden kann
	 */
	@Test
	public void hTestDeleteIncomeError() {
		ReturnCodeResponse resp = remoteSystem.deleteIncome(sessionId,  123456789);
		assertEquals(407, resp.getReturnCode());
	}
	
	/**
	 * Testet die Berechnung des Betrag pro Category
	 */
	@Test
	public void iTestIncomesAmountForCategories() {
		AmountListResponse resp = remoteSystem.getIncomesAmountForCategories(sessionId);
		assertEquals(200, resp.getReturnCode());
		assertEquals(18.0, resp.getAmountList().get(0).getValue(), 0.1);
		assertEquals("UnitTestCategory", resp.getAmountList().get(0).getName());
	}
	
	/**
	 * Testet die Berechnung des Betrag der Einahmen diesen Monats
	 */
	@Test
	public void jTestIncomesAmountActualMonth() {
		AmountResponse resp = remoteSystem.getIncomesOfActualMonth(sessionId);
		assertEquals(200, resp.getReturnCode());
		assertTrue(0.0 < resp.getValue());
	}
	
	/**
	 * Löscht alle zum Test erstellten Daten
	 */
	@AfterClass
	public static void endTestCase() {
		remoteSystem.deleteIncome(sessionId,  testIncId);
		remoteSystem.deleteIncome(sessionId,  testIncId1);
		remoteSystem.deleteIncome(sessionId,  testIncId2);
		remoteSystem.deleteIncome(sessionId,  testIncId3);
		remoteSystem.deleteCategory(sessionId,  testCatId);
		remoteSystem.logout(sessionId);
	}
}