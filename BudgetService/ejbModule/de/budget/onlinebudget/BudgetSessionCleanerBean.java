package de.budget.onlinebudget;

import java.sql.Timestamp;
import java.util.List;




import javax.ejb.EJB;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;


import org.jboss.logging.Logger;
import de.budget.dao.BudgetOnlineDAOLocal;
import de.budget.entities.BudgetSession;


/**
 * TimerBean, die automatisch alte BudgetSessions aufräumt
 * 
 * @author Moritz
 *
 */
@Singleton
@Startup
public class BudgetSessionCleanerBean {
	
	private static final Logger logger = Logger.getLogger(BudgetSessionCleanerBean.class);
	
	@EJB
	private BudgetOnlineDAOLocal dao;
	
	@Schedule(hour="00",minute="00")
	private void checkOldBudgetSessions() 
	{
		try {
			Timestamp date = new Timestamp(System.currentTimeMillis()-((25 * 60 * 60 * 1000)*2));
			List<BudgetSession> sessions =dao.getOldSessions(date);
			if(sessions.size()!=0){
				for (BudgetSession b : sessions) {
					dao.closeSession(b.getId());
				}
				logger.info("TIMERBEAN | Alte Sessions:" + sessions.size());
			}
			else
			{
				logger.info("TIMERBEAN | Keine Alten Sessions");
			}
		}
		catch(IllegalArgumentException e) {
			logger.error("TIMERBEAN | IllegalArgumentException- " + e.getMessage());
		}
		catch(Exception e) {
			logger.error("TIMERBEAN | Exception- " + e.getMessage());
		}
	}

}
