
package de.budget.onlinebudget;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for amountListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="amountListResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="amountList" type="{http://onlinebudget.budget.de/}amountTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "amountListResponse", propOrder = {
    "amountList"
})
public class AmountListResponse
    extends ReturnCodeResponse
{

    @XmlElement(nillable = true)
    protected List<AmountTO> amountList;

    /**
     * Gets the value of the amountList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the amountList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmountList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AmountTO }
     * 
     * 
     */
    public List<AmountTO> getAmountList() {
        if (amountList == null) {
            amountList = new ArrayList<AmountTO>();
        }
        return this.amountList;
    }

}
