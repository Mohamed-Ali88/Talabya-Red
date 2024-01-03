package com.aliProApp.aliPro_Paints.Domain;

public class OrderDetailsData {

    String productid ,price;
    private int quantity;

    public OrderDetailsData(String price,String productid) {
        this.productid = productid;
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
