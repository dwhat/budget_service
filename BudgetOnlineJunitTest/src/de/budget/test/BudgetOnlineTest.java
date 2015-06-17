/**
 * Package for Junit Test
 */
package de.budget.test;


import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;


//Import jUnit

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Test class for JunitTest for the service
 * @author Marco
 * 01.06.2015
 */
public class BudgetOnlineTest {
	
	private BudgetOnlineServiceBean remoteSystem;
	Endpoint endpoint;
	private int sessionId;
	
	public BudgetOnlineTest() {
		
	}
	
	@Before
	public void startUp() throws MalformedURLException {
		//endpoint = Endpoint.publish("http://85.214.64.59:8080/budget/BudgetOnlineServiceBean", new BudgetOnlineServiceBean());
	    //assertTrue(endpoint.isPublished());
	    //assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());
	 
	    // Data to access the web service
	    URL wsdlDocumentLocation = new URL("http://85.214.64.59:8080/budget/BudgetOnlineServiceBean?wsdl");
	    String namespaceURI = "http://onlinebudget.budget.de/";
	    String servicePart = "BudgetOnlineServiceBeanService";
	    String portName = "BudgetOnlineServiceBeanPort";
	    QName serviceQN = new QName(namespaceURI, servicePart);
	    //QName portQN = new QName(namespaceURI, portName);
	 
	    // Creates a service instance
	    Service service = Service.create(wsdlDocumentLocation, serviceQN);
	    //remoteSystem = service.getPort(portQN, BudgetOnlineServiceBean.class);
	    remoteSystem = service.getPort(BudgetOnlineServiceBean.class);

		sessionId = remoteSystem.login("emma", "25d55ad283aa400af464c76d713c07ad").getSessionId();
	}
	
	/*
	@Test
	public void paymentTest(){
		//anlegen
		PaymentResponse resp = remoteSystem.createOrUpdatePayment(sessionId, 0, "VB", "DE123456789", "BIC123", true);
		assertEquals(200, resp.getReturnCode());
		assertEquals("VB", resp.getPaymentTo().getName());
		assertEquals(true, resp.getPaymentTo().isActive());
		int paymentId = resp.getPaymentTo().getId();
		//bestimmtes Payment suchen
		resp = remoteSystem.getPayment(sessionId, paymentId);
		assertEquals(paymentId, resp.getPaymentTo().getId());
		assertEquals("DE123456789", resp.getPaymentTo().getNumber());
		//Update
		resp = remoteSystem.createOrUpdatePayment(sessionId, paymentId, "VB_Change", "DE123456789", "BIC123", false);
		assertEquals("VB_Change", resp.getPaymentTo().getName());
		assertEquals(false, resp.getPaymentTo().isActive());
		//Liste aller Payments laden
		PaymentListResponse payListResp = remoteSystem.getPayments(sessionId);
		assertEquals(200, payListResp.getReturnCode());
		ArrayList<PaymentTO> payList = (ArrayList<PaymentTO>) payListResp.getPaymentList();
		int oldSize = payList.size();
		//ein Payment hinzufügen
		remoteSystem.createOrUpdatePayment(sessionId, 0, "VB1", "DE12345678910", "BIC123456", true);
		PaymentListResponse payListResp1 = remoteSystem.getPayments(sessionId);
		assertEquals(200, payListResp1.getReturnCode());
		//Länge der Listen vergleichen, ob ein Payment dazugekommen ist
		ArrayList<PaymentTO> payList1 = (ArrayList<PaymentTO>) payListResp1.getPaymentList();
		assertEquals(oldSize+1, payList1.size());
		remoteSystem.deletePayment(sessionId, paymentId);
		assertEquals(oldSize, remoteSystem.getPayments(sessionId).getPaymentList().size());
	}
	*/
	@Test
	public void test() {
		assertEquals(true, true);
	}
	@After
	public void end() {

	    // Unpublishes the SOAP Web Service
	    endpoint.stop();
	    assertFalse(endpoint.isPublished());
	}
	
}
