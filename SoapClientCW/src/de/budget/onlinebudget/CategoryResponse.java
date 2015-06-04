
package de.budget.onlinebudget;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="categoryTo" type="{http://onlinebudget.budget.de/}categoryTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryResponse", propOrder = {
    "categoryTo"
})
public class CategoryResponse
    extends ReturnCodeResponse
{

    protected CategoryTO categoryTo;

    /**
     * Gets the value of the categoryTo property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryTO }
     *     
     */
    public CategoryTO getCategoryTo() {
        return categoryTo;
    }

    /**
     * Sets the value of the categoryTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryTO }
     *     
     */
    public void setCategoryTo(CategoryTO value) {
        this.categoryTo = value;
    }

}
