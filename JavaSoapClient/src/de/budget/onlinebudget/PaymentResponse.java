
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paymentResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paymentResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="paymentTo" type="{http://onlinebudget.budget.de/}paymentTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentResponse", propOrder = {
    "paymentTo"
})
public class PaymentResponse
    extends ReturnCodeResponse
{

    protected PaymentTO paymentTo;

    /**
     * Gets the value of the paymentTo property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTO }
     *     
     */
    public PaymentTO getPaymentTo() {
        return paymentTo;
    }

    /**
     * Sets the value of the paymentTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTO }
     *     
     */
    public void setPaymentTo(PaymentTO value) {
        this.paymentTo = value;
    }

}
