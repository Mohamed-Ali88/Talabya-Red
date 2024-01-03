package com.aliProApp.aliPro_Paints.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Adapter.productNormalAdapter;
import com.aliProApp.aliPro_Paints.Domain.Product;
import com.aliProApp.aliPro_Paints.Domain.comCatDomain;
import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showProducts extends AppCompatActivity {

    RecyclerView products;
    ArrayList<Product> productArrayList = new ArrayList<>();
    TextView noItems, titlePage;
    comCatDomain object;
    ImageView back;
    ShimmerFrameLayout shimmerFrameLayout;
    SharedPreferences userPref;
    LinearLayout ly;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prodcuts);
        getIds();
        getBundle();
        onClicked();
        checkCatOrCom();
    }

    private void startDesign() {
//        productArrayList.add(new Product(0,"كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
//        productArrayList.add(new Product(0,"كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
//        productArrayList.add(new Product(0,"كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
//        productArrayList.add(new Product(0,"كوكاكولا ", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", 12, 300.0, 500.0));
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showProducts.this, 2);
//        products.setLayoutManager(layoutManager);
//        productNormalAdapter productNormalAdapter = new productNormalAdapter(productArrayList);
//        products.setAdapter(productNormalAdapter);
//        products.setHasFixedSize(true);
    }

    private void getBundle() {
        object = (comCatDomain) getIntent().getSerializableExtra("object2");
        titlePage.setText(object.getTitle());
        id = object.getId();
    }

    private void checkCatOrCom() {
        Bundle b = getIntent().getExtras();
        String Type = b.getString("comOrCatProducts");
        if (Type.equals("company")) {
            setCompanyProducts();
        } else if (Type.equals("category")) {
            setCategoryProducts();
        }
    }

    private void setCategoryProducts() {
        setShimmer(1);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constant.categoryProduct + "/" + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObj = response.getJSONObject(i);
                            String title = jsonObj.getString("name");
                            int id = jsonObj.getInt("id");
                            String Image = jsonObj.getString("product_image");
                            String description = jsonObj.getString("description");
                            double price = jsonObj.getDouble("price");
                            productArrayList.add(new Product(id, title, Image, description, price, 0.0));

                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showProducts.this, 2);
                        products.setLayoutManager(layoutManager);
                        productNormalAdapter productNormalAdapter = new productNormalAdapter(productArrayList);
                        products.setAdapter(productNormalAdapter);
                        products.setHasFixedSize(true);
                        setShimmer(0);


                } catch (Exception e) {
                    Log.e("eeeeee", e.getMessage());
                }
            }
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;
            }
        };
        Volley.newRequestQueue(showProducts.this).add(request);


    }

    private void setCompanyProducts() {
        setShimmer(1);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constant.companyProduct + "/" + id , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Toast.makeText(showProducts.this, response.length()+"", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObj = response.getJSONObject(i);
                            String title = jsonObj.getString("name");
                            int id = jsonObj.getInt("id");
                            String Image = jsonObj.getString("product_image");
                            String description = jsonObj.getString("description");
                            double price = jsonObj.getDouble("price");
                            productArrayList.add(new Product(id, title, Image, description, price, 0.0));

                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showProducts.this, 2);
                        products.setLayoutManager(layoutManager);
                        productNormalAdapter productNormalAdapter = new productNormalAdapter(productArrayList);
                        products.setAdapter(productNormalAdapter);
                        products.setHasFixedSize(true);
                        setShimmer(0);

                } catch (Exception e) {
                    Log.e("eeeeee", e.getMessage());
                }
            }
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;
            }
        };
        Volley.newRequestQueue(showProducts.this).add(request);


    }

    private void setShimmer(int i) {
        if(i == 1){
            ly.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
        }else{
            ly.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        products = findViewById(R.id.products);
        ly = findViewById(R.id.ly);
        shimmerFrameLayout = findViewById(R.id.shimmer_view);
        back = findViewById(R.id.back);
        titlePage = findViewById(R.id.title_product_page);
        noItems = findViewById(R.id.no_items);
    }

    private void onClicked() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}