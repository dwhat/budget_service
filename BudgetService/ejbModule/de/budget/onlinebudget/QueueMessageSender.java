package de.budget.onlinebudget;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
//import javax.jms.TextMessage;
//import javax.jms.ObjectMessage;

import org.jboss.logging.Logger;




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
	
	  private static final Logger logger = Logger.getLogger(QueueMessageSender.class);
	  
	  @Inject
	  private JMSContext context;

	  @Resource(mappedName="java:/JmsXA")
	  private ConnectionFactory jmsFactory;
	  
	  @Resource(mappedName="java:/jms/queue/BudgetOutput")
	  private Queue outputQueue;
	  
	  
	  /**
	   * sendTextMessage
	   * Funktion überholt nutzten nun MapMessage
	   * 
	   * @author Marco
	   * 
	   * Sends a Message with the letter text to the output queue,
	   * assuming that this causes the letter to be processed and printed.
	   * @param letter
	  *
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
	  */
	  
	  /**
	   * sendObjectMessage
	   * Objekte in BudgetQueue schreiben
	   * 
	   * @author Moritz
	   * @param user
	   
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
	  */
	  
	  /*
	   * MapMessage für StandardMailversand.
	   * Aufrufen um Email nachricht in Queue einzustellen.
	   * 
	   * 
	   */
	  public void sendMapMessage(String emailTO,String subject, String body, String userName) {
			try {
				MapMessage mapMsg = context.createMapMessage();
			
				mapMsg.setString( "email",   emailTO );
				mapMsg.setString( "subject", subject );
				mapMsg.setString( "body",   body );
				mapMsg.setString( "user",   userName );
				//logger.info("QueueMessageSender| MapMessage Inhalt : " + mapMsg.getString("email"));
				//logger.info("QueueMessageSender| MapMessage : " + mapMsg.toString());
				
				//Nachricht in Queue einstellen 
				context.createProducer().send(outputQueue, mapMsg);
		  }
		  catch (JMSException e) {
			//e.printStackTrace();
			logger.error("QueueMessageSender| JMSFehler : " +e.getMessage());
		} 
	  }
}
