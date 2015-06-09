
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createOrUpdateItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createOrUpdateItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="arg4" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="arg5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arg6" type="{http://onlinebudget.budget.de/}timestamp" minOccurs="0"/>
 *         &lt;element name="arg7" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg8" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createOrUpdateItem", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4",
    "arg5",
    "arg6",
    "arg7",
    "arg8"
})
public class CreateOrUpdateItem {

    protected int arg0;
    protected int arg1;
    protected String arg2;
    protected double arg3;
    protected double arg4;
    protected String arg5;
    protected Timestamp arg6;
    protected int arg7;
    protected int arg8;

    /**
     * Gets the value of the arg0 property.
     * 
     */
    public int getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     */
    public void setArg0(int value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     */
    public int getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     */
    public void setArg1(int value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg2(String value) {
        this.arg2 = value;
    }

    /**
     * Gets the value of the arg3 property.
     * 
     */
    public double getArg3() {
        return arg3;
    }

    /**
     * Sets the value of the arg3 property.
     * 
     */
    public void setArg3(double value) {
        this.arg3 = value;
    }

    /**
     * Gets the value of the arg4 property.
     * 
     */
    public double getArg4() {
        return arg4;
    }

    /**
     * Sets the value of the arg4 property.
     * 
     */
    public void setArg4(double value) {
        this.arg4 = value;
    }

    /**
     * Gets the value of the arg5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArg5() {
        return arg5;
    }

    /**
     * Sets the value of the arg5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArg5(String value) {
        this.arg5 = value;
    }

    /**
     * Gets the value of the arg6 property.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getArg6() {
        return arg6;
    }

    /**
     * Sets the value of the arg6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setArg6(Timestamp value) {
        this.arg6 = value;
    }

    /**
     * Gets the value of the arg7 property.
     * 
     */
    public int getArg7() {
        return arg7;
    }

    /**
     * Sets the value of the arg7 property.
     * 
     */
    public void setArg7(int value) {
        this.arg7 = value;
    }

    /**
     * Gets the value of the arg8 property.
     * 
     */
    public int getArg8() {
        return arg8;
    }

    /**
     * Sets the value of the arg8 property.
     * 
     */
    public void setArg8(int value) {
        this.arg8 = value;
    }

}
