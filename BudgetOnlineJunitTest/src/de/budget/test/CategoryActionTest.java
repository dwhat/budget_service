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



import de.budget.onlinebudget.CategoryListResponse;
import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.CategoryResponse;
import de.budget.onlinebudget.ReturnCodeResponse;

/**
 * Test Klasse zum Testen von Kategorien
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryActionTest {

	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private static int testCatId;
	private static int testCatId1;
	private static int testCatId2;
	private static int testCatId3;
	
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
	 * Testet ob eine Kategorie angelegt werden kann. Erwartet den OK-Code 200 und den Namen der zuvor angelegten Kategorie
	 */
	@Test
	public void aTestCreateCategory() {
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, true, true, "UnitTestCategory", "test", "FFFFFFF");
		assertEquals(200, catResp.getReturnCode());
		assertEquals("UnitTestCategory", catResp.getCategoryTo().getName());
		testCatId = catResp.getCategoryTo().getId();
		CategoryResponse catResp1 = remoteSystem.createOrUpdateCategory(sessionId, 0, true, true, "UnitTestCategoryTest1", "test123", "FFFFFFF");
		assertEquals(200, catResp1.getReturnCode());
		assertEquals("UnitTestCategoryTest1", catResp1.getCategoryTo().getName());
		testCatId1 = catResp1.getCategoryTo().getId();
		CategoryResponse catResp2 = remoteSystem.createOrUpdateCategory(sessionId, 0, false, true, "UnitTestCategoryTest2", "test123123", "FFFFFFF");
		assertEquals(200, catResp2.getReturnCode());
		testCatId2 = catResp2.getCategoryTo().getId();
		CategoryResponse catResp3 = remoteSystem.createOrUpdateCategory(sessionId, 0, true, false, "UnitTestCategoryTest3", "test1234", "FFFFFFF");
		assertEquals(200, catResp3.getReturnCode());
		assertTrue(catResp3.getCategoryTo().isActive()); //Da neue Kategorien immer als aktive angelegt werden
		testCatId3 = catResp3.getCategoryTo().getId();
		
	}
	
	/**
	 * Testet ob eine Kategorie angelegt werden kann, obwohl schon eine Kategorie mit gleichem Namen existiert. Eerwartet Fehlerfall
	 */
	@Test
	public void bTestCreateCategoryError() {
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, true, true, "UnitTestCategory", "test", "FFFFFFF");
		assertNotEquals(200, catResp.getReturnCode());
		assertEquals(400, catResp.getReturnCode());
	}
	
	/**
	 * Testet ob eine Kategorie mit einer bestimmten Id gefunden werden kann
	 */
	@Test
	public void cTestGetOneCategory() {
		CategoryResponse catResp = remoteSystem.getCategory(sessionId, testCatId);
		assertEquals(200, catResp.getReturnCode());
		assertEquals(testCatId, catResp.getCategoryTo().getId());
	}
	
	/**
	 * Testet ob eine Category geupdatet werden kann
	 */
	@Test
	public void dTestUpdateCategory() {
		CategoryResponse catResp2 = remoteSystem.createOrUpdateCategory(sessionId, testCatId, false, true, "ge�ndert", "test123123", "FFFFFFF");
		assertEquals(200, catResp2.getReturnCode());
		assertEquals("ge�ndert", catResp2.getCategoryTo().getName());
	}
	
	/**
	 * Testet ob eine Kategorie mit einer unbekannten Id nicht gefunden wird. Erwaretet Fehlerfall
	 */
	@Test
	public void eTestGetOneCategoryError() {
		CategoryResponse catResp = remoteSystem.getCategory(sessionId, 123456789);
		assertEquals(404, catResp.getReturnCode());
	}
		
	/**
	 * Testet ob alle Kategorien als Liste zur�ck gegeben werden k�nnen
	 */
	@Test
	public void fTestGetAllCategories() {
		CategoryListResponse catListResp = remoteSystem.getCategorys(sessionId);
		assertEquals(200, catListResp.getReturnCode());
		assertEquals(4, catListResp.getCategoryList().size()); // Es wurden zuvor 4 Kategorien angelegt
	}
	
	/**
	 * Testet ob alle Kategorien, die Income == true haben zur�ck gegeben werden
	 */
	@Test
	public void gTestGetIncomeCategories() {
		CategoryListResponse catListResp = remoteSystem.getCategorysOfIncome(sessionId);
		assertEquals(200, catListResp.getReturnCode());
		assertEquals(2, catListResp.getCategoryList().size()); // Es wurden zuvor 2 income Kategorien angelegt
		assertTrue(catListResp.getCategoryList().get(1).isIncome());
	}
	
	/**
	 * Testet ob alle Kategorien, die Income == false haben zur�ck gegeben werden
	 */
	@Test
	public void hTestGetLossCategories() {
		CategoryListResponse catListResp = remoteSystem.getCategorysOfLoss(sessionId);
		assertEquals(200, catListResp.getReturnCode());
		assertEquals(2, catListResp.getCategoryList().size()); // Es wurden zuvor 2 loss Kategorien angelegt
		assertFalse(catListResp.getCategoryList().get(0).isIncome());
	}
	
	/**
	 * Testet ob eine Kategorie gel�scht werden kann
	 */
	@Test
	public void iTestDeleteCategory() {
		ReturnCodeResponse resp = remoteSystem.deleteCategory(sessionId,  testCatId);
		assertEquals(200, resp.getReturnCode());
		CategoryResponse catResp = remoteSystem.getCategory(sessionId, testCatId);
		assertEquals(404, catResp.getReturnCode());
	}
	
	/**
	 * Testet ob eine Kategorie gel�scht werden kann, obwohl falsche Id �bergeben wird, erwartet Fehlerfall
	 */
	@Test
	public void jTestDeleteCategoryError() {
		ReturnCodeResponse resp = remoteSystem.deleteCategory(sessionId,  123456789);
		assertNotEquals(200, resp.getReturnCode());
	}
	
	/**
	 * L�scht alle zum Test erstellten Daten
	 */
	@AfterClass
	public static void endTestCase() {
		remoteSystem.deleteCategory(sessionId,  testCatId1);
		remoteSystem.deleteCategory(sessionId,  testCatId2);
		remoteSystem.deleteCategory(sessionId,  testCatId3);
		remoteSystem.logout(sessionId);
	}
}
