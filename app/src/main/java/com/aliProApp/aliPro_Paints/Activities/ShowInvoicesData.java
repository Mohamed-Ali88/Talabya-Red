package com.aliProApp.aliPro_Paints.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Domain.Product;
import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowInvoicesData extends AppCompatActivity {

    RecyclerView invoicesProducts;
    SharedPreferences userPref;
    ArrayList<Product> ordersDomainArrayList = new ArrayList<>();
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invoices_data);
        getIds();
    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        invoicesProducts = findViewById(R.id.invoicesProducts);
    }

    public void getBundle() {
        try {
        //    object = (invoicesDomain) getIntent().getSerializableExtra("object");

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public  void getData(){
        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.companies, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String v1 = jsonObj.getString("name");
                    String v2 = jsonObj.getString("name");
                    String v3 = jsonObj.getString("name");
                    String v4 = jsonObj.getString("name");
             //       ordersDomainArrayList.add(new Product(id,v1, v2, v3,v4,));
                }
                invoicesProducts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
           //     ComAdapter comAdapter = new ComAdapter(ordersDomainArrayList);
       //         invoicesProducts.setAdapter(comAdapter);
                invoicesProducts.setHasFixedSize(true);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;

            }

        };
      //  Volley.newRequestQueue(getContext()).add(request2);
    }

}