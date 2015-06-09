package de.budget.onlinebudget;

import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.budget.common.MessageSender;
import de.budget.entities.User;

/**
 * Bean Implemenation for SenderInterface
 * 
 * @author Moritz
 *
 */
@Stateless
@Remote(MessageSender.class)
public class MessageSenderBean implements MessageSender {
	
	@Resource(name = "java:jboss/mail/BudgetMail")
	private Session mailSession;
	
	//Email Absender vorerst als Konstante 
	private static final String DEFAULT_SENDER = "robot@budget.de"; 

	@Override
	public Future<Boolean> send(String subject, String body, User user) {
		boolean result = false;
		String email = null;
		email = user.getEmail();
		
		if(email != null) {
			javax.mail.Message mail = new MimeMessage(mailSession);
			
			try {
				mail.setRecipients(
						javax.mail.Message.RecipientType.TO, 
						InternetAddress.parse(email));
				
				mail.setFrom(
						InternetAddress.parse(DEFAULT_SENDER)[0]);
				
				mail.setSubject("Budget-Email-Notifcation");
				//Hier wäre auch HTML denkbar
				mail.setText("Hallo,\b dies ist eine Testnachricht\b \b Inhalt:" + body);
				Transport.send(mail);
				
				result = true;
			}
			catch(MessagingException me) {
				me.printStackTrace();
			}
		}
		return new AsyncResult<Boolean>(result);
	}

}
