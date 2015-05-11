package de.budget.dao;

//Lokales Interface das die Operationen erleichtert 
// wie zb findUser // createSession etc

import javax.ejb.Local;

import de.budget.entities.User;


@Local
public interface BudgetOnlineDAOLocal {
	
	public User findUserByName(String userName);
	
	public int createSession(User userObject);

}
