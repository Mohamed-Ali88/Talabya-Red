package com.aliProApp.aliPro_Paints.Domain;

public class mmm {
    String clientid,totalprice;
    String orderDetail;

    public mmm(String clientid, String totalprice, String orderDetail) {
        this.clientid = clientid;
        this.totalprice = totalprice;
        this.orderDetail = orderDetail;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }
}
