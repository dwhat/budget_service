package de.budget.client;

import java.util.ArrayList;

import de.budget.onlinebudget.CategoryListResponse;
import de.budget.onlinebudget.CategoryResponse;
import de.budget.onlinebudget.CategoryTO;
import de.budget.onlinebudget.PaymentTO;
import de.budget.onlinebudget.PaymentListResponse;
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
		   //szenarioEmma();
		   //szenarioJoe();		   	       
		   //szenarioEmma();
		   //szenarioPeter();
 	      szenarioCategory();
		   
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		   	ex.printStackTrace();
		}
	}
	
	/**
	 * szenario to test a login an to create, update, get and delete a category
	 * @author Marco
	 */
	private static void szenarioCategory() {
		   System.out.println("============================================================");
	       UserLoginResponse loginResponse = remoteSystem.login("emma", "12345678");
	       if (loginResponse != null & loginResponse.getReturnCode()==200) {
	    	   int sessionId = loginResponse.getSessionId();
			   System.out.println("Emma hat sich angemeldet");
			   System.out.println("LoginReturnCode: " + loginResponse.getReturnCode());
			   
			   createCategoryHelper(sessionId, 0, true, true, "Lohn", "notiz", "FFTTZZ");
			   createCategoryHelper(sessionId, 0, true, true, "Lohn1", "notiz1", "FFTTZZ");
			   createCategoryHelper(sessionId, 0, false, true, "Einkauf", "notiz1", "FFTTZZ");
			   

			   int sampleCategoryId = 0; //Für spätere Test bei update und get
			   CategoryListResponse catListResp = remoteSystem.getCategorys(sessionId);
			   System.out.println("KategorieListe ausgeben ReturnCode: " + catListResp.getReturnCode());
			   if(catListResp.getReturnCode() == 200) {
				   ArrayList<CategoryTO> catList= (ArrayList<CategoryTO>) catListResp.getCategoryList();
				   System.out.println("es sind " + catList.size() + " Kategorien vorhanden.");
				   sampleCategoryId = catList.get(1).getId();
				   for (CategoryTO c : catList){
					   System.out.println("ID: "+ c.getId() + "  Name: " + c.getName());
				   }
			   }
			   else {
				   System.out.println("Message: " + catListResp.getMessage());
			   }
			   System.out.println("Message: " + catListResp.getMessage());
			   
			   System.out.println("Suche Kategory mit Id " + sampleCategoryId);
			   CategoryResponse catResp = remoteSystem.getCategory(sessionId, sampleCategoryId);
			   System.out.println("KategorieÄndern ReturnCode: " + catResp.getReturnCode());
			   if(catResp.getReturnCode() == 200) {
				   System.out.println("ID: "+ catResp.getCategoryTo().getId() + "  Name: " + catResp.getCategoryTo().getName());
			   }
			   else {
				   System.out.println("Message: " + catResp.getMessage());
			   }
			   System.out.println("Message: " + catResp.getMessage());
			   
			   System.out.println("Ändere Kategory mit Id " + sampleCategoryId);
			   createCategoryHelper(sessionId, sampleCategoryId, false, true, "EinkaufÄndern", "notiz1", "FFTTZZ");
			   
			   System.out.println("Lösche Kategory mit Id " + sampleCategoryId);
			   ReturnCodeResponse resp = remoteSystem.deleteCategory(sessionId, sampleCategoryId);
			   System.out.println("Kategorielöschen ReturnCode: " + resp.getReturnCode());
			   if(resp.getReturnCode() == 200) {
				   System.out.println("Suche Kategory mit Id " + sampleCategoryId);
				   CategoryResponse catResp1 = remoteSystem.getCategory(sessionId, sampleCategoryId);
				   System.out.println("Kategorie ReturnCode: " + catResp1.getReturnCode());
			   }
			   else {
				   System.out.println("Message: " + resp.getMessage());
			   }
			   System.out.println("Message: " + resp.getMessage());
			   
	       }
	       
	}
	
	/**
	 * HelferMethode zum anlegen von Kategorien
	 * @author Marco
	 * @date 08.06.2015
	 */
	private static void createCategoryHelper (int sessionId, int catId, boolean income, boolean active, String name, String notice, String colour) {
		System.out.println("Lege Kategorie an. ");
		   CategoryResponse catResp = remoteSystem.createOrUpdateCategory(sessionId, catId, income, active, name, notice, colour);
		   System.out.println("KategorieAnlegen ReturnCode: " + catResp.getReturnCode());
		   if(catResp.getReturnCode() == 200) {
			   System.out.println("Category angelegt");
			   if(catResp.getCategoryTo() != null) {
				   System.out.println("Kategorie Eigenschaften: ");
				   System.out.println("Name = " + catResp.getCategoryTo().getName());
				   System.out.println("Notiz = " + catResp.getCategoryTo().getNotice());
				   System.out.println("Owner = " + catResp.getCategoryTo().getUser().getUsername());
			   }
			   else {
				   System.out.println ("Category ist gleich null");
			   }
		   }
		   else {
			   System.out.println("Message: " + catResp.getMessage());
		   }
		   System.out.println("Message: " + catResp.getMessage());
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
		   /*
		   System.out.println("Emma legt ein Payment an");
		   PaymentResponse paymentResponse = remoteSystem.createOrUpdatePayment(sessionId, 0, "Volksbank", "123456789", "DE12345678", true);
		   System.out.println("Emma hat ein Payment angelegt");
		   System.out.println("Paymentname = " + paymentResponse.getPaymentTo().getName());
		   System.out.println("Paymentnumber = " + paymentResponse.getPaymentTo().getNumber());
		   System.out.println("PaymentBIC = " + paymentResponse.getPaymentTo().getBic());
		   System.out.println(paymentResponse.getReturnCode());
		   */
		   // Payment anlegen
			PaymentResponse payResp = remoteSystem.createOrUpdatePayment(sessionId, 12, "Konto1789", "123456789", "BIC", true);
			System.out.println("Neuerzeugtes Konto mit Namen: " + payResp.getReturnCode());
			System.out.println("Neuerzeugtes Konto mit Namen: " + payResp.getPaymentTo().getName());
			System.out.println("Joa hat ein Payment angelegt");
					
			// Payment abrufen
			PaymentListResponse payListResp = remoteSystem.getPayments(sessionId);
			ArrayList<PaymentTO> paymentList = (ArrayList<PaymentTO>) payListResp.getPaymentList();
			System.out.println("Erster Name des Kontos: " + paymentList.get(0).getName());
			
			//Logout
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

