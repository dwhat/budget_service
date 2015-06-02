
package de.budget.onlinebudget;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="basketList" type="{http://onlinebudget.budget.de/}basketTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="categoryList" type="{http://onlinebudget.budget.de/}categoryTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://onlinebudget.budget.de/}timestamp" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastChanged" type="{http://onlinebudget.budget.de/}timestamp" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentList" type="{http://onlinebudget.budget.de/}paymentTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vendorList" type="{http://onlinebudget.budget.de/}vendorTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userTO", propOrder = {
    "basketList",
    "categoryList",
    "createDate",
    "email",
    "lastChanged",
    "password",
    "paymentList",
    "username",
    "vendorList"
})
public class UserTO {

    @XmlElement(nillable = true)
    protected List<BasketTO> basketList;
    @XmlElement(nillable = true)
    protected List<CategoryTO> categoryList;
    protected Timestamp createDate;
    protected String email;
    protected Timestamp lastChanged;
    protected String password;
    @XmlElement(nillable = true)
    protected List<PaymentTO> paymentList;
    protected String username;
    @XmlElement(nillable = true)
    protected List<VendorTO> vendorList;

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

    /**
     * Gets the value of the categoryList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoryTO }
     * 
     * 
     */
    public List<CategoryTO> getCategoryList() {
        if (categoryList == null) {
            categoryList = new ArrayList<CategoryTO>();
        }
        return this.categoryList;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setCreateDate(Timestamp value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the lastChanged property.
     * 
     * @return
     *     possible object is
     *     {@link Timestamp }
     *     
     */
    public Timestamp getLastChanged() {
        return lastChanged;
    }

    /**
     * Sets the value of the lastChanged property.
     * 
     * @param value
     *     allowed object is
     *     {@link Timestamp }
     *     
     */
    public void setLastChanged(Timestamp value) {
        this.lastChanged = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the paymentList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTO }
     * 
     * 
     */
    public List<PaymentTO> getPaymentList() {
        if (paymentList == null) {
            paymentList = new ArrayList<PaymentTO>();
        }
        return this.paymentList;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

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
