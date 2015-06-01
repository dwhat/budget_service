package de.budget.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.budget.dto.BasketTO;
import de.budget.dto.CategoryTO;
import de.budget.dto.IncomeTO;
import de.budget.dto.ItemTO;
import de.budget.dto.PaymentTO;
import de.budget.dto.UserTO;
import de.budget.dto.VendorTO;
import de.budget.entities.Basket;
import de.budget.entities.Category;
import de.budget.entities.Income;
import de.budget.entities.Item;
import de.budget.entities.Payment;
import de.budget.entities.User;
import de.budget.entities.Vendor;

/**
 * Klasse zum Erstellen von DTOs, die zum Datentransfer benötigt werden
 * 
 * DTO's werden von den vorherigen Entities gebildet. 
 * 
 * @date 19.05.2015
 * @author Marco
 *
 */
@Stateless
public class DtoAssembler {
	
	
	public VendorTO makeDto(Vendor vendor) {
		VendorTO dto = new VendorTO();
		dto.setId(vendor.getId());
		dto.setName(vendor.getName());
		dto.setCreateDate(vendor.getCreateDate());
		dto.setLastChanged(vendor.getLastChanged());
		dto.setUser(makeDto(vendor.getUser()));
		dto.setLogo(vendor.getLogo());
		return dto;
	}
	
	public UserTO makeDto (User user) {
		UserTO dto = new UserTO();
		dto.setUsername(user.getUserName());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setCreateDate(user.getCreateDate());
		dto.setLastChanged(user.getLastChanged());
		dto.setBasketList(makeBasketListDto(user.getBaskets()));
		dto.setVendorList(makeVendorListDto(user.getVendors()));
		dto.setCategoryList(makeCategoryListDto(user.getCategories()));
		dto.setPaymentList(makePaymentListDto(user.getPayments()));
		return dto;
	}
	
	public List<VendorTO> makeVendorListDto(List<Vendor> vendors) {
		ArrayList<VendorTO> dtoList = new ArrayList<>();
		for(Vendor v : vendors) {
			dtoList.add(makeDto(v));
		}
		return dtoList;
	}
	
	public CategoryTO makeDto(Category category) {
		CategoryTO dto = new CategoryTO();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setNotice(category.getNotice());
		dto.setCreateDate(category.getCreateDate());
		dto.setActive(category.isActive());
		dto.setIncome(category.isIncome());
		dto.setLastChanged(category.getLastChanged());
		dto.setUser(makeDto(category.getUser()));
		return dto;
	}
	
	public List<CategoryTO> makeCategoryListDto(List<Category> categories) {
		ArrayList<CategoryTO> dtoList = new ArrayList<>();
		for(Category c : categories) {
			dtoList.add(makeDto(c));
		}
		return dtoList;
	}
	
	public PaymentTO makeDto(Payment payment) {
		PaymentTO dto = new PaymentTO();
		dto.setId(payment.getId());
		dto.setName(payment.getName());
		dto.setNumber(payment.getNumber());
		dto.setBic(payment.getBic());
		dto.setCreateDate(payment.getCreateDate());
		dto.setActive(payment.isActive());
		dto.setLastChanged(payment.getLastChanged());
		dto.setUser(makeDto(payment.getUser()));
		return dto;
	}
	
	public List<PaymentTO> makePaymentListDto(List<Payment> payments) {
		ArrayList<PaymentTO> dtoList = new ArrayList<>();
		for(Payment p : payments) {
			dtoList.add(makeDto(p));
		}
		return dtoList;
	}
	
	public BasketTO makeDto(Basket basket) {
		BasketTO dto = new BasketTO();
		dto.setId(basket.getId());
		dto.setNotice(basket.getNotice());
		dto.setCreateDate(basket.getCreateDate());
		dto.setAmount(basket.getAmount());
		dto.setLastChanged(basket.getLastChanged());
		dto.setUser(makeDto(basket.getUser()));
		dto.setPurchaseDate(basket.getPurchaseDate());
		dto.setVendor(makeDto(basket.getVendor()));
		dto.setPayment(makeDto(basket.getPayment()));
		dto.setItems(makeItemListDto(basket.getItems()));
		return dto;
	}
	
	public List<ItemTO> makeItemListDto(List<Item> items) {
		ArrayList<ItemTO> dtoList = new ArrayList<>();
		for(Item i : items) {
			dtoList.add(makeDto(i));
		}
		return dtoList;
	}
	
	public ItemTO makeDto(Item item) {
		ItemTO dto = new ItemTO();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setQuantity(item.getQuantity());
		dto.setPrice(item.getPrice());
		dto.setNotice(item.getNotice());
		dto.setPeriod(item.getPeriod());
		dto.setCreateDate(item.getCreateDate());
		dto.setLaunchDate(item.getLaunchDate());
		dto.setFinishDate(item.getFinishDate());
		dto.setLastChanged(item.getLastChanged());
		dto.setBasket(makeDto(item.getBasket()));
		dto.setCategory(makeDto(item.getCategory()));
		return dto;
	}
	
	public List<BasketTO> makeBasketListDto(List<Basket> baskets) {
		ArrayList<BasketTO> dtoList = new ArrayList<>();
		for(Basket b : baskets) {
			dtoList.add(makeDto(b));
		}
		return dtoList;
	}
	
	public List<IncomeTO> makeIncomeListDto(List<Income> incomes) {
		ArrayList<IncomeTO> dtoList = new ArrayList<>();
		for(Income i : incomes) {
			dtoList.add(makeDto(i));
		}
		return dtoList;
	}
	
	public IncomeTO makeDto(Income income) {
		IncomeTO dto = new IncomeTO();
		dto.setId(income.getId());
		dto.setName(income.getName());
		dto.setQuantity(income.getQuantity());
		dto.setAmount(income.getAmount());
		dto.setNotice(income.getNotice());
		dto.setPeriod(income.getPeriod());
		dto.setCreateDate(income.getCreateDate());
		dto.setLaunchDate(income.getLaunchDate());
		dto.setFinishDate(income.getFinishDate());
		dto.setLastChanged(income.getLastChanged());
		dto.setUser(makeDto(income.getUser()));
		dto.setCategory(makeDto(income.getCategory()));
		return dto;
	}
}
