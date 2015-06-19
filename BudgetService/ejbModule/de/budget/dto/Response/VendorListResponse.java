package de.budget.dto.Response;

import java.util.List;

import de.budget.dto.VendorTO;

/**
 * Klasse für eine Liste von VendorTO Objecten als Antwort auf Anfragen
 * @author Marco
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
