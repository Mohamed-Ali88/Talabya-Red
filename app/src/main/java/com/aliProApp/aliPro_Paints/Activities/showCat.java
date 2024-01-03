package com.aliProApp.aliPro_Paints.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Adapter.CatSubAdapter;
import com.aliProApp.aliPro_Paints.Adapter.productHorizontalAdapter;
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
import java.util.Timer;
import java.util.TimerTask;

public class showCat extends AppCompatActivity {
    RecyclerView categories, promotions;
    comCatDomain object;
    TextView proTitle, pageTitle,no_items;
    ImageView back;
    LinearLayout ly;
    ShimmerFrameLayout shimmerFrameLayout;
    ArrayList<comCatDomain> catArrayList = new ArrayList<>();
    ArrayList<Product> horizontalProductArrayList = new ArrayList<>();
    SharedPreferences userPref;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cat);
        getIds();
        getBundle();
        onClicked();
        setViews();
    }
    private void setViews() {
        setShimmer(1);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constant.subCategories+"/"+id , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length() == 0){
                        no_items.setVisibility(View.VISIBLE);
                        setShimmer(0);
                    }else{
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObj = response.getJSONObject(i);
                            int id = jsonObj.getInt("id");
                            String title = jsonObj.getString("name");
                            String Image = jsonObj.getString("category_image");
                            catArrayList.add(new comCatDomain(title, Image, id));
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(showCat.this, 3);
                        categories.setLayoutManager(layoutManager);
                        CatSubAdapter catAdapter = new CatSubAdapter(catArrayList);
                        categories.setAdapter(catAdapter);
                        categories.setHasFixedSize(true);
                        setShimmer(0);
                        no_items.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    no_items.setText(e.getMessage()+"");
                    no_items.setVisibility(View.VISIBLE);
                    setShimmer(0);

                }
            }
        }, e -> {
            e.printStackTrace();
            no_items.setText(e.getMessage()+"");
            no_items.setVisibility(View.VISIBLE);
            setShimmer(0);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String Token = userPref.getString("token", "");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + Token);
                return map;
            }
        };
        Volley.newRequestQueue(showCat.this).add(request);

        // promotion
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Constant.offerProducts + "/{" + "???? id ????" + "}", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);
                        String title = jsonObj.getString("name");
                        int id = jsonObj.getInt("id");
                        String Image = jsonObj.getString("category_image");
                        //     horizontalProductArrayList.add(new Product(id,title, Image, 12, 310.0, 500.0));
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(showCat.this, LinearLayoutManager.HORIZONTAL, false);
                    promotions.setLayoutManager(linearLayoutManager);
                    productHorizontalAdapter productHorizontalAdapter = new productHorizontalAdapter(showCat.this, horizontalProductArrayList);
                    promotions.setAdapter(productHorizontalAdapter);
                    LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
                    linearSnapHelper.attachToRecyclerView(promotions);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (productHorizontalAdapter.getItemCount() - 1)) {
                                linearLayoutManager.smoothScrollToPosition(promotions, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                            } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (productHorizontalAdapter.getItemCount() - 1)) {
                                linearLayoutManager.smoothScrollToPosition(promotions, new RecyclerView.State(), 0);

                            }
                        }
                    }, 0, 3000);


                } catch (Exception e) {
                    e.printStackTrace();
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
        Volley.newRequestQueue(showCat.this).add(request1);


    }

    private void getBundle() {
        object = (comCatDomain) getIntent().getSerializableExtra("object");
        id = object.getId();
        pageTitle.setText(object.getTitle());
    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        categories = findViewById(R.id.cats);
        no_items = findViewById(R.id.no_items);
        ly = findViewById(R.id.showCatLy);
        shimmerFrameLayout = findViewById(R.id.shimmer_view);
        promotions = findViewById(R.id.promotion_cats);
        proTitle = findViewById(R.id.tvPromotion);
        back = findViewById(R.id.backCat);
        pageTitle = findViewById(R.id.page_title_cats);
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

    private void onClicked() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}