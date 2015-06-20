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

import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.ReturnCodeResponse;
import de.budget.onlinebudget.UserLoginResponse;

/**
 * Test Klasse zum Testen von Useractions
 * @author Marco
 * The tests must have a special order to run
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserActionTest {
	
	private static BudgetOnlineServiceBean remoteSystem;
	private static int sessionId;

	
	/**
	 * Baut einmalig Server Verbindung auf
	 */
	@BeforeClass
	public static void initTestCase() {
		BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		remoteSystem = service.getBudgetOnlineServiceBeanPort();
	}
	
	/**
	 * Testet die Registrierung eines Users
	 */
	@Test
	public void aTestRegisterUser() {
		UserLoginResponse resp = remoteSystem.setUser("xyzUserTestJunit", "25d55ad283aa400af464c76d713c07ad", "test@junitTest.de");
		assertEquals(200, resp.getReturnCode());
		sessionId = resp.getSessionId();
	}
	
	/**
	 * Testet die Logout Funktion
	 */
	@Test
	public void bTestLogout() {
		ReturnCodeResponse resp = remoteSystem.logout(sessionId);
		assertEquals(200, resp.getReturnCode());
	}
	
	/**
	 * Testet die Registrierung eines Users mit bereits vorhandenen Namen, erwartet Fehlerfall
	 */
	@Test
	public void cTestRegisterUserError() {
		UserLoginResponse resp = remoteSystem.setUser("xyzUserTestJunit", "25d55ad283aa400af464c76d713c07ad", "test@junitTest.de");
		assertEquals(409, resp.getReturnCode());
	}
	
	/**
	 * Testet die Registrierung eines Users mit zu kurzem Passwort, erwartet Fehlerfall
	 */
	@Test
	public void dTestRegisterUserErrorPassword() {
		UserLoginResponse resp = remoteSystem.setUser("xyzUserTestJunit123456789", "123", "test@junitTest.de");
		assertEquals(500, resp.getReturnCode());
	}
	
	/**
	 * Testet das Einloggen eines Users
	 */
	@Test
	public void eTestLoginUser() {
		UserLoginResponse resp = remoteSystem.login("xyzUserTestJunit", "25d55ad283aa400af464c76d713c07ad");
		assertEquals(200, resp.getReturnCode());
		sessionId = resp.getSessionId();
	}
	
	/**
	 * Testet die Logout Funktion
	 */
	@Test
	public void fTestLogout() {
		ReturnCodeResponse resp = remoteSystem.logout(sessionId);
		assertEquals(resp.getReturnCode(), 200);
	}
	
	/**
	 * Testet das einloggen eines Users mit falschen Password
	 */
	@Test
	public void gTestLoginWithWrongPassword() {
		UserLoginResponse resp = remoteSystem.login("xyzUserTestJunit", "123456789123456789");
		assertEquals(500, resp.getReturnCode());
	}
	
	/**
	 * Testet das einloggen eines Users mit unbekannten Usernamen
	 */
	@Test
	public void hTestLoginWithWrongUsername() {
		UserLoginResponse resp = remoteSystem.login("xyzblablabla", "123456789123456789");
		assertEquals(20, resp.getReturnCode());
	}
	
	/**
	 * Löscht alle zum Test erstellten Daten
	 */
	@AfterClass
	public static void endTestCase() {
		UserLoginResponse resp = remoteSystem.login("xyzUserTestJunit", "25d55ad283aa400af464c76d713c07ad");
		sessionId = resp.getSessionId();
		remoteSystem.deleteUser(sessionId,  "xyzUserTestJunit");

	}
	

	
	
	
}
