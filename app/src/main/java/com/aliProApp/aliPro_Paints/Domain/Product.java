package com.aliProApp.aliPro_Paints.Domain;

import java.io.Serializable;

public class Product implements Serializable {
    private int productid;
    private String title;
    private String pic;
    private String description;
    private Double price;
    private Double discountPrice;
    private int quantity;

    public Product(int productid, String title, String pic, String description, Double price, Double discountPrice) {
        this.productid = productid;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

