package com.aliProApp.aliPro_Paints.Helper;

public class Constant {
    public static final String URL = "https://www.orderr.website/api";
    public static final String TypeOfUser = "/client";

    public static final String login = URL + "/login";
    public static final String logOut = URL + "/logout";
    public static final String register = URL + "/storeClient";
//    public static final String showUser = URL + TypeOfUser + "/showClient";

    public static final String companies = URL + TypeOfUser + "/companies";
    public static final String departments = URL + TypeOfUser + "/departments";
    public static final String subCategories = URL + TypeOfUser + "/departmentCategories";
    public static final String companyProduct = URL + TypeOfUser + "/CompanyProducts";
    public static final String categoryProduct = URL + TypeOfUser + "/CategoryProducts";
    public static final String offerProducts = URL + "/showOffer";
    public static final String makeOrder = URL + TypeOfUser + "/MakeOrder";
    public static final String clientOrders = URL + TypeOfUser + "/showOrder";
    public static final String getSubRegion = URL + "/government-regions";

}
