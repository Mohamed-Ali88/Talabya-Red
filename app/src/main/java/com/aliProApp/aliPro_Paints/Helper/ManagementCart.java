package com.aliProApp.aliPro_Paints.Helper;

import android.content.Context;

import com.aliProApp.aliPro_Paints.Domain.OrderDetailsData;
import com.aliProApp.aliPro_Paints.Domain.Product;
import com.aliProApp.aliPro_Paints.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(Product item) {
        ArrayList<Product> listFood = getListCart();
        Boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                n = i;
                break;
            }

        }
        if (existAlready) {
            listFood.get(n).setQuantity(item.getQuantity());
        } else {
            listFood.add(item);
        }
        tinyDB.putListObject("cardList", listFood);
    }
    public void insertFood2(OrderDetailsData item) {
        ArrayList<OrderDetailsData> listFood = getListCart2();
        Boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getProductid().equals(item.getProductid())) {
                n = i;
                break;
            }

        }
        if (existAlready) {
            listFood.get(n).setQuantity(item.getQuantity());
        } else {
            listFood.add(item);
        }
        tinyDB.putListObject2("cardList2", listFood);
    }

    public ArrayList<Product> getListCart() {
        return tinyDB.getListObject("cardList");

    }

    public ArrayList<OrderDetailsData> getListCart2() {
        return tinyDB.getListObject2("cardList2");

    }


    public void minusNumberFood(ArrayList<Product> listFood,ArrayList<OrderDetailsData> listFood2, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listFood.get(position).getQuantity() == 1) {
            listFood.remove(position);
        } else {
            listFood.get(position).setQuantity(listFood.get(position).getQuantity() - 1);
        }
        if (listFood2.get(position).getQuantity() == 1) {
            listFood2.remove(position);
        } else {
            listFood2.get(position).setQuantity(listFood2.get(position).getQuantity() - 1);
        }
        tinyDB.putListObject("cardList", listFood);
        tinyDB.putListObject2("cardList2", listFood2);
        changeNumberItemsListener.Changed();
    }

    public void plusNumberFood(ArrayList<Product> listFood,ArrayList<OrderDetailsData> listFood2, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listFood.get(position).setQuantity(listFood.get(position).getQuantity() + 1);
        listFood2.get(position).setQuantity(listFood2.get(position).getQuantity() + 1);
        tinyDB.putListObject("cardList", listFood);
        tinyDB.putListObject2("cardList2", listFood2);
        changeNumberItemsListener.Changed();

    }


    public Double getTotalFee() {

        ArrayList<Product> listFeed2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listFeed2.size(); i++) {
            fee = fee + (listFeed2.get(i).getPrice() * listFeed2.get(i).getQuantity());
        }
        return fee;
    }

    public ArrayList getDataToServer() {

        ArrayList<OrderDetailsData> listFeed2 = getListCart2();
        return listFeed2;
    }


}
