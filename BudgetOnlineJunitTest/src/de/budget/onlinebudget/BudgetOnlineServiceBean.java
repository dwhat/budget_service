
package de.budget.onlinebudget;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BudgetOnlineServiceBean", targetNamespace = "http://onlinebudget.budget.de/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BudgetOnlineServiceBean {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.CategoryResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCategory", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategory")
    @ResponseWrapper(localName = "getCategoryResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategoryResponse")
    public CategoryResponse getCategory(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteCategory", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteCategory")
    @ResponseWrapper(localName = "deleteCategoryResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteCategoryResponse")
    public ReturnCodeResponse deleteCategory(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAmountForVendors", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetAmountForVendors")
    @ResponseWrapper(localName = "getAmountForVendorsResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetAmountForVendorsResponse")
    public AmountListResponse getAmountForVendors(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteVendor", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteVendor")
    @ResponseWrapper(localName = "deleteVendorResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteVendorResponse")
    public ReturnCodeResponse deleteVendor(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.IncomeListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIncomes", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomes")
    @ResponseWrapper(localName = "getIncomesResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesResponse")
    public IncomeListResponse getIncomes(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "generateReport", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GenerateReport")
    @ResponseWrapper(localName = "generateReportResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GenerateReportResponse")
    public ReturnCodeResponse generateReport(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.BasketListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLastBaskets", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetLastBaskets")
    @ResponseWrapper(localName = "getLastBasketsResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetLastBasketsResponse")
    public BasketListResponse getLastBaskets(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.UserLoginResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "setUser", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.SetUser")
    @ResponseWrapper(localName = "setUserResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.SetUserResponse")
    public UserLoginResponse setUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.IncomeListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIncomesByCategory", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesByCategory")
    @ResponseWrapper(localName = "getIncomesByCategoryResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesByCategoryResponse")
    public IncomeListResponse getIncomesByCategory(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ItemListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItemsByBasket", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemsByBasket")
    @ResponseWrapper(localName = "getItemsByBasketResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemsByBasketResponse")
    public ItemListResponse getItemsByBasket(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @return
     *     returns de.budget.onlinebudget.VendorResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdateVendor", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateVendor")
    @ResponseWrapper(localName = "createOrUpdateVendorResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateVendorResponse")
    public VendorResponse createOrUpdateVendor(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        int arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        int arg7);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBasketsOfActualMonth", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsOfActualMonth")
    @ResponseWrapper(localName = "getBasketsOfActualMonthResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsOfActualMonthResponse")
    public AmountResponse getBasketsOfActualMonth(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @param arg8
     * @return
     *     returns de.budget.onlinebudget.BasketResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdateBasketList", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateBasketList")
    @ResponseWrapper(localName = "createOrUpdateBasketListResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateBasketListResponse")
    public BasketResponse createOrUpdateBasketList(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        double arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        long arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        int arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        int arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        List<ItemTO> arg8);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIncomesOfActualMonth", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesOfActualMonth")
    @ResponseWrapper(localName = "getIncomesOfActualMonthResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesOfActualMonthResponse")
    public AmountResponse getIncomesOfActualMonth(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @return
     *     returns de.budget.onlinebudget.IncomeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdateIncome", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateIncome")
    @ResponseWrapper(localName = "createOrUpdateIncomeResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateIncomeResponse")
    public IncomeResponse createOrUpdateIncome(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        double arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        double arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        long arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        int arg7);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.CategoryListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCategorys", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategorys")
    @ResponseWrapper(localName = "getCategorysResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategorysResponse")
    public CategoryListResponse getCategorys(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.PaymentResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdatePayment", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdatePayment")
    @ResponseWrapper(localName = "createOrUpdatePaymentResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdatePaymentResponse")
    public PaymentResponse createOrUpdatePayment(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        boolean arg5);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.CategoryListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCategorysOfIncome", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategorysOfIncome")
    @ResponseWrapper(localName = "getCategorysOfIncomeResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategorysOfIncomeResponse")
    public CategoryListResponse getCategorysOfIncome(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.VendorListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVendors", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetVendors")
    @ResponseWrapper(localName = "getVendorsResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetVendorsResponse")
    public VendorListResponse getVendors(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.BasketResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBasket", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasket")
    @ResponseWrapper(localName = "getBasketResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketResponse")
    public BasketResponse getBasket(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.PaymentListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPayments", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetPayments")
    @ResponseWrapper(localName = "getPaymentsResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetPaymentsResponse")
    public PaymentListResponse getPayments(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIncomeByPeriod", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomeByPeriod")
    @ResponseWrapper(localName = "getIncomeByPeriodResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomeByPeriodResponse")
    public AmountResponse getIncomeByPeriod(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "logout", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.Logout")
    @ResponseWrapper(localName = "logoutResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.LogoutResponse")
    public ReturnCodeResponse logout(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteBasket", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteBasket")
    @ResponseWrapper(localName = "deleteBasketResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteBasketResponse")
    public ReturnCodeResponse deleteBasket(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.IncomeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIncome", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncome")
    @ResponseWrapper(localName = "getIncomeResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomeResponse")
    public IncomeResponse getIncome(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.PaymentResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPayment", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetPayment")
    @ResponseWrapper(localName = "getPaymentResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetPaymentResponse")
    public PaymentResponse getPayment(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLossByPeriod", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetLossByPeriod")
    @ResponseWrapper(localName = "getLossByPeriodResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetLossByPeriodResponse")
    public AmountResponse getLossByPeriod(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @return
     *     returns de.budget.onlinebudget.BasketResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdateBasket", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateBasket")
    @ResponseWrapper(localName = "createOrUpdateBasketResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateBasketResponse")
    public BasketResponse createOrUpdateBasket(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        double arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        long arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        int arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        int arg7);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<de.budget.onlinebudget.Basket>
     * @throws BudgetOnlineException_Exception
     * @throws Exception_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBasketsByVendorHelper", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsByVendorHelper")
    @ResponseWrapper(localName = "getBasketsByVendorHelperResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsByVendorHelperResponse")
    public List<Basket> getBasketsByVendorHelper(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1)
        throws BudgetOnlineException_Exception, Exception_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteIncome", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteIncome")
    @ResponseWrapper(localName = "deleteIncomeResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteIncomeResponse")
    public ReturnCodeResponse deleteIncome(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteItem", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteItem")
    @ResponseWrapper(localName = "deleteItemResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteItemResponse")
    public ReturnCodeResponse deleteItem(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.BasketListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBaskets", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBaskets")
    @ResponseWrapper(localName = "getBasketsResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsResponse")
    public BasketListResponse getBaskets(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.UserLoginResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.LoginResponse")
    public UserLoginResponse login(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.CategoryListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCategorysOfLoss", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategorysOfLoss")
    @ResponseWrapper(localName = "getCategorysOfLossResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetCategorysOfLossResponse")
    public CategoryListResponse getCategorysOfLoss(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ItemListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItemsByLossCategory", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemsByLossCategory")
    @ResponseWrapper(localName = "getItemsByLossCategoryResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemsByLossCategoryResponse")
    public ItemListResponse getItemsByLossCategory(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteUser", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteUser")
    @ResponseWrapper(localName = "deleteUserResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeleteUserResponse")
    public ReturnCodeResponse deleteUser(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.BasketListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBasketsByPayment", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsByPayment")
    @ResponseWrapper(localName = "getBasketsByPaymentResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsByPaymentResponse")
    public BasketListResponse getBasketsByPayment(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @return
     *     returns de.budget.onlinebudget.CategoryResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdateCategory", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateCategory")
    @ResponseWrapper(localName = "createOrUpdateCategoryResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateCategoryResponse")
    public CategoryResponse createOrUpdateCategory(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        boolean arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        boolean arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        String arg6);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.VendorResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getVendor", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetVendor")
    @ResponseWrapper(localName = "getVendorResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetVendorResponse")
    public VendorResponse getVendor(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ReturnCodeResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deletePayment", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeletePayment")
    @ResponseWrapper(localName = "deletePaymentResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.DeletePaymentResponse")
    public ReturnCodeResponse deletePayment(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIncomesAmountForCategories", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesAmountForCategories")
    @ResponseWrapper(localName = "getIncomesAmountForCategoriesResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetIncomesAmountForCategoriesResponse")
    public AmountListResponse getIncomesAmountForCategories(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.UserResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserByName", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetUserByName")
    @ResponseWrapper(localName = "getUserByNameResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetUserByNameResponse")
    public UserResponse getUserByName(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.BasketListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBasketsByVendor", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsByVendor")
    @ResponseWrapper(localName = "getBasketsByVendorResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetBasketsByVendorResponse")
    public BasketListResponse getBasketsByVendor(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.ItemResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItemByBasket", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemByBasket")
    @ResponseWrapper(localName = "getItemByBasketResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemByBasketResponse")
    public ItemResponse getItemByBasket(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.AmountListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getItemsAmountForCategories", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemsAmountForCategories")
    @ResponseWrapper(localName = "getItemsAmountForCategoriesResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetItemsAmountForCategoriesResponse")
    public AmountListResponse getItemsAmountForCategories(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns de.budget.onlinebudget.IncomeListResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLastIncomes", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetLastIncomes")
    @ResponseWrapper(localName = "getLastIncomesResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.GetLastIncomesResponse")
    public IncomeListResponse getLastIncomes(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

    /**
     * 
     * @param arg5
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @param arg6
     * @param arg7
     * @param arg8
     * @return
     *     returns de.budget.onlinebudget.ItemResponse
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createOrUpdateItem", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateItem")
    @ResponseWrapper(localName = "createOrUpdateItemResponse", targetNamespace = "http://onlinebudget.budget.de/", className = "de.budget.onlinebudget.CreateOrUpdateItemResponse")
    public ItemResponse createOrUpdateItem(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        double arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        double arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        long arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        int arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        int arg8);

}
