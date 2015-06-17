package de.budget.onlinebudget;

import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.budget.common.ReportSender;
import de.budget.entities.User;

/**
 * Bean Implemenation for SenderInterface
 * 
 * @author Moritz
 *
 */
@Stateless
@Remote(ReportSender.class)
public class ReportSenderBean implements ReportSender {
	
	/*
  @Inject
  private JMSContext context;

  @Resource(mappedName="java:/JmsXA")
  private ConnectionFactory jmsFactory;
  
  @Resource(mappedName="java:/jms/queue/BudgetOutput")
  private Queue outputQueue;
*/

	@Override
	public Future<Boolean> send(String subject, String body) {
		
		 boolean result = false;
		 /*
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
		}*/
		return new AsyncResult<Boolean>(result);
	}

}

