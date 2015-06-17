package de.budget.onlinebudget;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

import de.budget.entities.User;


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
 * Bean die unsere BudgetQueue befüllt, wenn Emails versendet werden sollen. 
 * 
 * @author Moritz
 * 
 * Session Bean implementation class OutputRequesterBean
 */
@Stateless
@LocalBean
public class QueueMessageSender {

	  @Resource(mappedName="java:/JmsXA")
	  private ConnectionFactory jmsFactory;
	  
	  @Resource(mappedName="java:/jms/queue/BudgetOutput")
	  private Queue outputQueue;
	  
	  
	  /**
	   * Funktion überholt nutzten nun MapMessage
	   * 
	   * @author Marco
	   * 
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
			// TODO evtl noch Logging hier einbauen 			
			e.printStackTrace();
		}  
	  }
	  
	  /**
	   * 
	   * Objekte in BudgetQueue schreiben
	   * 
	   * @author Moritz
	   * @param user
	   */
	  public void sendObjectMessage(User user) {
		  try (JMSContext context = jmsFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
			ObjectMessage objMessage = context.createObjectMessage();
			objMessage.setObject(user);
			
			context.createProducer().send(outputQueue, objMessage);
		  }
		  catch (JMSException e) {
			// TODO evtl noch Logging hier einbauen 			
			e.printStackTrace();
		} 
		  
	  }
	  
	  
	  /*
	   * Aufrufen um Email nachricht in Queue einzustellen.
	   * 
	   * @author Moritz
	   * @date 17.06.15
	   * 
	   */
	  public void sendMapMessage(String emailTO,String subject, String body, String userName) {
		  try (JMSContext context = jmsFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)){
			MapMessage mapMessage = context.createMapMessage();
			mapMessage.setString( "email",   emailTO );
			mapMessage.setString( "subject", subject );
			mapMessage.setString( "body",   body );
			mapMessage.setString( "user",   userName );
			
			context.createProducer().send(outputQueue, mapMessage);
		  }
		  catch (JMSException e) {
			e.printStackTrace();
			//TODO evtl noch Logging hier einbauen 
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
