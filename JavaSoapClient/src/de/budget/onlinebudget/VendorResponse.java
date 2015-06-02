
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for vendorResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vendorResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="vendorTo" type="{http://onlinebudget.budget.de/}vendorTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vendorResponse", propOrder = {
    "vendorTo"
})
public class VendorResponse
    extends ReturnCodeResponse
{

    protected VendorTO vendorTo;

    /**
     * Gets the value of the vendorTo property.
     * 
     * @return
     *     possible object is
     *     {@link VendorTO }
     *     
     */
    public VendorTO getVendorTo() {
        return vendorTo;
    }

    /**
     * Sets the value of the vendorTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorTO }
     *     
     */
    public void setVendorTo(VendorTO value) {
        this.vendorTo = value;
    }

}
