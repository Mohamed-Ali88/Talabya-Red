package com.aliProApp.aliPro_Paints.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Adapter.CatMainAdapter;
import com.aliProApp.aliPro_Paints.Adapter.ComAdapter;
import com.aliProApp.aliPro_Paints.Adapter.productHorizontalAdapter;
import com.aliProApp.aliPro_Paints.Domain.Product;
import com.aliProApp.aliPro_Paints.Domain.comCatDomain;
import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    TextView catTitle, comTitle, proTitle;
    ImageSlider dailySale;
    ConstraintLayout homeConst;
    ScrollView scrollView;
    Animation fromBottom;
    RecyclerView companies, categories, promotions;
    ArrayList<comCatDomain> catArrayList = new ArrayList<>();
    ArrayList<comCatDomain> comArrayList = new ArrayList<>();
    ArrayList<Product> horizontalProductArrayList = new ArrayList<>();
    ArrayList<SlideModel> slideModels = new ArrayList<>();
    SharedPreferences userPref;
    ShimmerFrameLayout shimmerFrameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        getIds(rootView);
        setStartAnimation();
        setViews();
        promotions();
        setTestDesign();
        return rootView;
    }

    private void setShimmer(int i) {
        if (i == 1) {
            scrollView.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
        } else {
            scrollView.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void setTestDesign() {
//cat
//        catArrayList.add(new comCatDomain("معلبات", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/cats%2F1701268718911.jpg?alt=media&token=ce50a448-7ab2-45fd-be38-0aaca9dbb53c",1));
//        catArrayList.add(new comCatDomain("مشروبات باردة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/cats%2F1701268718902.jpg?alt=media&token=a178537d-9e86-493e-8d03-ca777d744b88",2));
//        catArrayList.add(new comCatDomain("مشروبات ساخنة", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/cats%2F1701268718891.jpg?alt=media&token=07894680-e1f1-46aa-bcb2-73b772912eef",3));
//        catArrayList.add(new comCatDomain("عصائر", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/cats%2F1701268718891.jpg?alt=media&token=07894680-e1f1-46aa-bcb2-73b772912eef",4));
//        catArrayList.add(new comCatDomain("زيت و سمن", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/cats%2F1701268718868.jpg?alt=media&token=9f0543bc-a4cf-4129-ab3b-94dab6fcfbcd",5));
//
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
//        categories.setLayoutManager(layoutManager);
//        CatMainAdapter catAdapter = new CatMainAdapter(catArrayList);
//        categories.setAdapter(catAdapter);
//        categories.setHasFixedSize(true);


        //com
//        comArrayList.add(new comCatDomain("ربيع", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121557.jpg?alt=media&token=ca894653-6232-46ac-bee2-13a85e6bf1ae", "0"));
//        comArrayList.add(new comCatDomain("لمار", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121565.jpg?alt=media&token=7f9ad904-bde0-49a0-a2be-43f0152e2c6e", "0"));
//        comArrayList.add(new comCatDomain("سن توب", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121573.jpg?alt=media&token=f465d387-270e-4b34-a026-e6a01e9fa8dc", "0"));
//        comArrayList.add(new comCatDomain("بيتي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121582.jpg?alt=media&token=789ffff7-3b25-475e-b2d8-232438b5a34b", "0"));
//        comArrayList.add(new comCatDomain("sting", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121591.jpg?alt=media&token=e4f0d6cb-8d3f-409b-acf9-31af2a0b6558", "0"));
//        comArrayList.add(new comCatDomain("صن بايتس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121601.jpg?alt=media&token=67492353-19a3-4fa0-b73b-dd72d986ba84", "0"));
//        comArrayList.add(new comCatDomain("ليبتون", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/com%2F1701269121611.jpg?alt=media&token=50dd45e8-3d8e-423e-bdbb-546233d416e0", "0"));
//
//
//        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 3);
//        companies.setLayoutManager(layoutManager1);
//        ComAdapter comAdapter = new ComAdapter(comArrayList);
//        companies.setAdapter(comAdapter);
//        companies.setHasFixedSize(true);

        //daily
//
//        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008411.jpg?alt=media&token=fe1289c3-c9c8-4ab4-afcd-7f17df12936a", ScaleTypes.FIT));
//        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", ScaleTypes.FIT));
//        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096612.jpg?alt=media&token=7a8a8065-591e-48b1-9bc6-3e56f6bb12f5", ScaleTypes.FIT));
//        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096644.jpg?alt=media&token=c8ac267d-2bb8-4a14-9f5b-65f5130cd043", ScaleTypes.FIT));
//        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D9%85%D8%B9%D9%84%D8%A8%D8%A7%D8%AA%2F1701270148140.jpg?alt=media&token=0018295d-4a05-4ffb-97f2-387013b56829", ScaleTypes.FIT));
//        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%AD%D9%84%D9%88%D9%8A%D8%A7%D8%AA%20%D8%A7%D8%B3%D9%86%D8%A7%D9%83%D8%B3%2F1701270051870.jpg?alt=media&token=ba672478-6faf-427b-bf7d-3d50d20bf8af", ScaleTypes.FIT));
//
//        dailySale.setImageList(slideModels, ScaleTypes.FIT);
//
//
        //down
        horizontalProductArrayList.add(new Product(0, "كولا", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593084.jpg?alt=media&token=5ff8f0ad-d1a3-41f1-a240-de81efc7e63c", "12", 310.0, 0.0));
        horizontalProductArrayList.add(new Product(1, "شوبس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/coldDrinks%2F1701269593117.jpg?alt=media&token=740de0ec-f5be-4358-a0c2-d42980f7e568", "12", 310.0, 0.0));
        horizontalProductArrayList.add(new Product(2, "المراعي", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096605.jpg?alt=media&token=14d15ef7-4f0d-4d9e-92e7-8e5399980df7", "12", 310.0, 500.0));
        horizontalProductArrayList.add(new Product(3, "عبور لاند", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%A7%D9%84%D8%A8%D8%A7%D9%86%2F1701270096635.jpg?alt=media&token=f09c9e98-7163-4a99-9e0a-bc4e1768c731", "12", 310.0, 500.0));
        horizontalProductArrayList.add(new Product(4, "مكس", "https://firebasestorage.googleapis.com/v0/b/alipropaints.appspot.com/o/%D8%B9%D8%B5%D8%A7%D8%A2%D8%B1%2F1701270008373.jpg?alt=media&token=79ea8538-31be-496a-ba60-5cb1c0d8b270", "12", 310.0, 500.0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        promotions.setLayoutManager(linearLayoutManager);
        productHorizontalAdapter productHorizontalAdapter = new productHorizontalAdapter(getActivity(), horizontalProductArrayList);
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


    }

    private void setStartAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.frombottom);
        homeConst.startAnimation(fromBottom);
    }

    private void setViews() {
        setShimmer(1);
        StringRequest request = new StringRequest(Request.Method.GET, Constant.departments, response -> {
            try {
                JSONArray array = new JSONArray(response);

                Toast.makeText(getContext(), array.length()+"", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String title = jsonObj.getString("name");
                    int id = jsonObj.getInt("id");
                    String Image = jsonObj.getString("department_image");
                    catArrayList.add(new comCatDomain(title, Image, id));
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                categories.setLayoutManager(layoutManager);
                CatMainAdapter catAdapter = new CatMainAdapter(catArrayList);
                categories.setAdapter(catAdapter);
                categories.setHasFixedSize(false);

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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
        //companies
        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.companies, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String title = jsonObj.getString("name");
                    int id = jsonObj.getInt("id");
                    String Image = jsonObj.getString("company_image");
                    comArrayList.add(new comCatDomain(title, Image, id));
                }
                companies.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                ComAdapter comAdapter = new ComAdapter(comArrayList);
                companies.setAdapter(comAdapter);
                companies.setHasFixedSize(true);
                setShimmer(2);


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
        Volley.newRequestQueue(getContext()).add(request2);
    }

    private void getIds(ViewGroup rootView) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        homeConst = rootView.findViewById(R.id.homeConst);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmer_view);
        scrollView = rootView.findViewById(R.id.home_scroll_View);
        dailySale = rootView.findViewById(R.id.daily_sales);
        catTitle = rootView.findViewById(R.id.cat_title);
        proTitle = rootView.findViewById(R.id.pro_title);
        comTitle = rootView.findViewById(R.id.com_title);
        promotions = rootView.findViewById(R.id.promotion_main);
        companies = rootView.findViewById(R.id.companies);
        categories = rootView.findViewById(R.id.categories_view);
    }

    private void setDailySale() {
        String URL = "---------";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray("__________");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String pic = jsonObject1.getString("---------");
                            slideModels.add(new SlideModel(pic, ScaleTypes.FIT));
                        }
                        dailySale.setImageList(slideModels, ScaleTypes.FIT);
                    } else {
                        dailySale.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "no daily date", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Daily: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void promotions() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.offerProducts + "/{" + "???? id ????" + "}", response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String title = jsonObj.getString("name");
                    int id = jsonObj.getInt("id");
                    String Image = jsonObj.getString("category_image");
                    //      horizontalProductArrayList.add(new Product(0, title, Image, 12, 310.0, 500.0));
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                promotions.setLayoutManager(linearLayoutManager);
                productHorizontalAdapter productHorizontalAdapter = new productHorizontalAdapter(getActivity(), horizontalProductArrayList);
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
        Volley.newRequestQueue(getContext()).add(request);
    }

}