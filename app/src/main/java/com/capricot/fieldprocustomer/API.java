package com.capricot.fieldprocustomer;

/**
 * Created by Balaji Prabhu on 9/15/2017.
 */

public class API {

    private static String appurl="https://www.kaspontech.com/fieldpro-capricot/index.php?";

    public static final String Register_URL = appurl+"/controller_cust/register";
    public static final String LoadCompany_URL = appurl+"/controller_cust/load_company";
    public static final String LoadProduct_URL = appurl+"/controller_cust/load_product";
    public static final String LoadCategory_URL = appurl+"/controller_cust/load_category";
    public static final String LOGIN_URL = appurl+"/controller_cust/login";
    public static final String RaiseTicket_Product = appurl+"/controller_cust/load_cust_product";
    public static final String RaiseTicket_Category = appurl+"/controller_cust/load_cust_category";
    public static final String RaiseTicket_Contractortype = appurl+"/controller_cust/load_cust_service_category";
    public static final String RaiseTicket_CallCategory = appurl+"/webservice/get_customer_call_type";
    public static final String RaiseTicket = appurl+"/controller_cust/raise_tkt";
    public static final String AddProductDetails = appurl+"/controller_cust/add_product";
    public static final String LoadTickets = appurl+"/controller_cust/load_tickets";
    public static final String LoadProduct = appurl+"/controller_cust/load_customer_details";
    public static final String ContractType = appurl+"/controller_cust/load_cust_service_category";
    public static final String ModelNumber = appurl+"/controller_cust/load_model";
    public static final String AMCcontract = appurl+"/controller_cust/raise_amc";
    public static final String ForgotPassword = appurl+"/webservice/ForgotPassword_Customer";
    public static final String ChangePassword = appurl+"/webservice/change_password_cus";
    public static final String WorkType = appurl+"/webservice/get_work_type";
    public static final String ContractTyperegistration = appurl+"/webservice/get_customer_contract_type";

}
