package de.budget.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.CategoryResponse;

/**
 * Test Klasse zum Testen von Items
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemActionTest {
	
	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;
	private static int testCatId;
	private int testIncId; 
	private long dateLong = System.currentTimeMillis();
	
	/**
	 * Baut einmalig Server Verbindung auf und legt eine Test Kategorie an
	 */
	@BeforeClass
	public static void initTestCase() {
		BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		remoteSystem = service.getBudgetOnlineServiceBeanPort();
		sessionId = remoteSystem.login("JunitTestUser", "25d55ad283aa400af464c76d713c07ad").getSessionId();
		
		CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, 0, false, true, "UnitTestLossCategoryzt", "test", "FFFFFFF");
		assertEquals(200, catResp.getReturnCode());
		testCatId = catResp.getCategoryTo().getId();
	}
}
