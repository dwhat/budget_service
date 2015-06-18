package de.budget.jms;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;


/**
 * 
 * MessageReciever Class liest die BudgetQueue und versendet nachrichten die sich darin befinden
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
	
	//Email Absender als Konstante,da Mailkonto fix
	private static final String DEFAULT_SENDER = "robotbudget@gmail.com"; 

	private static final Logger logger = Logger.getLogger(QueueMessageReciever.class);
	
	@Resource(name = "java:jboss/mail/BudgetMail")
	private Session mailSession;
	
	@Override
	public void onMessage(Message message) {
		String eMail = "";
		String Betreff = "";
		String Nachricht = "";
		String UserName = "";
		
		//Im moment sind wir in der Lage mehrere Messages zu listen
		try {
			if(message != null) {
				if(message instanceof MapMessage) {
					MapMessage mapMessage = (MapMessage) message;
					
					eMail = mapMessage.getString( "email" );
					Betreff = mapMessage.getString( "subject" );
					Nachricht = mapMessage.getString( "body" );
					UserName = mapMessage.getString( "user" );
					
					if(sendMail(eMail,Betreff,Nachricht,UserName)) {
						logger.info("BudgetMailer| versendete Nachricht an: "+ UserName + "Inhalt:" + Nachricht);
					}
					//Der Queue mitteilen das die Nachricht gelesen wurde, um erneute Zustellung zu vermeiden.
					//message.acknowledge();
				}
			}
		}
		catch(MessagingException me) {
			logger.error("BudgetMailer| JMSFehler : " +me.getMessage());
		}
		catch(JMSException e) {
			//e.printStackTrace();
			logger.error("BudgetMailer| JMSFehler : "+ e.getErrorCode() + "message:" +e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * wenn Andere MessageArten gelesen werden sollen aktivieren 
	 *  andere Messagetypen erstemal ignorieren 
	if(message instanceof TextMessage) {
		TextMessage body = (TextMessage) message;
		Nachricht = body.getText();
	}
	else if(message instanceof ObjectMessage) {
		//User user = message.getBody(User.class);
		Object obj = message.getBody(null);
	}
	*/
	
	
	/**
	 * EmailVersand 
	 * 
	 * @author Moritz
	 * @date 18.06.2015
	 * 
	 * @param emailTO
	 * @param subject
	 * @param body
	 * @param userName
	 * @return
	 * @throws MessagingException
	 */
	public boolean sendMail(String emailTO,String subject, String body, String userName) throws MessagingException {
		boolean result = false;
		String mailTextStart = "Herzlich Willkommen " + userName + ",<br><br>";
		String message;
		String mailTextEnd = "<br><br>Dein Budget-Team...";
		
		
		if(emailTO != null) {
			//Falls von oben keine Nachricht mitübergeben wurde bauen wir eine
			if(body != null) {
				message = mailTextStart + body + mailTextEnd;
			}
			else
			{
				message = mailTextStart + "danke dass du unsere App nutzt. Viel Spaß damit !" + mailTextEnd;
			}
			
			javax.mail.Message mail = new MimeMessage(mailSession);
			
			mail.setRecipients(
					javax.mail.Message.RecipientType.TO, 
					InternetAddress.parse(emailTO));
			
			mail.setFrom(
					InternetAddress.parse(DEFAULT_SENDER)[0]);
			
			mail.setSubject("Budget|"+subject);
		
			//Um HTML zu ermöglichen 
			mail.setContent(message, "text/html");
			//mail.setText(message);
			Transport.send(mail);
			result = true;
		}
		return result;
	}
}
