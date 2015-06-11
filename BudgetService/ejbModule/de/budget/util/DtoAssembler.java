package de.budget.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
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
	
	/**
	 * für interne methodenaufrufe, da this verboten ist in EJB, da man dadurch die Aufgabe des Containers übernimmt
	 * @author Marco
	 */
	private DtoAssembler dtoAssem;
	
	/**
	 * The SessionContext of this EJB
	 * @author Marco
	 */
	@Resource
	private SessionContext ctx;
	
	/**
	 * Methode, die beim der Erzeugung der Bean aufgerufen wird
	 * Initialisiert den DtoAssembler für den "verbotenen This Aufruf"
	 * @author Marco
	 * @date 08.06.2015
	 */
	@PostConstruct
	public void init() {
		dtoAssem = ctx.getBusinessObject(DtoAssembler.class);
	}
	
	public VendorTO makeDto(Vendor vendor) {
		VendorTO dto = new VendorTO();
		dto.setId(vendor.getId());
		dto.setName(vendor.getName());
		dto.setCreateDate(vendor.getCreateDate().getTime());
		dto.setLastChanged(vendor.getLastChanged().getTime());
		dto.setUser(dtoAssem.makeDto(vendor.getUser()));
		dto.setLogo(vendor.getLogo());
		dto.setStreet(vendor.getStreet());
		dto.setPLZ(vendor.getPLZ());
		dto.setHouseNumber(vendor.getHouseNumber());
		dto.setCity(vendor.getCity());
		return dto;
	}
	
	public UserTO makeDto (User user) {

		String username = user.getUserName();
		String password = user.getPassword();
		String email = user.getEmail();
		long createDate = user.getCreateDate().getTime();
		long lastChanged = user.getLastChanged().getTime();
		/*
		 * TODO
		dto.setBasketList(dtoAssem.makeBasketListDto(user.getBaskets()));
		dto.setVendorList(dtoAssem.makeVendorListDto(user.getVendors()));
		dto.setCategoryList(dtoAssem.makeCategoryListDto(user.getCategories()));
		dto.setPaymentList(dtoAssem.makePaymentListDto(user.getPayments()));
		 */
		UserTO dto = new UserTO(username, password, email, createDate, lastChanged, null, null, null, null);
		return dto;
	}
	
	public List<VendorTO> makeVendorListDto(List<Vendor> vendors) {
		ArrayList<VendorTO> dtoList = new ArrayList<>();
		for(Vendor v : vendors) {
			dtoList.add(dtoAssem.makeDto(v));
		}
		return dtoList;
	}
	
	public CategoryTO makeDto(Category category) {
		/*
		int id = category.getId();
		String name = category.getName();
		String notice = category.getNotice();
		Timestamp createDate = category.getCreateDate();
		boolean active = category.isActive();
		boolean income = category.isIncome();
		Timestamp lastChanged= category.getLastChanged();
		UserTO user = dtoAssem.makeDto(category.getUser());		
		CategoryTO dto = new CategoryTO(id, name, notice, active, income,createDate, lastChanged, user);
		*/
		CategoryTO dto = new CategoryTO();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setNotice(category.getNotice());
		dto.setCreateDate(category.getCreateDate().getTime());
		dto.setActive(category.isActive());
		dto.setIncome(category.isIncome());
		dto.setLastChanged(category.getLastChanged().getTime());
		dto.setUser(dtoAssem.makeDto(category.getUser()));
		dto.setColour(category.getColour());
		
		return dto;
	}
	
	public List<CategoryTO> makeCategoryListDto(List<Category> categories) {
		ArrayList<CategoryTO> dtoList = new ArrayList<>();
		for(Category c : categories) {
			dtoList.add(dtoAssem.makeDto(c));
		}
		return dtoList;
	}
	
	public PaymentTO makeDto(Payment payment) {
		PaymentTO dto = new PaymentTO();
		dto.setId(payment.getId());
		dto.setName(payment.getName());
		dto.setNumber(payment.getNumber());
		dto.setBic(payment.getBic());
		dto.setCreateDate(payment.getCreateDate().getTime());
		dto.setActive(payment.isActive());
		dto.setLastChanged(payment.getLastChanged().getTime());
		dto.setUser(dtoAssem.makeDto(payment.getUser()));
		return dto;
	}
	
	public List<PaymentTO> makePaymentListDto(List<Payment> payments) {
		ArrayList<PaymentTO> dtoList = new ArrayList<>();
		for(Payment p : payments) {
			dtoList.add(dtoAssem.makeDto(p));
		}
		return dtoList;
	}
	
	public BasketTO makeDto(Basket basket) {
		BasketTO dto = new BasketTO();
		dto.setId(basket.getId());
		dto.setName(basket.getName());
		dto.setNotice(basket.getNotice());
		dto.setCreateDate(basket.getCreateDate().getTime());
		dto.setAmount(basket.getAmount());
		dto.setLastChanged(basket.getLastChanged().getTime());
		dto.setUser(dtoAssem.makeDto(basket.getUser()));
		dto.setPurchaseDate(basket.getPurchaseDate().getTime());
		dto.setVendor(dtoAssem.makeDto(basket.getVendor()));
		dto.setPayment(dtoAssem.makeDto(basket.getPayment()));
		dto.setItems(dtoAssem.makeItemListDto(basket.getItems()));
		return dto;
	}
	
	public List<ItemTO> makeItemListDto(List<Item> items) {
		ArrayList<ItemTO> dtoList = new ArrayList<>();
		for(Item i : items) {
			dtoList.add(dtoAssem.makeDto(i));
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
		dto.setCreateDate(item.getCreateDate().getTime());
		dto.setReceiptDate(item.getReceiptDate().getTime());
		dto.setLastChanged(item.getLastChanged().getTime());
		dto.setBasketId(item.getBasket().getId());
		dto.setCategoryId(item.getCategory().getId());
		return dto;
	}
	
	public List<BasketTO> makeBasketListDto(List<Basket> baskets) {
		ArrayList<BasketTO> dtoList = new ArrayList<>();
		for(Basket b : baskets) {
			dtoList.add(dtoAssem.makeDto(b));
		}
		return dtoList;
	}
	
	public List<IncomeTO> makeIncomeListDto(List<Income> incomes) {
		ArrayList<IncomeTO> dtoList = new ArrayList<>();
		for(Income i : incomes) {
			dtoList.add(dtoAssem.makeDto(i));
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
		dto.setReceiptDate(income.getReceiptDate().getTime());
		dto.setUser(dtoAssem.makeDto(income.getUser()));
		dto.setCategory(dtoAssem.makeDto(income.getCategory()));
		return dto;
	}
}
