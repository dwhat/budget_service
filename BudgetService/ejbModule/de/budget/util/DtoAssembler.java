package de.budget.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.budget.dto.VendorTO;
import de.budget.entities.Vendor;

/**
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
	
	public List<VendorTO> makeDto(List<Vendor> vendors) {
		ArrayList<VendorTO> dtoList = new ArrayList<>();
		for(Vendor v : vendors) {
			dtoList.add(makeDto(v));
		}
		return dtoList;
	}
}
