package de.xbank.client;

import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.budget.common.BudgetOnlineService;
import de.budget.dto.PaymentTO;
import de.budget.dto.Response.PaymentListResponse;
import de.budget.dto.Response.PaymentResponse;
import de.budget.dto.Response.UserLoginResponse;


/**
 * Test client for the BudgetServer
 * @author Marco
 * 
 */
public class SimpleBudgetJavaTestClient {
	
	private static BudgetOnlineService remoteSystem;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
           Context context = new InitialContext();
	       
           //java:global/BudgetEAR/BudgetService/BudgetOnlineServiceBean!de.budget.common.BudgetOnlineService
	       //Lookup-String für eine EJB besteht aus: Name_EA/Name_EJB-Modul/Name_EJB-Klasse!Name_RemoteInterface
	       String lookupString = "ejb:BudgetEAR/BudgetService/BudgetOnlineServiceBean!de.budget.common.BudgetOnlineService";
	       remoteSystem = (BudgetOnlineService) context.lookup(lookupString);
 	       
 	       //Zeige, welche Referenz auf das Server-Objekt der Client erhalten hast:
 	       System.out.println("Client hat folgendes Server-Objekt nach dem Lookup erhalten:");
 	       System.out.println(remoteSystem.toString());
 	       System.out.println();
 	       
 	       //Test-Szeanarien ausfuehren:
		   szenarioEmma();
		   //szenarioJoe();		   	       
		   //szenarioPeter();
		   
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		}
	}
	
    /**
     * Test-Szenario: Emma meldet sich an und danach wieder ab
     */
	private static void szenarioEmma() {
		try {
		   System.out.println("============================================================");			
	       UserLoginResponse resp1 = remoteSystem.login("emma", "12345678");
		   System.out.println("Emma hat sich angemeldet.");
		   int sessionID = resp1.getSessionId();
		   System.out.println("Emma hat sich angemeldet.");
	       remoteSystem.logout(sessionID);
		   System.out.println("Emma hat sich abgemeldet.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * Test-Szenario: Joe meldet sich an, und legt ein payment an und ruft dieses ab
     */
	private static void szenarioJoe() {
		try {
			System.out.println("============================================================");			
			UserLoginResponse resp1 = remoteSystem.login("joe", "12345678");
			System.out.println("Joe hat sich angemeldet.");
			int sessionID = resp1.getSessionId();
			
			// Payment anlegen
			PaymentResponse payResp = remoteSystem.createOrUpdatePayment(sessionID, 12, "Konto1789", "123456789", "BIC", true);
			System.out.println("Neuerzeugtes Konto mit Namen: " + payResp.getReturnCode());
			System.out.println("Neuerzeugtes Konto mit Namen: " + payResp.getPaymentTo().getName());
			System.out.println("Joa hat ein Payment angelegt");
			
			// Payment abrufen
			PaymentListResponse payListResp = remoteSystem.getPayments(resp1.getSessionId());
			ArrayList<PaymentTO> paymentList = (ArrayList<PaymentTO>) payListResp.getPaymentList();
			System.out.println("Erster Name des Kontos: " + paymentList.get(0).getName());
			
			// Ausloggen
			remoteSystem.logout(sessionID);
			System.out.println("Joe hat sich abgemeldet.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Peter registriert sich neu und loggt sich danach aus
	 */
	private static void szenarioPeter() {
		System.out.println("============================================================");
		System.out.println("Szenario Peter");

		UserLoginResponse resp1 = remoteSystem.setUser("peter", "12345678", "test1@gmx.de");

		System.out.println("Szenario Peter");

		if (resp1.getReturnCode() == 0) {
			int sessionId = resp1.getSessionId();
			System.out.println("Peter hat sich registiert.");
			remoteSystem.logout(sessionId);
			System.out.println("Peter hat sich ausgelogt.");
		}
		else if(resp1.getReturnCode() == 409) {
			System.out.println("Der User existiert bereits");
		}
		else {
			System.out.println(resp1.getMessage());
		}
	}
}
