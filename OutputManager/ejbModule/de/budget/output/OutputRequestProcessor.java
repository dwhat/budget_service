package de.budget.output;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;


/**
 * 
 * Message-Driven Bean implementation class for: OutputRequestProcessor
 * @author Marco
 *
 */
@MessageDriven(
		activationConfig = {  
			 @ActivationConfigProperty(
			      propertyName = "destinationType",
			      propertyValue = "javax.jms.Queue"),
			 @ActivationConfigProperty(
			      propertyName = "destination",
			      propertyValue = "java:/jms/queue/BudgetOutput"),
			 @ActivationConfigProperty(
			      propertyName = "messageSelector",
			      propertyValue = "DocType LIKE 'Letter'") })
public class OutputRequestProcessor implements MessageListener {

	private static final Logger logger = Logger.getLogger(OutputRequestProcessor.class);
	//TODO
	@Override
	public void onMessage(Message message) {
       try {
    	  //Address[] addresses = message.getJMS
    	  TextMessage msg = (TextMessage) message;
          logger.info("Received message from queue/BankingOutput: " + msg.getText());
       }
       catch (JMSException e) {
            throw new EJBException(e);
       }
    }

}