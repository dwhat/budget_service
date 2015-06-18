package de.budget.common;

import java.util.concurrent.Future;

import de.budget.entities.BudgetSession;

/**
 * Asynchron Report Generator 
 * @author Marco
 *
 */
public interface ReportGenerator {
	
	/**
	 * Asynchron Report Generator
	 * 
	 * @author Marco
	 * @param subject
	 * @param body
	 * @return
	 */
	public Future<Boolean> build(BudgetSession session);

}
