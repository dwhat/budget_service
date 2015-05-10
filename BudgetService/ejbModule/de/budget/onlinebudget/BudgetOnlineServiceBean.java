package de.budget.onlinebudget;

import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.budget.common.BudgetOnlineService;
import de.budget.common.Customer;

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

	@Override
	public Customer getCustomer(int customerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCustomer(int customerID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getVendor(int vendorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createVendor(Customer vendor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateVendor(Customer vendor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteVendor(int vendorID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getPayment(int paymentID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPayment(Customer payment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePayment(Customer payment) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePayment(int paymentID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getCategory(int categoryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createCategory(Customer category) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCategory(Customer category) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCategory(int categoryID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getBasket(int basketID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createBasket(Customer basket) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBasket(Customer basket) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBasket(int basketID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Integer, Integer> getChart(int customerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBalance(int customerID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createIncome(Map<Integer, Customer> incomes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateIncome(Customer income, int incomeID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteIncome(int incomeID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createLoss(Map<Integer, Customer> losses) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateLoss(Customer loss, int lossID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteLoss(int lossID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
