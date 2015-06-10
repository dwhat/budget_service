package de.budget.client;

import java.util.ArrayList;

import de.budget.onlinebudget.CategoryListResponse;
import de.budget.onlinebudget.CategoryResponse;
import de.budget.onlinebudget.CategoryTO;
import de.budget.onlinebudget.IncomeListResponse;
import de.budget.onlinebudget.IncomeResponse;
import de.budget.onlinebudget.IncomeTO;
import de.budget.onlinebudget.PaymentTO;
import de.budget.onlinebudget.PaymentListResponse;
import de.budget.onlinebudget.BudgetOnlineServiceBean;
import de.budget.onlinebudget.BudgetOnlineServiceBeanService;
import de.budget.onlinebudget.PaymentResponse;
import de.budget.onlinebudget.ReturnCodeResponse;
import de.budget.onlinebudget.UserLoginResponse;
import de.budget.onlinebudget.VendorListResponse;
import de.budget.onlinebudget.VendorResponse;
import de.budget.onlinebudget.VendorTO;

/**
 * Test Client for the Budget System
 * @author Marco
 * @date 06.06.2015
 */
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
 	       	//szenarioRegister();
 	        szenarioCategory();
 	       	//szenarioVendor();
 	       	//szenarioPayment();
 	       	szenarioIncome();
		   
		}
		catch (Exception ex) {
		   	System.out.println(ex);
		   	ex.printStackTrace();
		}
	}
	
	/**
	 * szenario to test a login an to create, update, get and delete an income
	 * @author Marco
	 */
	private static void szenarioIncome() {
		   System.out.println("============================================================");
	       UserLoginResponse loginResponse = remoteSystem.login("emma", "12345678");
	       if (loginResponse != null & loginResponse.getReturnCode()==200) {
	    	   int sessionId = loginResponse.getSessionId();
			   System.out.println("Emma hat sich angemeldet");
			   System.out.println("LoginReturnCode: " + loginResponse.getReturnCode());
			   System.out.println("============================================================");
			   System.out.println("Suche Kategorie für Income");
			   CategoryListResponse catListRespIncome = remoteSystem.getCategorysOfIncome(sessionId);
			   int katId = catListRespIncome.getCategoryList().get(0).getId();
			   System.out.println("Kategorie mit Id "+ katId + " gefunden");
			   
			   long dateLong = System.currentTimeMillis();
			   System.out.println("============================================================");
			   createIncomeHelper(sessionId, -99, "Lohn", 1.00, 2000.00, "Lohn Mai", dateLong, katId);
			   /*
			   createIncomeHelper(sessionId, -99, "Lohn", 1.00, 2000.00, "Lohn April", dateLong, katId);
			   createIncomeHelper(sessionId, -99, "Lohn", 1.00, 2000.00, "Lohn März", dateLong, katId);
			   System.out.println("============================================================");
			   int sampleIncomeId = 0; //Für spätere Test bei update und get
			   IncomeListResponse incListResp = remoteSystem.getIncomes(sessionId);
			   System.out.println("IncomeListe ausgeben ReturnCode: " + incListResp.getReturnCode());
			   if(incListResp.getReturnCode() == 200) {
				   ArrayList<IncomeTO> incList= (ArrayList<IncomeTO>) incListResp.getIncomeList();
				   System.out.println("Es sind " + incList.size() + " Händler vorhanden.");
				   sampleIncomeId = incList.get(1).getId();
				   for (IncomeTO i : incList){
					   System.out.println("ID: "+ i.getId() + "  Name: " + i.getName());
				   }
			   }
			   else {
				   System.out.println("Message: " + incListResp.getMessage());
			   }
			   System.out.println("Message: " + incListResp.getMessage());
			   System.out.println("============================================================");
			   System.out.println("Suche Income mit Id " + sampleIncomeId);
			   IncomeResponse incResp = remoteSystem.getIncome(sessionId, sampleIncomeId);
			   System.out.println("IncomeSuchen ReturnCode: " + incResp.getReturnCode());
			   if(incResp.getReturnCode() == 200) {
				   System.out.println("ID: "+ incResp.getIncomeTo().getId() + "  Name: " + incResp.getIncomeTo().getName());
			   }
			   else {
				   System.out.println("Message: " + incResp.getMessage());
			   }
			   System.out.println("Message: " + incResp.getMessage());
			   System.out.println("============================================================");
			   System.out.println("Ändere Income mit Id " + sampleIncomeId);
			   createIncomeHelper(sessionId, sampleIncomeId, "LohnGeändert", 1.00, 2000.00, "Lohn MärzGeändert", dateLong, katId);

			   System.out.println("============================================================");
			   System.out.println("Lösche Income mit Id " + sampleIncomeId);
			   ReturnCodeResponse resp = remoteSystem.deleteIncome(sessionId, sampleIncomeId);
			   System.out.println("Incomelöschen ReturnCode: " + resp.getReturnCode());
			   if(resp.getReturnCode() == 200) {
				   System.out.println("Suche Income mit Id " + sampleIncomeId);
				   IncomeResponse incResp1 = remoteSystem.getIncome(sessionId, sampleIncomeId);
				   System.out.println("Income ReturnCode: " + incResp1.getReturnCode());
			   }
			   else {
				   System.out.println("Message: " + resp.getMessage());
			   }
			   System.out.println("Message: " + resp.getMessage());
			   remoteSystem.logout(sessionId);
			   System.out.println("Emma hat sich abgemeldet.");
			   */
	       }    
	}
	/**
	 * HelferMethode zum anlegen von Incomes
	 * @author Marco
	 * @date 08.06.2015
	 */
	private static void createIncomeHelper (int sessionId, int incomeId, String name,
			double quantity, double amount, String notice,  long receiptDate, int categoryId)  {
		
		System.out.println("Lege Income an. ");
		   IncomeResponse incResp = remoteSystem.createOrUpdateIncome(sessionId, incomeId, name, quantity, amount, notice, receiptDate, categoryId);
		   System.out.println("IncomeAnlegen ReturnCode: " + incResp.getReturnCode());
		   if(incResp.getReturnCode() == 200) {
			   System.out.println("Income angelegt");
			   if(incResp.getIncomeTo() != null) {
				   System.out.println("Income Eigenschaften: ");
				   System.out.println("Name = " + incResp.getIncomeTo().getName());
				   System.out.println("Amount = " + incResp.getIncomeTo().getAmount());
				   System.out.println("Kategorie = " + incResp.getIncomeTo().getCategory().getName());
				   System.out.println("Owner = " + incResp.getIncomeTo().getUser().getUsername());
			   }
			   else {
				   System.out.println ("Income ist gleich null");
			   }
		   }
		   else {
			   System.out.println("Message: " + incResp.getMessage());
		   }
		   System.out.println("Message: " + incResp.getMessage());
		   System.out.println("============================================================");
    }

	
	/**
	 * szenario to test a login an to create, update, get and delete a payments
	 * @author Marco
	 * @date 09.06.2015
	 */ 
	private static void szenarioPayment() {
		System.out.println("============================================================");
	       UserLoginResponse loginResponse = remoteSystem.login("emma", "12345678");
	       if (loginResponse != null & loginResponse.getReturnCode()==200) {
	    	   int sessionId = loginResponse.getSessionId();
			   System.out.println("Emma hat sich angemeldet");
			   System.out.println("LoginReturnCode: " + loginResponse.getReturnCode());
			   
			   System.out.println("============================================================");
			   createPaymentHelper(sessionId, -99, "Volksbank", "12356799", "BIC1234", true);
			   createPaymentHelper(sessionId, -99, "Sparkasse", "DE123456789", "BIC1234", true);
			   createPaymentHelper(sessionId, -99, "Postbank", "123456789", "BIC123456789", true);
			   createPaymentHelper(sessionId, -99, "Comerzbank", "12ADAD356799", "BIC1234DE", true);
			   createPaymentHelper(sessionId, -99, "Dt. Bank", "123ASDAD56799", "BIC1234NL", true);
			   
			   System.out.println("============================================================");
			   int samplePaymentId = 0; //Für spätere Test bei update und get
			   PaymentListResponse payListResp = remoteSystem.getPayments(sessionId);
			   System.out.println("PaymentListe ausgeben ReturnCode: " + payListResp.getReturnCode());
			   if(payListResp.getReturnCode() == 200) {
				   ArrayList<PaymentTO> payList= (ArrayList<PaymentTO>) payListResp.getPaymentList();
				   System.out.println("Es sind " + payList.size() + " Payments vorhanden.");
				   samplePaymentId = payList.get(1).getId();
				   for (PaymentTO p : payList){
					   System.out.println("ID: "+ p.getId() + "  Name: " + p.getName() + "  Number: " + p.getNumber() + "  BIC: " + p.getBic());
				   }
			   }
			   else {
				   System.out.println("Message: " + payListResp.getMessage());
			   }
			   System.out.println("Message: " + payListResp.getMessage());
			   System.out.println("============================================================");
			   System.out.println("Suche Payment mit Id " + samplePaymentId);
			   PaymentResponse payResp = remoteSystem.getPayment(sessionId, samplePaymentId);
			   System.out.println("PaymentSuchen ReturnCode: " + payResp.getReturnCode());
			   if(payResp.getReturnCode() == 200) {
				   System.out.println("ID: "+ payResp.getPaymentTo().getId() + "  Name: " + payResp.getPaymentTo().getName());
			   }
			   else {
				   System.out.println("Message: " + payResp.getMessage());
			   }
			   System.out.println("Message: " + payResp.getMessage());
			   
			   System.out.println("============================================================");
			   System.out.println("Ändere Payment mit Id " + samplePaymentId);
			   createPaymentHelper(sessionId, samplePaymentId, "PaymentÄndern", "number1", "BICaas", false);
			   
			   System.out.println("============================================================");
			   System.out.println("Lösche Payment mit Id " + samplePaymentId);
			   ReturnCodeResponse resp = remoteSystem.deletePayment(sessionId, samplePaymentId);
			   System.out.println("Payment löschen ReturnCode: " + resp.getReturnCode());
			   if(resp.getReturnCode() == 200) {
				   System.out.println("Suche Payment mit Id " + samplePaymentId);
				   PaymentResponse payResp1 = remoteSystem.getPayment(sessionId, samplePaymentId);
				   System.out.println("Payment ReturnCode: " + payResp1.getReturnCode());
			   }
			   else {
				   System.out.println("Message: " + resp.getMessage());
			   }
			   System.out.println("Message: " + resp.getMessage());
			   
			   remoteSystem.logout(sessionId);
			   System.out.println("Emma hat sich abgemeldet.");
			   
	       }

	       
	}
	
	/**
	 * HelferMethode zum anlegen von Payments
	 * @author Marco
	 * @date 08.06.2015
	 */
	private static void createPaymentHelper (int sessionId, int catId, String name, String number, String bic, boolean active) {
		System.out.println("Lege Paymente an. ");
		PaymentResponse payResp = remoteSystem.createOrUpdatePayment(sessionId, catId, name, number, bic, active);
		   System.out.println("PaymentAnlegen ReturnCode: " + payResp.getReturnCode());
		   if(payResp.getReturnCode() == 200) {
			   System.out.println("Payment angelegt");
			   if(payResp.getPaymentTo() != null) {
				   System.out.println("Payment Eigenschaften: ");
				   System.out.println("Name = " + payResp.getPaymentTo().getName());
				   System.out.println("Number = " + payResp.getPaymentTo().getNumber());
				   System.out.println("BIC = " + payResp.getPaymentTo().getBic());
				   System.out.println("Owner = " + payResp.getPaymentTo().getUser().getUsername());
			   }
			   else {
				   System.out.println ("Payment ist gleich null");
			   }
		   }
		   else {
			   System.out.println("Message: " + payResp.getMessage());
		   }
		   System.out.println("Message: " + payResp.getMessage());
		   System.out.println("============================================================");
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
			   
			   System.out.println("============================================================");
			   createCategoryHelper(sessionId, -99, true, true, "Lohn", "notiz", "FFTTZZ");
			   createCategoryHelper(sessionId, 0, true, true, "Supermarkt", "Supermarkt", "FFTTZZ");
			   createCategoryHelper(sessionId, 0, false, true, "Drogerie", "Drogerie", "FFTTZZ");
			   createCategoryHelper(sessionId, 0, false, true, "Einkauf", "Einkaufskategorie", "FFTTZZ");
			   createCategoryHelper(sessionId, 0, false, true, "Kneipe", "Feiern", "FFTTZZ");
			   
			   System.out.println("============================================================");
			   int sampleCategoryId = 0; //Für spätere Test bei update und get
			   CategoryListResponse catListResp = remoteSystem.getCategorys(sessionId);
			   System.out.println("KategorieListe ausgeben ReturnCode: " + catListResp.getReturnCode());
			   if(catListResp.getReturnCode() == 200) {
				   ArrayList<CategoryTO> catList= (ArrayList<CategoryTO>) catListResp.getCategoryList();
				   System.out.println("Es sind " + catList.size() + " Kategorien vorhanden.");
				   sampleCategoryId = catList.get(1).getId();
				   for (CategoryTO c : catList){
					   System.out.println("ID: "+ c.getId() + "  Name: " + c.getName());
				   }
			   }
			   else {
				   System.out.println("Message: " + catListResp.getMessage());
			   }
			   System.out.println("Message: " + catListResp.getMessage());
			   System.out.println("============================================================");
			   System.out.println("Suche Kategory mit Id " + sampleCategoryId);
			   CategoryResponse catResp = remoteSystem.getCategory(sessionId, sampleCategoryId);
			   System.out.println("KategorieSuchen ReturnCode: " + catResp.getReturnCode());
			   if(catResp.getReturnCode() == 200) {
				   System.out.println("ID: "+ catResp.getCategoryTo().getId() + "  Name: " + catResp.getCategoryTo().getName());
			   }
			   else {
				   System.out.println("Message: " + catResp.getMessage());
			   }
			   System.out.println("Message: " + catResp.getMessage());
			   
			   System.out.println("============================================================");
			   System.out.println("Ändere Kategory mit Id " + sampleCategoryId);
			   createCategoryHelper(sessionId, sampleCategoryId, false, true, "EinkaufÄndern", "notiz1", "FFTTZZ");
			   
			   System.out.println("============================================================");
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
			   remoteSystem.logout(sessionId);
			   System.out.println("Emma hat sich abgemeldet.");
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
		   System.out.println("============================================================");
    }
	
	/**
	 * szenario to test a login an to create, update, get and delete a vendor
	 * @author Marco
	 */
	private static void szenarioVendor() {
		   System.out.println("============================================================");
	       UserLoginResponse loginResponse = remoteSystem.login("emma", "12345678");
	       if (loginResponse != null & loginResponse.getReturnCode()==200) {
	    	   int sessionId = loginResponse.getSessionId();
			   System.out.println("Emma hat sich angemeldet");
			   System.out.println("LoginReturnCode: " + loginResponse.getReturnCode());
			   System.out.println("============================================================");
			   createVendorHelper(sessionId, -99, "Rewe", "BILD", "Straße1", "Stadt1", 48691, 22);
			   createVendorHelper(sessionId, -99, "ALDI", "BILD", "Straße2", "Stadt2", 48691, 22);
			   createVendorHelper(sessionId, -99, "LIDL", "BILD", "Straße3", "Stadt3", 48691, 22);
			   System.out.println("============================================================");
			   int sampleVendorId = 0; //Für spätere Test bei update und get
			   VendorListResponse venListResp = remoteSystem.getVendors(sessionId);
			   System.out.println("VendorListe ausgeben ReturnCode: " + venListResp.getReturnCode());
			   if(venListResp.getReturnCode() == 200) {
				   ArrayList<VendorTO> venList= (ArrayList<VendorTO>) venListResp.getVendorList();
				   System.out.println("Es sind " + venList.size() + " Händler vorhanden.");
				   sampleVendorId = venList.get(1).getId();
				   for (VendorTO v : venList){
					   System.out.println("ID: "+ v.getId() + "  Name: " + v.getName());
				   }
			   }
			   else {
				   System.out.println("Message: " + venListResp.getMessage());
			   }
			   System.out.println("Message: " + venListResp.getMessage());
			   System.out.println("============================================================");
			   System.out.println("Suche Vendor mit Id " + sampleVendorId);
			   VendorResponse venResp = remoteSystem.getVendor(sessionId, sampleVendorId);
			   System.out.println("VendorSuchen ReturnCode: " + venResp.getReturnCode());
			   if(venResp.getReturnCode() == 200) {
				   System.out.println("ID: "+ venResp.getVendorTo().getId() + "  Name: " + venResp.getVendorTo().getName());
			   }
			   else {
				   System.out.println("Message: " + venResp.getMessage());
			   }
			   System.out.println("Message: " + venResp.getMessage());
			   System.out.println("============================================================");
			   System.out.println("Ändere Vendor mit Id " + sampleVendorId);
			   createVendorHelper(sessionId, sampleVendorId, "Geändert", "ändereBild", "StraßeGeändert", "StadtGeändert", 48691, 22);
			   System.out.println("============================================================");
			   System.out.println("Lösche Vendor mit Id " + sampleVendorId);
			   ReturnCodeResponse resp = remoteSystem.deleteVendor(sessionId, sampleVendorId);
			   System.out.println("Vendorlöschen ReturnCode: " + resp.getReturnCode());
			   if(resp.getReturnCode() == 200) {
				   System.out.println("Suche Vendor mit Id " + sampleVendorId);
				   VendorResponse venResp1 = remoteSystem.getVendor(sessionId, sampleVendorId);
				   System.out.println("vendor ReturnCode: " + venResp1.getReturnCode());
			   }
			   else {
				   System.out.println("Message: " + resp.getMessage());
			   }
			   System.out.println("Message: " + resp.getMessage());
			   remoteSystem.logout(sessionId);
			   System.out.println("Emma hat sich abgemeldet.");
			   
	       }
	       
	}
	
	/**
	 * HelferMethode zum anlegen von Kategorien
	 * @author Marco
	 * @date 08.06.2015
	 */
	private static void createVendorHelper (int sessionId, int vendorId, String name, String logo, String street, String city, int PLZ, int houseNumber) {
		System.out.println("Lege Vendor an. ");
		   VendorResponse venResp = remoteSystem.createOrUpdateVendor(sessionId, vendorId, name, logo, street, city, PLZ, houseNumber);
		   System.out.println("VendorAnlegen ReturnCode: " + venResp.getReturnCode());
		   if(venResp.getReturnCode() == 200) {
			   System.out.println("Vendor angelegt");
			   if(venResp.getVendorTo() != null) {
				   System.out.println("Vendor Eigenschaften: ");
				   System.out.println("Name = " + venResp.getVendorTo().getName());
				   System.out.println("Logo = " + venResp.getVendorTo().getLogo());
				   System.out.println("Owner = " + venResp.getVendorTo().getUser().getUsername());
			   }
			   else {
				   System.out.println ("Vendor ist gleich null");
			   }
		   }
		   else {
			   System.out.println("Message: " + venResp.getMessage());
		   }
		   System.out.println("Message: " + venResp.getMessage());
		   System.out.println("============================================================");
    }


	/**
	 * Test-Szenario mit Kundenregistrierung 
	 */
	private static void szenarioRegister() {
	   System.out.println();
	   System.out.println("============================================================");
	   System.out.println("Register Szenario:");

       UserLoginResponse loginResponse = remoteSystem.setUser("emma", "12345678", "test@mail.com");
       if (loginResponse.getReturnCode()==0) {
    	   int sessionId = loginResponse.getSessionId();
		   System.out.println("Emma hat sich erfolgreich registriert.");
	       remoteSystem.logout(sessionId);
		   System.out.println("Emma hat sich abgemeldet.");
	   }
       else {
    	   System.out.println(loginResponse.getMessage());
       }       
	}
}

