
package de.budget.onlinebudget;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vendorListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vendorListResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="vendorList" type="{http://onlinebudget.budget.de/}vendorTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vendorListResponse", propOrder = {
    "vendorList"
})
public class VendorListResponse
    extends ReturnCodeResponse
{

    @XmlElement(nillable = true)
    protected List<VendorTO> vendorList;

    /**
     * Gets the value of the vendorList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vendorList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVendorList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VendorTO }
     * 
     * 
     */
    public List<VendorTO> getVendorList() {
        if (vendorList == null) {
            vendorList = new ArrayList<VendorTO>();
        }
        return this.vendorList;
    }

}
