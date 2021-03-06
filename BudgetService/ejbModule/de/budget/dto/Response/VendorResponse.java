package de.budget.dto.Response;

import de.budget.dto.VendorTO;


/**
 * Class for the Vendor response
 * @author Marco
 */
public class VendorResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
		
	private VendorTO vendorTo;

	/**
	* Default Constructor
	*/
	public VendorResponse() {
			
	}

	/**
	 * @return the vendorTo
	 */
	public VendorTO getVendorTo() {
		return vendorTo;
	}

	/**
	 * @param vendorTo the vendorTo to set
	 */
	public void setVendorTo(VendorTO vendorTo) {
		this.vendorTo = vendorTo;
	}
}
