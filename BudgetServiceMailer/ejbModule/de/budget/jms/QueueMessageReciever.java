package de.budget.jms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;


/**
 * 
 * MessageReciever Class liest die BudgetOUteput Queue und versendet nachrichten die sich darin befinden
 * 
 * @author Moritz
 * @date 17.06.15
 * 
 */

@MessageDriven(
		activationConfig = {  
				 @ActivationConfigProperty(
				      propertyName = "destination",
				      propertyValue = "java:/jms/queue/BudgetOutput")
				  })
public class QueueMessageReciever implements MessageListener {
	
	@Resource(name = "java:jboss/mail/BudgetMail")
	private Session mailSession;
	
	//Email Absender vorerst als Konstante 
	private static final String DEFAULT_SENDER = "robotbudget@gmail.com"; 


	private static final Logger logger = Logger.getLogger(QueueMessageReciever.class);
	
	@Override
	public void onMessage(Message message) {
		String eMail = "";
		String Betreff = "";
		String Nachricht = "";
		String UserName = "";
		
		
		try {
		
			if(message instanceof TextMessage) {
				TextMessage body = (TextMessage) message;
				Nachricht = body.getText();
			}
			else if(message instanceof ObjectMessage) {
				//User user = message.getBody(User.class);
				Object obj = message.getBody(null);
			}
			else if(message instanceof MapMessage) {
				MapMessage mapMessage = (MapMessage) message;
				eMail = mapMessage.getString( "email" );
				Betreff = mapMessage.getString( "subject" );
				Nachricht = mapMessage.getString( "body" );
				UserName = mapMessage.getString( "user" );
			}
			
			sendMail(eMail,Betreff,Nachricht,UserName);
			
			logger.info("BudgetMailer| versendete Nachricht an: "+ UserName + "Inhalt:" + Nachricht);
		}
		catch(MessagingException me) {
			logger.error("BudgetMailer| JMSFehler : " +me.getMessage());
		}
		catch(JMSException e) {
			e.printStackTrace();
			logger.error("BudgetMailer| JMSFehler : "+ e.getErrorCode() + "message:" +e.getMessage());
		}
			
		
		
	}
	
	
	public void sendMail(String emailTO,String subject, String body, String userName) throws MessagingException {
		String mailText = "";
		if(emailTO != null) {
			
			//Falls von oben keine Nachricht mitübergeben wurde bauen wir eine
			if(body.equals(null)) {
				switch(subject){
				case("Erfolgreiche-Registrierung"):
				mailText= "Herzlich Willkommen " + userName + ",<br><br> wir hoffen Du hast viel Spaß mit dieser App!";
				break;
				}
				
			}
			else
			{
				mailText= "Herzlich Willkommen " + userName + ",<br><br> ";
				mailText =  mailText + body;
			}
			
			javax.mail.Message mail = new MimeMessage(mailSession);
			
			mail.setRecipients(
					javax.mail.Message.RecipientType.TO, 
					InternetAddress.parse(emailTO));
			
			mail.setFrom(
					InternetAddress.parse(DEFAULT_SENDER)[0]);
			
			mail.setSubject("Budget|"+subject);
			
			
			
			
			//Hier wäre auch HTML denkbar
			mail.setText("Hallo,\b dies ist eine Testnachricht\b \b Inhalt:" + body + ":EndeInhalt");
			Transport.send(mail);
			
		}
		
	}
	
	

}
