package de.budget.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.budget.dto.CategoryTO;
import de.budget.dto.VendorTO;
import de.budget.entities.Category;
import de.budget.entities.Vendor;

/**
 * Klasse zum erstellen von DTOs
 * @date 19.05.2015
 * @author Marco
 *
 */
@Stateless
public class DtoAssembler {

	public VendorTO makeDto(Vendor vendor){
		VendorTO dto = new VendorTO();
		dto.setId(vendor.getId());
		dto.setName(vendor.getName());
		dto.setCreateDate(vendor.getCreateDate());
		dto.setLastChanged(vendor.getLastChanged());
		dto.setUser(vendor.getUser().getUserName());
		dto.setLogo(vendor.getLogo());
		return dto;
	}
	
	public List<VendorTO> makeVendorListDto(List<Vendor> vendors) {
		ArrayList<VendorTO> dtoList = new ArrayList<>();
		for(Vendor v : vendors) {
			dtoList.add(makeDto(v));
		}
		return dtoList;
	}
	
	public CategoryTO makeDto(Category category){
		CategoryTO dto = new CategoryTO();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setNotice(category.getNotice());
		dto.setCreateDate(category.getCreateDate());
		dto.setActive(category.isActive());
		dto.setIncome(category.isIncome());
		dto.setLastChanged(category.getLastChanged());
		dto.setUser(category.getUser().getUserName());
		return dto;
	}
	
	public List<CategoryTO> makeCategoryListDto(List<Category> categories) {
		ArrayList<CategoryTO> dtoList = new ArrayList<>();
		for(Category c : categories) {
			dtoList.add(makeDto(c));
		}
		return dtoList;
	}
}
