package de.budget.onlinebudget;

import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.budget.common.BudgetOnlineService;
import de.budget.entities.User;

@Stateless
@Remote(BudgetOnlineService.class)
public class BudgetOnlineServiceBean implements BudgetOnlineService {

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

}
