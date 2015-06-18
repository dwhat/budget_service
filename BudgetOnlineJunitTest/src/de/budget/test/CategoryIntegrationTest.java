/**
 * Package for Unit Tests
 */
package de.budget.test;


import static org.junit.Assert.*;
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
public class CategoryIntegrationTest {

	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private int testCatId;
	
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
		
		CategoryResponse catResp1 = remoteSystem.createOrUpdateCategory(sessionId, 0, true, true, "UnitTestCategoryTest1", "test123", "FFFFFFF");
		assertEquals(200, catResp1.getReturnCode());
		assertEquals("UnitTestCategory", catResp1.getCategoryTo().getName());
		testCatId = catResp1.getCategoryTo().getId();
		CategoryResponse catResp2 = remoteSystem.createOrUpdateCategory(sessionId, 0, false, true, "UnitTestCategoryTest2", "test123123", "FFFFFFF");
		assertEquals(200, catResp2.getReturnCode());
		CategoryResponse catResp3 = remoteSystem.createOrUpdateCategory(sessionId, 0, true, false, "UnitTestCategoryTest3", "test1234", "FFFFFFF");
		assertEquals(200, catResp3.getReturnCode());
		assertTrue(catResp.getCategoryTo().isActive()); //Da neue Kategorien immer als aktive angelegt werden
	}
	
	/**
	 * Testet ob eine Kategorie angelegt werden kann, obwohl schon eine Kategorie mit gleichem Namen existiert. Eerwartet Fehlerfall
	 */
	@Test
	public void bTestCreateCategoryError() {
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, true, true, "UnitTestCategory", "test", "FFFFFFF");
		assertNotEquals(200, catResp.getReturnCode());
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
		CategoryResponse catResp2 = remoteSystem.createOrUpdateCategory(sessionId, testCatId, false, true, "geändert", "test123123", "FFFFFFF");
		assertEquals(200, catResp2.getReturnCode());
		assertEquals("geändert", catResp2.getCategoryTo().getName());
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
	 * Testet ob alle Kategorien als Liste zurück gegeben werden können
	 */
	@Test
	public void fTestGetAllCategories() {
		CategoryListResponse catListResp = remoteSystem.getCategorys(sessionId);
		assertEquals(200, catListResp.getReturnCode());
		assertEquals(4, catListResp.getCategoryList().size()); // Es wurden zuvor 4 Kategorien angelegt
	}
	
	/**
	 * Testet ob alle Kategorien, die Income == true haben zurück gegeben werden
	 */
	@Test
	public void gTestGetIncomeCategories() {
		CategoryListResponse catListResp = remoteSystem.getCategorysOfIncome(sessionId);
		assertEquals(200, catListResp.getReturnCode());
		assertEquals(3, catListResp.getCategoryList().size()); // Es wurden zuvor 3 income Kategorien angelegt
		assertTrue(catListResp.getCategoryList().get(1).isIncome());
	}
	
	/**
	 * Testet ob alle Kategorien, die Income == false haben zurück gegeben werden
	 */
	@Test
	public void hTestGetLossCategories() {
		CategoryListResponse catListResp = remoteSystem.getCategorysOfLoss(sessionId);
		assertEquals(200, catListResp.getReturnCode());
		assertEquals(1, catListResp.getCategoryList().size()); // Es wurden zuvor 3 income Kategorien angelegt
		assertFalse(catListResp.getCategoryList().get(0).isIncome());
	}
	
	/**
	 * Testet ob eine Kategorie gelöscht werden kann
	 */
	@Test
	public void iTestDeleteCategory() {
		ReturnCodeResponse resp = remoteSystem.deleteCategory(sessionId,  testCatId);
		assertEquals(200, resp.getReturnCode());
	}
	
	/**
	 * Testet ob eine Kategorie gelöscht werden kann, obwohl falsche Id übergeben wird, erwartet Fehlerfall
	 */
	@Test
	public void jTestDeleteCategoryError() {
		ReturnCodeResponse resp = remoteSystem.deleteCategory(sessionId,  123456789);
		assertNotEquals(200, resp.getReturnCode());
	}
}
