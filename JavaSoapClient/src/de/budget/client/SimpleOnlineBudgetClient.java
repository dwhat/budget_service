package de.budget.client;

import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.PaymentResponse;
import de.budget.onlinebudget.ReturnCodeResponse;
import de.budget.onlinebudget.UserLoginResponse;


public class SimpleOnlineBudgetClient {

	private static BudgetOnlineServiceBean remoteSystem;
	
	
	public static void main(String[] args) {
		try {
		   BudgetOnlineServiceBeanService service = new BudgetOnlineServiceBeanService();
		   remoteSystem = service.getBudgetOnlineServiceBeanPort();
 	       
 	       //Zeige, welche Referenz auf das Server-Objekt der Client erhalten hast:
 	       System.out.println("Client hat folgendes Server-Objekt erhalten:");
 	       System.out.println(remoteSystem.toString());
 	       System.out.println();
 	       
 	       //Test-Szeanarien ausfuehren:
		   szenarioEmma();
		   //szenarioJoe();		   	       
		   //szenarioEmma();
		   //szenarioPeter();
		   
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		   	ex.printStackTrace();
		}
	}
	
    /**
     * Test-Szenario: Emma meldet sich an
     */

	private static void szenarioEmma() {
	   System.out.println("============================================================");
       UserLoginResponse loginResponse = remoteSystem.login("emma", "12345678");
       if (loginResponse != null & loginResponse.getReturnCode()==200) {
    	   int sessionId = loginResponse.getSessionId();
		   System.out.println("Emma hat sich angemeldet");
		   System.out.println(loginResponse.getReturnCode());
		   
		   System.out.println("Emma legt ein Payment an");
		   PaymentResponse paymentResponse = remoteSystem.createOrUpdatePayment(sessionId, 0, "Volksbank", "123456789", "DE12345678", true);
		   System.out.println("Emma hat ein Payment angelegt");
		   System.out.println("Paymentname = " + paymentResponse.getPaymentTo().getName());
		   System.out.println("Paymentnumber = " + paymentResponse.getPaymentTo().getNumber());
		   System.out.println("PaymentBIC = " + paymentResponse.getPaymentTo().getBic());
		   System.out.println(paymentResponse.getReturnCode());
		   
	       ReturnCodeResponse resp = remoteSystem.logout(sessionId);
		   System.out.println("Emma hat sich abgemeldet.");
		   System.out.println(resp.getReturnCode());
		   
       }
       else {
    	   System.out.println(loginResponse.getMessage());
       }
	}

    /**
     * Test-Szenario: Joe meldet sich an
     */
	/*
	private static void szenarioJoe() {
       UserLoginResponse loginResponse = remoteSystem.login("joe", "joe1");
       if (loginResponse.getReturnCode()==0) {
    	   int sessionId = loginResponse.getSessionId();
		   System.out.println("Joe hat sich angemeldet, um eine Ueberweisung aufzugeben.");

		   AccountListResponse accountListResponse = remoteSystem.getMyAccounts(sessionId); 
	       List<AccountTO> joesKonten = accountListResponse.getAccountList();
	       if (joesKonten.size()>0) {
	    	   //gib Joes Konten aus:
		       System.out.println("Joe hat folgende Konten:");
		       for (AccountTO k : joesKonten) {
		    	   System.out.println("Konto-Nr: " + k.getId() + " Saldo: " + k.getBalance());
		       }
		       System.out.println();
		       
		       TransferMoneyResponse transferResponse = remoteSystem.transfer(sessionId, JOES_KONTO, EMMAS_KONTO, new BigDecimal(33));
		       System.out.println("Joe hat von Konto "+ JOES_KONTO +" 33 EUR an Konto " + EMMAS_KONTO + " ueberwiesen.");
		       System.out.println("Der neue Saldo von Konto "+JOES_KONTO+" betraegt: " + transferResponse.getNewBalance());
	       }
	       remoteSystem.logout(sessionId);
		   System.out.println("Joe hat sich abgemeldet.");
	   }
       else {
    	   System.out.println(loginResponse.getMessage());
       }       
	}
	*/
	/**
	 * Test-Szenario mit Kundenregistrierung 
	 */
	/*
	private static void szenarioPeter() {
	   System.out.println();
	   System.out.println("============================================================");
	   System.out.println("Peters Szenario:");

       UserLoginResponse loginResponse = remoteSystem.registerNewCustomer("peter", "peter1");
       if (loginResponse.getReturnCode()==0) {
    	   int sessionId = loginResponse.getSessionId();
		   System.out.println("Peter hat sich erfolgreich registriert.");
	       remoteSystem.logout(sessionId);
		   System.out.println("Peter hat sich abgemeldet.");
	   }
       else {
    	   System.out.println(loginResponse.getMessage());
       }       
	}
	*/
}

