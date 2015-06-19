package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.VendorTO;

/**
 * @date 19.05.2015
 * @author Marco
 *	Klasse für eine Liste von VendorTO Objecten als Antwort auf Anfragen
 */
public class VendorListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = -5754928488884226775L;

	private List<VendorTO> vendorList;
		
	/**
	 * DefaultConstructor
	 */
	public VendorListResponse() {

	}

	public List<VendorTO> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<VendorTO> vendorList) {
		this.vendorList = vendorList;
	}

}
