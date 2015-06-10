
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for incomeTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="incomeTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="category" type="{http://onlinebudget.budget.de/}categoryTO" minOccurs="0"/>
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lastChanged" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="receiptDate" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="user" type="{http://onlinebudget.budget.de/}userTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incomeTO", propOrder = {
    "amount",
    "category",
    "createDate",
    "id",
    "lastChanged",
    "name",
    "notice",
    "quantity",
    "receiptDate",
    "user"
})
public class IncomeTO {

    protected double amount;
    protected CategoryTO category;
    protected long createDate;
    protected int id;
    protected long lastChanged;
    protected String name;
    protected String notice;
    protected double quantity;
    protected long receiptDate;
    protected UserTO user;

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryTO }
     *     
     */
    public CategoryTO getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryTO }
     *     
     */
    public void setCategory(CategoryTO value) {
        this.category = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     */
    public long getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     */
    public void setCreateDate(long value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the lastChanged property.
     * 
     */
    public long getLastChanged() {
        return lastChanged;
    }

    /**
     * Sets the value of the lastChanged property.
     * 
     */
    public void setLastChanged(long value) {
        this.lastChanged = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the notice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotice() {
        return notice;
    }

    /**
     * Sets the value of the notice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotice(String value) {
        this.notice = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(double value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the receiptDate property.
     * 
     */
    public long getReceiptDate() {
        return receiptDate;
    }

    /**
     * Sets the value of the receiptDate property.
     * 
     */
    public void setReceiptDate(long value) {
        this.receiptDate = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link UserTO }
     *     
     */
    public UserTO getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserTO }
     *     
     */
    public void setUser(UserTO value) {
        this.user = value;
    }

}
