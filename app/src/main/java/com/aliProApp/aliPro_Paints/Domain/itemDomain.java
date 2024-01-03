package com.aliProApp.aliPro_Paints.Domain;

public class itemDomain {
    String gov_city_name;

    public itemDomain() {
    }

    public itemDomain(String gov_city_name) {
        this.gov_city_name = gov_city_name;
    }

    public String getGov_city_name() {
        return gov_city_name;
    }

    public void setGov_city_name(String gov_city_name) {
        this.gov_city_name = gov_city_name;
    }

    @Override
    public String toString() {
        return gov_city_name;
    }
}
