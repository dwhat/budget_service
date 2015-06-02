
package de.budget.onlinebudget;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.budget.onlinebudget package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetBasketsByVendorResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsByVendorResponse");
    private final static QName _GetItemByBasketResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getItemByBasketResponse");
    private final static QName _DeletePayment_QNAME = new QName("http://onlinebudget.budget.de/", "deletePayment");
    private final static QName _GetBasketsByVendor_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsByVendor");
    private final static QName _GetIncomesOfActualMonth_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomesOfActualMonth");
    private final static QName _GetUserByNameResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getUserByNameResponse");
    private final static QName _GetBasketsResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsResponse");
    private final static QName _GetCategorysResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getCategorysResponse");
    private final static QName _DeleteUser_QNAME = new QName("http://onlinebudget.budget.de/", "deleteUser");
    private final static QName _GetLossByPeriod_QNAME = new QName("http://onlinebudget.budget.de/", "getLossByPeriod");
    private final static QName _CreateOrUpdateCategory_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateCategory");
    private final static QName _GetVendor_QNAME = new QName("http://onlinebudget.budget.de/", "getVendor");
    private final static QName _CreateOrUpdateIncome_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateIncome");
    private final static QName _DeleteCategoryResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deleteCategoryResponse");
    private final static QName _CreateOrUpdateCategoryResponse_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateCategoryResponse");
    private final static QName _GetPayments_QNAME = new QName("http://onlinebudget.budget.de/", "getPayments");
    private final static QName _CreateOrUpdateBasketResponse_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateBasketResponse");
    private final static QName _DeleteCategory_QNAME = new QName("http://onlinebudget.budget.de/", "deleteCategory");
    private final static QName _GetBasketsByPayment_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsByPayment");
    private final static QName _GetBasketsByPaymentResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsByPaymentResponse");
    private final static QName _DeleteIncomeResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deleteIncomeResponse");
    private final static QName _DeletePaymentResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deletePaymentResponse");
    private final static QName _GetItemsByBasketResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getItemsByBasketResponse");
    private final static QName _GetLastIncomesResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getLastIncomesResponse");
    private final static QName _Login_QNAME = new QName("http://onlinebudget.budget.de/", "login");
    private final static QName _CreateOrUpdateBasket_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateBasket");
    private final static QName _GetPaymentResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getPaymentResponse");
    private final static QName _CreateOrUpdateVendorResponse_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateVendorResponse");
    private final static QName _LogoutResponse_QNAME = new QName("http://onlinebudget.budget.de/", "logoutResponse");
    private final static QName _DeleteVendorResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deleteVendorResponse");
    private final static QName _GetVendorResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getVendorResponse");
    private final static QName _GetIncomesOfActualMonthResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomesOfActualMonthResponse");
    private final static QName _GetLastIncomes_QNAME = new QName("http://onlinebudget.budget.de/", "getLastIncomes");
    private final static QName _CreateOrUpdateItem_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateItem");
    private final static QName _DeleteVendor_QNAME = new QName("http://onlinebudget.budget.de/", "deleteVendor");
    private final static QName _GetLastBasketsResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getLastBasketsResponse");
    private final static QName _GetPayment_QNAME = new QName("http://onlinebudget.budget.de/", "getPayment");
    private final static QName _DeleteItem_QNAME = new QName("http://onlinebudget.budget.de/", "deleteItem");
    private final static QName _GetItemByBasket_QNAME = new QName("http://onlinebudget.budget.de/", "getItemByBasket");
    private final static QName _DeleteItemResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deleteItemResponse");
    private final static QName _GetItemsByLossCategoryResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getItemsByLossCategoryResponse");
    private final static QName _GetBasketResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketResponse");
    private final static QName _CreateOrUpdatePayment_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdatePayment");
    private final static QName _GetIncomeResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomeResponse");
    private final static QName _SetUserResponse_QNAME = new QName("http://onlinebudget.budget.de/", "setUserResponse");
    private final static QName _GetVendors_QNAME = new QName("http://onlinebudget.budget.de/", "getVendors");
    private final static QName _LoginResponse_QNAME = new QName("http://onlinebudget.budget.de/", "loginResponse");
    private final static QName _CreateOrUpdatePaymentResponse_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdatePaymentResponse");
    private final static QName _GetItemsByBasket_QNAME = new QName("http://onlinebudget.budget.de/", "getItemsByBasket");
    private final static QName _GetBasketsOfActualMonth_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsOfActualMonth");
    private final static QName _GetItemsByLossCategory_QNAME = new QName("http://onlinebudget.budget.de/", "getItemsByLossCategory");
    private final static QName _GetIncomesByCategory_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomesByCategory");
    private final static QName _GetIncomeByPeriod_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomeByPeriod");
    private final static QName _GetLastBaskets_QNAME = new QName("http://onlinebudget.budget.de/", "getLastBaskets");
    private final static QName _GetCategory_QNAME = new QName("http://onlinebudget.budget.de/", "getCategory");
    private final static QName _Logout_QNAME = new QName("http://onlinebudget.budget.de/", "logout");
    private final static QName _GetCategorys_QNAME = new QName("http://onlinebudget.budget.de/", "getCategorys");
    private final static QName _CreateOrUpdateItemResponse_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateItemResponse");
    private final static QName _GetIncome_QNAME = new QName("http://onlinebudget.budget.de/", "getIncome");
    private final static QName _DeleteBasketResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deleteBasketResponse");
    private final static QName _GetLossByPeriodResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getLossByPeriodResponse");
    private final static QName _GetVendorsResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getVendorsResponse");
    private final static QName _GetBaskets_QNAME = new QName("http://onlinebudget.budget.de/", "getBaskets");
    private final static QName _GetBasket_QNAME = new QName("http://onlinebudget.budget.de/", "getBasket");
    private final static QName _DeleteBasket_QNAME = new QName("http://onlinebudget.budget.de/", "deleteBasket");
    private final static QName _GetBasketsOfActualMonthResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getBasketsOfActualMonthResponse");
    private final static QName _GetIncomesByCategoryResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomesByCategoryResponse");
    private final static QName _CreateOrUpdateVendor_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateVendor");
    private final static QName _DeleteIncome_QNAME = new QName("http://onlinebudget.budget.de/", "deleteIncome");
    private final static QName _GetPaymentsResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getPaymentsResponse");
    private final static QName _SetUser_QNAME = new QName("http://onlinebudget.budget.de/", "setUser");
    private final static QName _GetIncomeByPeriodResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getIncomeByPeriodResponse");
    private final static QName _GetUserByName_QNAME = new QName("http://onlinebudget.budget.de/", "getUserByName");
    private final static QName _DeleteUserResponse_QNAME = new QName("http://onlinebudget.budget.de/", "deleteUserResponse");
    private final static QName _GetCategoryResponse_QNAME = new QName("http://onlinebudget.budget.de/", "getCategoryResponse");
    private final static QName _CreateOrUpdateIncomeResponse_QNAME = new QName("http://onlinebudget.budget.de/", "createOrUpdateIncomeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.budget.onlinebudget
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeletePaymentResponse }
     * 
     */
    public DeletePaymentResponse createDeletePaymentResponse() {
        return new DeletePaymentResponse();
    }

    /**
     * Create an instance of {@link DeleteIncomeResponse }
     * 
     */
    public DeleteIncomeResponse createDeleteIncomeResponse() {
        return new DeleteIncomeResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdateCategoryResponse }
     * 
     */
    public CreateOrUpdateCategoryResponse createCreateOrUpdateCategoryResponse() {
        return new CreateOrUpdateCategoryResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdateBasketResponse }
     * 
     */
    public CreateOrUpdateBasketResponse createCreateOrUpdateBasketResponse() {
        return new CreateOrUpdateBasketResponse();
    }

    /**
     * Create an instance of {@link GetPayments }
     * 
     */
    public GetPayments createGetPayments() {
        return new GetPayments();
    }

    /**
     * Create an instance of {@link GetBasketsByPayment }
     * 
     */
    public GetBasketsByPayment createGetBasketsByPayment() {
        return new GetBasketsByPayment();
    }

    /**
     * Create an instance of {@link GetBasketsByPaymentResponse }
     * 
     */
    public GetBasketsByPaymentResponse createGetBasketsByPaymentResponse() {
        return new GetBasketsByPaymentResponse();
    }

    /**
     * Create an instance of {@link DeleteCategory }
     * 
     */
    public DeleteCategory createDeleteCategory() {
        return new DeleteCategory();
    }

    /**
     * Create an instance of {@link GetVendorResponse }
     * 
     */
    public GetVendorResponse createGetVendorResponse() {
        return new GetVendorResponse();
    }

    /**
     * Create an instance of {@link GetLastIncomes }
     * 
     */
    public GetLastIncomes createGetLastIncomes() {
        return new GetLastIncomes();
    }

    /**
     * Create an instance of {@link CreateOrUpdateItem }
     * 
     */
    public CreateOrUpdateItem createCreateOrUpdateItem() {
        return new CreateOrUpdateItem();
    }

    /**
     * Create an instance of {@link DeleteVendor }
     * 
     */
    public DeleteVendor createDeleteVendor() {
        return new DeleteVendor();
    }

    /**
     * Create an instance of {@link GetIncomesOfActualMonthResponse }
     * 
     */
    public GetIncomesOfActualMonthResponse createGetIncomesOfActualMonthResponse() {
        return new GetIncomesOfActualMonthResponse();
    }

    /**
     * Create an instance of {@link DeleteItem }
     * 
     */
    public DeleteItem createDeleteItem() {
        return new DeleteItem();
    }

    /**
     * Create an instance of {@link GetPayment }
     * 
     */
    public GetPayment createGetPayment() {
        return new GetPayment();
    }

    /**
     * Create an instance of {@link GetLastBasketsResponse }
     * 
     */
    public GetLastBasketsResponse createGetLastBasketsResponse() {
        return new GetLastBasketsResponse();
    }

    /**
     * Create an instance of {@link GetItemsByBasketResponse }
     * 
     */
    public GetItemsByBasketResponse createGetItemsByBasketResponse() {
        return new GetItemsByBasketResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link GetLastIncomesResponse }
     * 
     */
    public GetLastIncomesResponse createGetLastIncomesResponse() {
        return new GetLastIncomesResponse();
    }

    /**
     * Create an instance of {@link GetPaymentResponse }
     * 
     */
    public GetPaymentResponse createGetPaymentResponse() {
        return new GetPaymentResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdateBasket }
     * 
     */
    public CreateOrUpdateBasket createCreateOrUpdateBasket() {
        return new CreateOrUpdateBasket();
    }

    /**
     * Create an instance of {@link DeleteVendorResponse }
     * 
     */
    public DeleteVendorResponse createDeleteVendorResponse() {
        return new DeleteVendorResponse();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdateVendorResponse }
     * 
     */
    public CreateOrUpdateVendorResponse createCreateOrUpdateVendorResponse() {
        return new CreateOrUpdateVendorResponse();
    }

    /**
     * Create an instance of {@link GetUserByNameResponse }
     * 
     */
    public GetUserByNameResponse createGetUserByNameResponse() {
        return new GetUserByNameResponse();
    }

    /**
     * Create an instance of {@link GetBasketsResponse }
     * 
     */
    public GetBasketsResponse createGetBasketsResponse() {
        return new GetBasketsResponse();
    }

    /**
     * Create an instance of {@link GetCategorysResponse }
     * 
     */
    public GetCategorysResponse createGetCategorysResponse() {
        return new GetCategorysResponse();
    }

    /**
     * Create an instance of {@link GetBasketsByVendorResponse }
     * 
     */
    public GetBasketsByVendorResponse createGetBasketsByVendorResponse() {
        return new GetBasketsByVendorResponse();
    }

    /**
     * Create an instance of {@link GetItemByBasketResponse }
     * 
     */
    public GetItemByBasketResponse createGetItemByBasketResponse() {
        return new GetItemByBasketResponse();
    }

    /**
     * Create an instance of {@link GetIncomesOfActualMonth }
     * 
     */
    public GetIncomesOfActualMonth createGetIncomesOfActualMonth() {
        return new GetIncomesOfActualMonth();
    }

    /**
     * Create an instance of {@link DeletePayment }
     * 
     */
    public DeletePayment createDeletePayment() {
        return new DeletePayment();
    }

    /**
     * Create an instance of {@link GetBasketsByVendor }
     * 
     */
    public GetBasketsByVendor createGetBasketsByVendor() {
        return new GetBasketsByVendor();
    }

    /**
     * Create an instance of {@link CreateOrUpdateCategory }
     * 
     */
    public CreateOrUpdateCategory createCreateOrUpdateCategory() {
        return new CreateOrUpdateCategory();
    }

    /**
     * Create an instance of {@link GetVendor }
     * 
     */
    public GetVendor createGetVendor() {
        return new GetVendor();
    }

    /**
     * Create an instance of {@link CreateOrUpdateIncome }
     * 
     */
    public CreateOrUpdateIncome createCreateOrUpdateIncome() {
        return new CreateOrUpdateIncome();
    }

    /**
     * Create an instance of {@link DeleteCategoryResponse }
     * 
     */
    public DeleteCategoryResponse createDeleteCategoryResponse() {
        return new DeleteCategoryResponse();
    }

    /**
     * Create an instance of {@link DeleteUser }
     * 
     */
    public DeleteUser createDeleteUser() {
        return new DeleteUser();
    }

    /**
     * Create an instance of {@link GetLossByPeriod }
     * 
     */
    public GetLossByPeriod createGetLossByPeriod() {
        return new GetLossByPeriod();
    }

    /**
     * Create an instance of {@link CreateOrUpdateVendor }
     * 
     */
    public CreateOrUpdateVendor createCreateOrUpdateVendor() {
        return new CreateOrUpdateVendor();
    }

    /**
     * Create an instance of {@link DeleteIncome }
     * 
     */
    public DeleteIncome createDeleteIncome() {
        return new DeleteIncome();
    }

    /**
     * Create an instance of {@link GetBasket }
     * 
     */
    public GetBasket createGetBasket() {
        return new GetBasket();
    }

    /**
     * Create an instance of {@link GetVendorsResponse }
     * 
     */
    public GetVendorsResponse createGetVendorsResponse() {
        return new GetVendorsResponse();
    }

    /**
     * Create an instance of {@link GetBaskets }
     * 
     */
    public GetBaskets createGetBaskets() {
        return new GetBaskets();
    }

    /**
     * Create an instance of {@link DeleteBasket }
     * 
     */
    public DeleteBasket createDeleteBasket() {
        return new DeleteBasket();
    }

    /**
     * Create an instance of {@link GetBasketsOfActualMonthResponse }
     * 
     */
    public GetBasketsOfActualMonthResponse createGetBasketsOfActualMonthResponse() {
        return new GetBasketsOfActualMonthResponse();
    }

    /**
     * Create an instance of {@link GetIncomesByCategoryResponse }
     * 
     */
    public GetIncomesByCategoryResponse createGetIncomesByCategoryResponse() {
        return new GetIncomesByCategoryResponse();
    }

    /**
     * Create an instance of {@link DeleteUserResponse }
     * 
     */
    public DeleteUserResponse createDeleteUserResponse() {
        return new DeleteUserResponse();
    }

    /**
     * Create an instance of {@link GetCategoryResponse }
     * 
     */
    public GetCategoryResponse createGetCategoryResponse() {
        return new GetCategoryResponse();
    }

    /**
     * Create an instance of {@link GetUserByName }
     * 
     */
    public GetUserByName createGetUserByName() {
        return new GetUserByName();
    }

    /**
     * Create an instance of {@link GetIncomeByPeriodResponse }
     * 
     */
    public GetIncomeByPeriodResponse createGetIncomeByPeriodResponse() {
        return new GetIncomeByPeriodResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdateIncomeResponse }
     * 
     */
    public CreateOrUpdateIncomeResponse createCreateOrUpdateIncomeResponse() {
        return new CreateOrUpdateIncomeResponse();
    }

    /**
     * Create an instance of {@link SetUser }
     * 
     */
    public SetUser createSetUser() {
        return new SetUser();
    }

    /**
     * Create an instance of {@link GetPaymentsResponse }
     * 
     */
    public GetPaymentsResponse createGetPaymentsResponse() {
        return new GetPaymentsResponse();
    }

    /**
     * Create an instance of {@link GetIncomeResponse }
     * 
     */
    public GetIncomeResponse createGetIncomeResponse() {
        return new GetIncomeResponse();
    }

    /**
     * Create an instance of {@link SetUserResponse }
     * 
     */
    public SetUserResponse createSetUserResponse() {
        return new SetUserResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link GetVendors }
     * 
     */
    public GetVendors createGetVendors() {
        return new GetVendors();
    }

    /**
     * Create an instance of {@link GetItemsByBasket }
     * 
     */
    public GetItemsByBasket createGetItemsByBasket() {
        return new GetItemsByBasket();
    }

    /**
     * Create an instance of {@link CreateOrUpdatePaymentResponse }
     * 
     */
    public CreateOrUpdatePaymentResponse createCreateOrUpdatePaymentResponse() {
        return new CreateOrUpdatePaymentResponse();
    }

    /**
     * Create an instance of {@link DeleteItemResponse }
     * 
     */
    public DeleteItemResponse createDeleteItemResponse() {
        return new DeleteItemResponse();
    }

    /**
     * Create an instance of {@link GetItemByBasket }
     * 
     */
    public GetItemByBasket createGetItemByBasket() {
        return new GetItemByBasket();
    }

    /**
     * Create an instance of {@link GetItemsByLossCategoryResponse }
     * 
     */
    public GetItemsByLossCategoryResponse createGetItemsByLossCategoryResponse() {
        return new GetItemsByLossCategoryResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdatePayment }
     * 
     */
    public CreateOrUpdatePayment createCreateOrUpdatePayment() {
        return new CreateOrUpdatePayment();
    }

    /**
     * Create an instance of {@link GetBasketResponse }
     * 
     */
    public GetBasketResponse createGetBasketResponse() {
        return new GetBasketResponse();
    }

    /**
     * Create an instance of {@link GetLastBaskets }
     * 
     */
    public GetLastBaskets createGetLastBaskets() {
        return new GetLastBaskets();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link GetCategory }
     * 
     */
    public GetCategory createGetCategory() {
        return new GetCategory();
    }

    /**
     * Create an instance of {@link CreateOrUpdateItemResponse }
     * 
     */
    public CreateOrUpdateItemResponse createCreateOrUpdateItemResponse() {
        return new CreateOrUpdateItemResponse();
    }

    /**
     * Create an instance of {@link GetCategorys }
     * 
     */
    public GetCategorys createGetCategorys() {
        return new GetCategorys();
    }

    /**
     * Create an instance of {@link GetLossByPeriodResponse }
     * 
     */
    public GetLossByPeriodResponse createGetLossByPeriodResponse() {
        return new GetLossByPeriodResponse();
    }

    /**
     * Create an instance of {@link DeleteBasketResponse }
     * 
     */
    public DeleteBasketResponse createDeleteBasketResponse() {
        return new DeleteBasketResponse();
    }

    /**
     * Create an instance of {@link GetIncome }
     * 
     */
    public GetIncome createGetIncome() {
        return new GetIncome();
    }

    /**
     * Create an instance of {@link GetItemsByLossCategory }
     * 
     */
    public GetItemsByLossCategory createGetItemsByLossCategory() {
        return new GetItemsByLossCategory();
    }

    /**
     * Create an instance of {@link GetBasketsOfActualMonth }
     * 
     */
    public GetBasketsOfActualMonth createGetBasketsOfActualMonth() {
        return new GetBasketsOfActualMonth();
    }

    /**
     * Create an instance of {@link GetIncomesByCategory }
     * 
     */
    public GetIncomesByCategory createGetIncomesByCategory() {
        return new GetIncomesByCategory();
    }

    /**
     * Create an instance of {@link GetIncomeByPeriod }
     * 
     */
    public GetIncomeByPeriod createGetIncomeByPeriod() {
        return new GetIncomeByPeriod();
    }

    /**
     * Create an instance of {@link VendorResponse }
     * 
     */
    public VendorResponse createVendorResponse() {
        return new VendorResponse();
    }

    /**
     * Create an instance of {@link IncomeListResponse }
     * 
     */
    public IncomeListResponse createIncomeListResponse() {
        return new IncomeListResponse();
    }

    /**
     * Create an instance of {@link Income }
     * 
     */
    public Income createIncome() {
        return new Income();
    }

    /**
     * Create an instance of {@link PaymentTO }
     * 
     */
    public PaymentTO createPaymentTO() {
        return new PaymentTO();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link UserResponse }
     * 
     */
    public UserResponse createUserResponse() {
        return new UserResponse();
    }

    /**
     * Create an instance of {@link UserTO }
     * 
     */
    public UserTO createUserTO() {
        return new UserTO();
    }

    /**
     * Create an instance of {@link BasketTO }
     * 
     */
    public BasketTO createBasketTO() {
        return new BasketTO();
    }

    /**
     * Create an instance of {@link Timestamp }
     * 
     */
    public Timestamp createTimestamp() {
        return new Timestamp();
    }

    /**
     * Create an instance of {@link PaymentResponse }
     * 
     */
    public PaymentResponse createPaymentResponse() {
        return new PaymentResponse();
    }

    /**
     * Create an instance of {@link ItemTO }
     * 
     */
    public ItemTO createItemTO() {
        return new ItemTO();
    }

    /**
     * Create an instance of {@link Payment }
     * 
     */
    public Payment createPayment() {
        return new Payment();
    }

    /**
     * Create an instance of {@link IncomeTO }
     * 
     */
    public IncomeTO createIncomeTO() {
        return new IncomeTO();
    }

    /**
     * Create an instance of {@link Vendor }
     * 
     */
    public Vendor createVendor() {
        return new Vendor();
    }

    /**
     * Create an instance of {@link CategoryListResponse }
     * 
     */
    public CategoryListResponse createCategoryListResponse() {
        return new CategoryListResponse();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link Basket }
     * 
     */
    public Basket createBasket() {
        return new Basket();
    }

    /**
     * Create an instance of {@link BasketListResponse }
     * 
     */
    public BasketListResponse createBasketListResponse() {
        return new BasketListResponse();
    }

    /**
     * Create an instance of {@link ItemListResponse }
     * 
     */
    public ItemListResponse createItemListResponse() {
        return new ItemListResponse();
    }

    /**
     * Create an instance of {@link ReturnCodeResponse }
     * 
     */
    public ReturnCodeResponse createReturnCodeResponse() {
        return new ReturnCodeResponse();
    }

    /**
     * Create an instance of {@link IncomeResponse }
     * 
     */
    public IncomeResponse createIncomeResponse() {
        return new IncomeResponse();
    }

    /**
     * Create an instance of {@link VendorTO }
     * 
     */
    public VendorTO createVendorTO() {
        return new VendorTO();
    }

    /**
     * Create an instance of {@link PaymentListResponse }
     * 
     */
    public PaymentListResponse createPaymentListResponse() {
        return new PaymentListResponse();
    }

    /**
     * Create an instance of {@link BasketResponse }
     * 
     */
    public BasketResponse createBasketResponse() {
        return new BasketResponse();
    }

    /**
     * Create an instance of {@link UserLoginResponse }
     * 
     */
    public UserLoginResponse createUserLoginResponse() {
        return new UserLoginResponse();
    }

    /**
     * Create an instance of {@link CategoryResponse }
     * 
     */
    public CategoryResponse createCategoryResponse() {
        return new CategoryResponse();
    }

    /**
     * Create an instance of {@link ItemResponse }
     * 
     */
    public ItemResponse createItemResponse() {
        return new ItemResponse();
    }

    /**
     * Create an instance of {@link VendorListResponse }
     * 
     */
    public VendorListResponse createVendorListResponse() {
        return new VendorListResponse();
    }

    /**
     * Create an instance of {@link CategoryTO }
     * 
     */
    public CategoryTO createCategoryTO() {
        return new CategoryTO();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsByVendorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsByVendorResponse")
    public JAXBElement<GetBasketsByVendorResponse> createGetBasketsByVendorResponse(GetBasketsByVendorResponse value) {
        return new JAXBElement<GetBasketsByVendorResponse>(_GetBasketsByVendorResponse_QNAME, GetBasketsByVendorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemByBasketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getItemByBasketResponse")
    public JAXBElement<GetItemByBasketResponse> createGetItemByBasketResponse(GetItemByBasketResponse value) {
        return new JAXBElement<GetItemByBasketResponse>(_GetItemByBasketResponse_QNAME, GetItemByBasketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deletePayment")
    public JAXBElement<DeletePayment> createDeletePayment(DeletePayment value) {
        return new JAXBElement<DeletePayment>(_DeletePayment_QNAME, DeletePayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsByVendor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsByVendor")
    public JAXBElement<GetBasketsByVendor> createGetBasketsByVendor(GetBasketsByVendor value) {
        return new JAXBElement<GetBasketsByVendor>(_GetBasketsByVendor_QNAME, GetBasketsByVendor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomesOfActualMonth }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomesOfActualMonth")
    public JAXBElement<GetIncomesOfActualMonth> createGetIncomesOfActualMonth(GetIncomesOfActualMonth value) {
        return new JAXBElement<GetIncomesOfActualMonth>(_GetIncomesOfActualMonth_QNAME, GetIncomesOfActualMonth.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getUserByNameResponse")
    public JAXBElement<GetUserByNameResponse> createGetUserByNameResponse(GetUserByNameResponse value) {
        return new JAXBElement<GetUserByNameResponse>(_GetUserByNameResponse_QNAME, GetUserByNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsResponse")
    public JAXBElement<GetBasketsResponse> createGetBasketsResponse(GetBasketsResponse value) {
        return new JAXBElement<GetBasketsResponse>(_GetBasketsResponse_QNAME, GetBasketsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategorysResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getCategorysResponse")
    public JAXBElement<GetCategorysResponse> createGetCategorysResponse(GetCategorysResponse value) {
        return new JAXBElement<GetCategorysResponse>(_GetCategorysResponse_QNAME, GetCategorysResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteUser")
    public JAXBElement<DeleteUser> createDeleteUser(DeleteUser value) {
        return new JAXBElement<DeleteUser>(_DeleteUser_QNAME, DeleteUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLossByPeriod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getLossByPeriod")
    public JAXBElement<GetLossByPeriod> createGetLossByPeriod(GetLossByPeriod value) {
        return new JAXBElement<GetLossByPeriod>(_GetLossByPeriod_QNAME, GetLossByPeriod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateCategory")
    public JAXBElement<CreateOrUpdateCategory> createCreateOrUpdateCategory(CreateOrUpdateCategory value) {
        return new JAXBElement<CreateOrUpdateCategory>(_CreateOrUpdateCategory_QNAME, CreateOrUpdateCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVendor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getVendor")
    public JAXBElement<GetVendor> createGetVendor(GetVendor value) {
        return new JAXBElement<GetVendor>(_GetVendor_QNAME, GetVendor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateIncome }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateIncome")
    public JAXBElement<CreateOrUpdateIncome> createCreateOrUpdateIncome(CreateOrUpdateIncome value) {
        return new JAXBElement<CreateOrUpdateIncome>(_CreateOrUpdateIncome_QNAME, CreateOrUpdateIncome.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteCategoryResponse")
    public JAXBElement<DeleteCategoryResponse> createDeleteCategoryResponse(DeleteCategoryResponse value) {
        return new JAXBElement<DeleteCategoryResponse>(_DeleteCategoryResponse_QNAME, DeleteCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateCategoryResponse")
    public JAXBElement<CreateOrUpdateCategoryResponse> createCreateOrUpdateCategoryResponse(CreateOrUpdateCategoryResponse value) {
        return new JAXBElement<CreateOrUpdateCategoryResponse>(_CreateOrUpdateCategoryResponse_QNAME, CreateOrUpdateCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getPayments")
    public JAXBElement<GetPayments> createGetPayments(GetPayments value) {
        return new JAXBElement<GetPayments>(_GetPayments_QNAME, GetPayments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateBasketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateBasketResponse")
    public JAXBElement<CreateOrUpdateBasketResponse> createCreateOrUpdateBasketResponse(CreateOrUpdateBasketResponse value) {
        return new JAXBElement<CreateOrUpdateBasketResponse>(_CreateOrUpdateBasketResponse_QNAME, CreateOrUpdateBasketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteCategory")
    public JAXBElement<DeleteCategory> createDeleteCategory(DeleteCategory value) {
        return new JAXBElement<DeleteCategory>(_DeleteCategory_QNAME, DeleteCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsByPayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsByPayment")
    public JAXBElement<GetBasketsByPayment> createGetBasketsByPayment(GetBasketsByPayment value) {
        return new JAXBElement<GetBasketsByPayment>(_GetBasketsByPayment_QNAME, GetBasketsByPayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsByPaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsByPaymentResponse")
    public JAXBElement<GetBasketsByPaymentResponse> createGetBasketsByPaymentResponse(GetBasketsByPaymentResponse value) {
        return new JAXBElement<GetBasketsByPaymentResponse>(_GetBasketsByPaymentResponse_QNAME, GetBasketsByPaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteIncomeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteIncomeResponse")
    public JAXBElement<DeleteIncomeResponse> createDeleteIncomeResponse(DeleteIncomeResponse value) {
        return new JAXBElement<DeleteIncomeResponse>(_DeleteIncomeResponse_QNAME, DeleteIncomeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deletePaymentResponse")
    public JAXBElement<DeletePaymentResponse> createDeletePaymentResponse(DeletePaymentResponse value) {
        return new JAXBElement<DeletePaymentResponse>(_DeletePaymentResponse_QNAME, DeletePaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemsByBasketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getItemsByBasketResponse")
    public JAXBElement<GetItemsByBasketResponse> createGetItemsByBasketResponse(GetItemsByBasketResponse value) {
        return new JAXBElement<GetItemsByBasketResponse>(_GetItemsByBasketResponse_QNAME, GetItemsByBasketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastIncomesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getLastIncomesResponse")
    public JAXBElement<GetLastIncomesResponse> createGetLastIncomesResponse(GetLastIncomesResponse value) {
        return new JAXBElement<GetLastIncomesResponse>(_GetLastIncomesResponse_QNAME, GetLastIncomesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateBasket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateBasket")
    public JAXBElement<CreateOrUpdateBasket> createCreateOrUpdateBasket(CreateOrUpdateBasket value) {
        return new JAXBElement<CreateOrUpdateBasket>(_CreateOrUpdateBasket_QNAME, CreateOrUpdateBasket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getPaymentResponse")
    public JAXBElement<GetPaymentResponse> createGetPaymentResponse(GetPaymentResponse value) {
        return new JAXBElement<GetPaymentResponse>(_GetPaymentResponse_QNAME, GetPaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateVendorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateVendorResponse")
    public JAXBElement<CreateOrUpdateVendorResponse> createCreateOrUpdateVendorResponse(CreateOrUpdateVendorResponse value) {
        return new JAXBElement<CreateOrUpdateVendorResponse>(_CreateOrUpdateVendorResponse_QNAME, CreateOrUpdateVendorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "logoutResponse")
    public JAXBElement<LogoutResponse> createLogoutResponse(LogoutResponse value) {
        return new JAXBElement<LogoutResponse>(_LogoutResponse_QNAME, LogoutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVendorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteVendorResponse")
    public JAXBElement<DeleteVendorResponse> createDeleteVendorResponse(DeleteVendorResponse value) {
        return new JAXBElement<DeleteVendorResponse>(_DeleteVendorResponse_QNAME, DeleteVendorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVendorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getVendorResponse")
    public JAXBElement<GetVendorResponse> createGetVendorResponse(GetVendorResponse value) {
        return new JAXBElement<GetVendorResponse>(_GetVendorResponse_QNAME, GetVendorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomesOfActualMonthResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomesOfActualMonthResponse")
    public JAXBElement<GetIncomesOfActualMonthResponse> createGetIncomesOfActualMonthResponse(GetIncomesOfActualMonthResponse value) {
        return new JAXBElement<GetIncomesOfActualMonthResponse>(_GetIncomesOfActualMonthResponse_QNAME, GetIncomesOfActualMonthResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastIncomes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getLastIncomes")
    public JAXBElement<GetLastIncomes> createGetLastIncomes(GetLastIncomes value) {
        return new JAXBElement<GetLastIncomes>(_GetLastIncomes_QNAME, GetLastIncomes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateItem")
    public JAXBElement<CreateOrUpdateItem> createCreateOrUpdateItem(CreateOrUpdateItem value) {
        return new JAXBElement<CreateOrUpdateItem>(_CreateOrUpdateItem_QNAME, CreateOrUpdateItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVendor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteVendor")
    public JAXBElement<DeleteVendor> createDeleteVendor(DeleteVendor value) {
        return new JAXBElement<DeleteVendor>(_DeleteVendor_QNAME, DeleteVendor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastBasketsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getLastBasketsResponse")
    public JAXBElement<GetLastBasketsResponse> createGetLastBasketsResponse(GetLastBasketsResponse value) {
        return new JAXBElement<GetLastBasketsResponse>(_GetLastBasketsResponse_QNAME, GetLastBasketsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getPayment")
    public JAXBElement<GetPayment> createGetPayment(GetPayment value) {
        return new JAXBElement<GetPayment>(_GetPayment_QNAME, GetPayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteItem")
    public JAXBElement<DeleteItem> createDeleteItem(DeleteItem value) {
        return new JAXBElement<DeleteItem>(_DeleteItem_QNAME, DeleteItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemByBasket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getItemByBasket")
    public JAXBElement<GetItemByBasket> createGetItemByBasket(GetItemByBasket value) {
        return new JAXBElement<GetItemByBasket>(_GetItemByBasket_QNAME, GetItemByBasket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteItemResponse")
    public JAXBElement<DeleteItemResponse> createDeleteItemResponse(DeleteItemResponse value) {
        return new JAXBElement<DeleteItemResponse>(_DeleteItemResponse_QNAME, DeleteItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemsByLossCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getItemsByLossCategoryResponse")
    public JAXBElement<GetItemsByLossCategoryResponse> createGetItemsByLossCategoryResponse(GetItemsByLossCategoryResponse value) {
        return new JAXBElement<GetItemsByLossCategoryResponse>(_GetItemsByLossCategoryResponse_QNAME, GetItemsByLossCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketResponse")
    public JAXBElement<GetBasketResponse> createGetBasketResponse(GetBasketResponse value) {
        return new JAXBElement<GetBasketResponse>(_GetBasketResponse_QNAME, GetBasketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdatePayment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdatePayment")
    public JAXBElement<CreateOrUpdatePayment> createCreateOrUpdatePayment(CreateOrUpdatePayment value) {
        return new JAXBElement<CreateOrUpdatePayment>(_CreateOrUpdatePayment_QNAME, CreateOrUpdatePayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomeResponse")
    public JAXBElement<GetIncomeResponse> createGetIncomeResponse(GetIncomeResponse value) {
        return new JAXBElement<GetIncomeResponse>(_GetIncomeResponse_QNAME, GetIncomeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "setUserResponse")
    public JAXBElement<SetUserResponse> createSetUserResponse(SetUserResponse value) {
        return new JAXBElement<SetUserResponse>(_SetUserResponse_QNAME, SetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVendors }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getVendors")
    public JAXBElement<GetVendors> createGetVendors(GetVendors value) {
        return new JAXBElement<GetVendors>(_GetVendors_QNAME, GetVendors.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdatePaymentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdatePaymentResponse")
    public JAXBElement<CreateOrUpdatePaymentResponse> createCreateOrUpdatePaymentResponse(CreateOrUpdatePaymentResponse value) {
        return new JAXBElement<CreateOrUpdatePaymentResponse>(_CreateOrUpdatePaymentResponse_QNAME, CreateOrUpdatePaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemsByBasket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getItemsByBasket")
    public JAXBElement<GetItemsByBasket> createGetItemsByBasket(GetItemsByBasket value) {
        return new JAXBElement<GetItemsByBasket>(_GetItemsByBasket_QNAME, GetItemsByBasket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsOfActualMonth }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsOfActualMonth")
    public JAXBElement<GetBasketsOfActualMonth> createGetBasketsOfActualMonth(GetBasketsOfActualMonth value) {
        return new JAXBElement<GetBasketsOfActualMonth>(_GetBasketsOfActualMonth_QNAME, GetBasketsOfActualMonth.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetItemsByLossCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getItemsByLossCategory")
    public JAXBElement<GetItemsByLossCategory> createGetItemsByLossCategory(GetItemsByLossCategory value) {
        return new JAXBElement<GetItemsByLossCategory>(_GetItemsByLossCategory_QNAME, GetItemsByLossCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomesByCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomesByCategory")
    public JAXBElement<GetIncomesByCategory> createGetIncomesByCategory(GetIncomesByCategory value) {
        return new JAXBElement<GetIncomesByCategory>(_GetIncomesByCategory_QNAME, GetIncomesByCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomeByPeriod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomeByPeriod")
    public JAXBElement<GetIncomeByPeriod> createGetIncomeByPeriod(GetIncomeByPeriod value) {
        return new JAXBElement<GetIncomeByPeriod>(_GetIncomeByPeriod_QNAME, GetIncomeByPeriod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastBaskets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getLastBaskets")
    public JAXBElement<GetLastBaskets> createGetLastBaskets(GetLastBaskets value) {
        return new JAXBElement<GetLastBaskets>(_GetLastBaskets_QNAME, GetLastBaskets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getCategory")
    public JAXBElement<GetCategory> createGetCategory(GetCategory value) {
        return new JAXBElement<GetCategory>(_GetCategory_QNAME, GetCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Logout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "logout")
    public JAXBElement<Logout> createLogout(Logout value) {
        return new JAXBElement<Logout>(_Logout_QNAME, Logout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategorys }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getCategorys")
    public JAXBElement<GetCategorys> createGetCategorys(GetCategorys value) {
        return new JAXBElement<GetCategorys>(_GetCategorys_QNAME, GetCategorys.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateItemResponse")
    public JAXBElement<CreateOrUpdateItemResponse> createCreateOrUpdateItemResponse(CreateOrUpdateItemResponse value) {
        return new JAXBElement<CreateOrUpdateItemResponse>(_CreateOrUpdateItemResponse_QNAME, CreateOrUpdateItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncome }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncome")
    public JAXBElement<GetIncome> createGetIncome(GetIncome value) {
        return new JAXBElement<GetIncome>(_GetIncome_QNAME, GetIncome.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBasketResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteBasketResponse")
    public JAXBElement<DeleteBasketResponse> createDeleteBasketResponse(DeleteBasketResponse value) {
        return new JAXBElement<DeleteBasketResponse>(_DeleteBasketResponse_QNAME, DeleteBasketResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLossByPeriodResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getLossByPeriodResponse")
    public JAXBElement<GetLossByPeriodResponse> createGetLossByPeriodResponse(GetLossByPeriodResponse value) {
        return new JAXBElement<GetLossByPeriodResponse>(_GetLossByPeriodResponse_QNAME, GetLossByPeriodResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVendorsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getVendorsResponse")
    public JAXBElement<GetVendorsResponse> createGetVendorsResponse(GetVendorsResponse value) {
        return new JAXBElement<GetVendorsResponse>(_GetVendorsResponse_QNAME, GetVendorsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBaskets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBaskets")
    public JAXBElement<GetBaskets> createGetBaskets(GetBaskets value) {
        return new JAXBElement<GetBaskets>(_GetBaskets_QNAME, GetBaskets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasket")
    public JAXBElement<GetBasket> createGetBasket(GetBasket value) {
        return new JAXBElement<GetBasket>(_GetBasket_QNAME, GetBasket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBasket }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteBasket")
    public JAXBElement<DeleteBasket> createDeleteBasket(DeleteBasket value) {
        return new JAXBElement<DeleteBasket>(_DeleteBasket_QNAME, DeleteBasket.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBasketsOfActualMonthResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getBasketsOfActualMonthResponse")
    public JAXBElement<GetBasketsOfActualMonthResponse> createGetBasketsOfActualMonthResponse(GetBasketsOfActualMonthResponse value) {
        return new JAXBElement<GetBasketsOfActualMonthResponse>(_GetBasketsOfActualMonthResponse_QNAME, GetBasketsOfActualMonthResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomesByCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomesByCategoryResponse")
    public JAXBElement<GetIncomesByCategoryResponse> createGetIncomesByCategoryResponse(GetIncomesByCategoryResponse value) {
        return new JAXBElement<GetIncomesByCategoryResponse>(_GetIncomesByCategoryResponse_QNAME, GetIncomesByCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateVendor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateVendor")
    public JAXBElement<CreateOrUpdateVendor> createCreateOrUpdateVendor(CreateOrUpdateVendor value) {
        return new JAXBElement<CreateOrUpdateVendor>(_CreateOrUpdateVendor_QNAME, CreateOrUpdateVendor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteIncome }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteIncome")
    public JAXBElement<DeleteIncome> createDeleteIncome(DeleteIncome value) {
        return new JAXBElement<DeleteIncome>(_DeleteIncome_QNAME, DeleteIncome.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPaymentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getPaymentsResponse")
    public JAXBElement<GetPaymentsResponse> createGetPaymentsResponse(GetPaymentsResponse value) {
        return new JAXBElement<GetPaymentsResponse>(_GetPaymentsResponse_QNAME, GetPaymentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "setUser")
    public JAXBElement<SetUser> createSetUser(SetUser value) {
        return new JAXBElement<SetUser>(_SetUser_QNAME, SetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIncomeByPeriodResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getIncomeByPeriodResponse")
    public JAXBElement<GetIncomeByPeriodResponse> createGetIncomeByPeriodResponse(GetIncomeByPeriodResponse value) {
        return new JAXBElement<GetIncomeByPeriodResponse>(_GetIncomeByPeriodResponse_QNAME, GetIncomeByPeriodResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getUserByName")
    public JAXBElement<GetUserByName> createGetUserByName(GetUserByName value) {
        return new JAXBElement<GetUserByName>(_GetUserByName_QNAME, GetUserByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "deleteUserResponse")
    public JAXBElement<DeleteUserResponse> createDeleteUserResponse(DeleteUserResponse value) {
        return new JAXBElement<DeleteUserResponse>(_DeleteUserResponse_QNAME, DeleteUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCategoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "getCategoryResponse")
    public JAXBElement<GetCategoryResponse> createGetCategoryResponse(GetCategoryResponse value) {
        return new JAXBElement<GetCategoryResponse>(_GetCategoryResponse_QNAME, GetCategoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOrUpdateIncomeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://onlinebudget.budget.de/", name = "createOrUpdateIncomeResponse")
    public JAXBElement<CreateOrUpdateIncomeResponse> createCreateOrUpdateIncomeResponse(CreateOrUpdateIncomeResponse value) {
        return new JAXBElement<CreateOrUpdateIncomeResponse>(_CreateOrUpdateIncomeResponse_QNAME, CreateOrUpdateIncomeResponse.class, null, value);
    }

}
