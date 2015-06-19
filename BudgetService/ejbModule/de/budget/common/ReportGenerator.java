package de.budget.common;

import java.util.concurrent.Future;

import de.budget.entities.BudgetSession;


//ALLLES ERSTMAL AUSKOMMENTIERT !!!!

/**
 * Asynchron Report Generator 
 * @author Marco
 *
 */
public interface ReportGenerator {
	
	/**
	 * Asynchron Report Generator
	 * 
	 * @return boolean
	 */
	public Future<Boolean> build(BudgetSession session);

}
