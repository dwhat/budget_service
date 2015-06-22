package de.budget.onlinebudget;

import java.util.concurrent.Future;

//import javax.annotation.Resource;
import javax.ejb.AsyncResult;
//import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.jms.ConnectionFactory;
//import javax.jms.JMSContext;
//import javax.jms.Queue;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import de.budget.common.ReportGenerator;
//import de.budget.dao.BudgetOnlineDAOLocal;
import de.budget.entities.BudgetSession;
//import de.budget.entities.User;


// ALLLES ERSTMAL AUSKOMMENTIERT !!!!

/**
 * Bean Implemenation for SenderInterface
 * 
 * @author Moritz
 *
 */
@Stateless
@Remote(ReportGenerator.class)
public class ReportGeneratorBean implements ReportGenerator {
	
	
	/**
	 * EJB zur Abfrage von Datens�tzen
	 * Referenz auf die EJB wird per Dependency Injection gef�llt. 
	 
	@EJB(beanName = "BudgetOnlineDAO", beanInterface = de.budget.dao.BudgetOnlineDAOLocal.class)
	private BudgetOnlineDAOLocal dao;
	*/
	
	/**
	 * EJB zur Beauftragung des Nachrichtenversand
	 * Referenz auf die EJB wird per Dependency Injection gef�llt.
	 */
	// @ EJB
	//private QueueMessageSender outputSender;
	
	
	
	@Override
	public Future<Boolean> build(BudgetSession session) {
		boolean result = false;
		//String betreff = "�bersichtsreport";
		//String message = "";
		//User user = this.dao.findUserByName(session.getUsername());
		
		// hier userAufrufe f�r die Anzahl der Items Baskets etc 
		// alles sch�n mit html in message packen und weg damit 
		
		//if daten holen erfolgreich result = true
		
		//outputSender.sendMapMessage(user.getEmail(), betreff, message, user.getUserName());	
		
	
		return new AsyncResult<Boolean>(result);
	}


}

