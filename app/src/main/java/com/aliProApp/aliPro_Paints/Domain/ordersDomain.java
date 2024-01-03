package com.aliProApp.aliPro_Paints.Domain;

public class ordersDomain {
    String orderId,orderState,totalOrderPrice,requestDate;

    public ordersDomain(String orderId, String orderState, String totalOrderPrice, String requestDate) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.totalOrderPrice = totalOrderPrice;
        this.requestDate = requestDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
