package de.xbank.client;

import javax.naming.Context;
import javax.naming.InitialContext;

import de.budget.common.BudgetOnlineService;
import de.budget.dto.UserLoginResponse;



/**
 * @author Thoene
 * Diese Klasse realisiert einen rudimentaeren Client zum Zugriff auf das OnlineBankingSystem.
 */
public class SimpleBudgetJavaClient {
	
	private static BudgetOnlineService remoteSystem;
	
	/**
	 * In dieser Main-Methode werden Requests an den Onlinebanking-Server abgeschickt. Sie koennen die durchgefuehrten
	 * Szenarien nach Belieben anpassen.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
           Context context = new InitialContext();
	       
	       //Lookup-String für eine EJB besteht aus: Name_EA/Name_EJB-Modul/Name_EJB-Klasse!Name_RemoteInterface
	       String lookupString = "ejb:BudgetEAR/BudgetService/BudgetOnlineServiceBean!de.budget.common.BudgetOnlineService";
	       remoteSystem = (BudgetOnlineService) context.lookup(lookupString);
 	       
 	       //Zeige, welche Referenz auf das Server-Objekt der Client erhalten hast:
 	       System.out.println("Client hat folgendes Server-Objekt nach dem Lookup erhalten:");
 	       System.out.println(remoteSystem.toString());
 	       System.out.println();
 	       
 	       //Test-Szeanarien ausfuehren:
		   szenarioEmma();
		   szenarioJoe();		   	       
		   szenarioEmma();
		   
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		}
	}
	
    /**
     * Test-Szenario: Emma meldet sich an und fragt ihren Kontostand ab.
     */
	private static void szenarioEmma() {
		try {
		   System.out.println("============================================================");			
	       UserLoginResponse resp1 = remoteSystem.login("emma", "emma1");
		   System.out.println("Emma hat sich angemeldet.");
		   int sessionID = resp1.getSessionId();
	       remoteSystem.logout(sessionID);
		   System.out.println("Emma hat sich abgemeldet.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * Test-Szenario: Joe meldet sich an, fragt seine Konten ab und überweist an Emma
     */
	private static void szenarioJoe() {
		try {
			System.out.println("============================================================");			
			UserLoginResponse resp1 = remoteSystem.login("joe", "joe1");
			System.out.println("Joe hat sich angemeldet.");
			int sessionID = resp1.getSessionId();
			remoteSystem.logout(sessionID);
			System.out.println("Joe hat sich abgemeldet.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
