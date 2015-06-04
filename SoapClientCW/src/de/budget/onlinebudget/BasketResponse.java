
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for basketResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="basketResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="basketTo" type="{http://onlinebudget.budget.de/}basketTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basketResponse", propOrder = {
    "basketTo"
})
public class BasketResponse
    extends ReturnCodeResponse
{

    protected BasketTO basketTo;

    /**
     * Gets the value of the basketTo property.
     * 
     * @return
     *     possible object is
     *     {@link BasketTO }
     *     
     */
    public BasketTO getBasketTo() {
        return basketTo;
    }

    /**
     * Sets the value of the basketTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BasketTO }
     *     
     */
    public void setBasketTo(BasketTO value) {
        this.basketTo = value;
    }

}
