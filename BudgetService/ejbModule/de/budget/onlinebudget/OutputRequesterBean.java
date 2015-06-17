package de.budget.onlinebudget;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.jms.ConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;


/*Imports for Mailing
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.jms.Destination;
import de.budget.entities.User;
import javax.inject.Inject;
import javax.mail.MessagingException;
*/
/**
 * Session Bean implementation class OutputRequesterBean
 */
@Stateless
@LocalBean
public class OutputRequesterBean {

	  @Resource(mappedName="java:/JmsXA")
	  private ConnectionFactory jmsFactory;
	  
	  @Resource(mappedName="java:/jms/queue/BudgetOutput")
	  private Queue outputQueue;
	  
	  
	  /**
	   * Sends a Message with the letter text to the output queue,
	   * assuming that this causes the letter to be processed and printed.
	   * @param letter
	  **/
	  public void printLetter(String letter) {
		try (JMSContext context = jmsFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
			TextMessage message = context.createTextMessage();
			//message.set
			message.setStringProperty("DocType", "Letter");
			message.setText(letter);
			context.createProducer().send(outputQueue, message);
		}
		catch (JMSException e) {
			// TODO replace with output to logging framework			
			e.printStackTrace();
		}  
	  }
	  
	
	
	/*
	 * 
	 * MoritzPart -> versuch, jedoch abgewandert in MessageSender
	  
	  @Inject
	  private JMSContext context;
		
	  @Resource(mappedName="java:global/jms/BudgetOutputQueue")
	  private Destination budgetDestination;
	  
	  
	  //Injezieren der Mailsession vom Applikationserver
	  @Resource(name = "java:jboss/mail/BudgetMail")
	  private javax.mail.Session session;
	  
	  
	  public void sendMessage(String message) {
		  
		 if(message != null) {
		  
			 try {
				 
				 //User email laden 
				 
				String email = null;
				
				javax.mail.Message mail = new MimeMessage(session);
				
				mail.setRecipients(
						javax.mail.Message.RecipientType.TO, 
						InternetAddress.parse(email));
				
				mail.setSubject("Budget-Email-Notifcation");
				//Hier wäre auch HTML denkbar
				mail.setText("Hallo,\b dies ist eine Testnachricht\b \b " + message);
				
				Transport.send(mail);
				
				 
				
				 
			 
			  TextMessage textmessage = context.createTextMessage();
			  
			  textmessage.setStringProperty("DocType", "TestNachricht");
			  textmessage.setText("Nachricht" + message);
			  
			  context.createProducer().send(budgetDestination,	textmessage);
			  
			  
			 }
			 catch(JMSException e)
			 {
				 e.printStackTrace();
			 }
			 catch(AddressException ae)
			 {
				 ae.printStackTrace();
			 }
			 catch(MessagingException me) {
				 me.printStackTrace();
			 }
		 }
		  
	  }
	  
	  */

}
