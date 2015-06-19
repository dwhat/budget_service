/**
 * Package for Unit Tests
 */
package de.budget.test;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Testet alle Testfälle
 * @author Marco
 */
@RunWith(Suite.class)
@SuiteClasses({ CategoryActionTest.class, PaymentActionTest.class, 
	IncomeActionTest.class, UserActionTest.class, VendorActionTest.class})
public class TestAll {

}
