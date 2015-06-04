
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for incomeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incomeResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="incomeTo" type="{http://onlinebudget.budget.de/}incomeTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incomeResponse", propOrder = {
    "incomeTo"
})
public class IncomeResponse
    extends ReturnCodeResponse
{

    protected IncomeTO incomeTo;

    /**
     * Gets the value of the incomeTo property.
     * 
     * @return
     *     possible object is
     *     {@link IncomeTO }
     *     
     */
    public IncomeTO getIncomeTo() {
        return incomeTo;
    }

    /**
     * Sets the value of the incomeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IncomeTO }
     *     
     */
    public void setIncomeTo(IncomeTO value) {
        this.incomeTo = value;
    }

}
