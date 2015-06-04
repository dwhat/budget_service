
package de.budget.onlinebudget;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for basketListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="basketListResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="basketList" type="{http://onlinebudget.budget.de/}basketTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basketListResponse", propOrder = {
    "basketList"
})
public class BasketListResponse
    extends ReturnCodeResponse
{

    @XmlElement(nillable = true)
    protected List<BasketTO> basketList;

    /**
     * Gets the value of the basketList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the basketList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBasketList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BasketTO }
     * 
     * 
     */
    public List<BasketTO> getBasketList() {
        if (basketList == null) {
            basketList = new ArrayList<BasketTO>();
        }
        return this.basketList;
    }

}
