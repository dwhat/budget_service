
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for itemResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itemResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="itemTo" type="{http://onlinebudget.budget.de/}itemTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemResponse", propOrder = {
    "itemTo"
})
public class ItemResponse
    extends ReturnCodeResponse
{

    protected ItemTO itemTo;

    /**
     * Gets the value of the itemTo property.
     * 
     * @return
     *     possible object is
     *     {@link ItemTO }
     *     
     */
    public ItemTO getItemTo() {
        return itemTo;
    }

    /**
     * Sets the value of the itemTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemTO }
     *     
     */
    public void setItemTo(ItemTO value) {
        this.itemTo = value;
    }

}
