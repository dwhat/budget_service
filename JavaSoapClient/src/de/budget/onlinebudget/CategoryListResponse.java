
package de.budget.onlinebudget;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for categoryListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="categoryListResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://onlinebudget.budget.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="categoryList" type="{http://onlinebudget.budget.de/}categoryTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryListResponse", propOrder = {
    "categoryList"
})
public class CategoryListResponse
    extends ReturnCodeResponse
{

    @XmlElement(nillable = true)
    protected List<CategoryTO> categoryList;

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

}
