package com.aliProApp.aliPro_Paints.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Adapter.OrdersAdapter;
import com.aliProApp.aliPro_Paints.Domain.ordersDomain;
import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersFragment extends Fragment {
    RecyclerView orderedStates;
    TextView totalOrderedPrice, totalOrderedNumber;
    LinearLayout ll1;
    RelativeLayout orderConst;
    Animation fromBottom;
    ArrayList<ordersDomain> ordersDomainArrayList = new ArrayList<>();
    SharedPreferences userPref;
    int totalOrders = 0;
    int totalPrice = 0;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout ly;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_orders, container, false);
        getIds(rootView);
        setAnimation();
        getOrderedStates();
        getTopDate();
        return rootView;
    }

    private void setAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.frombottom);
        orderConst.startAnimation(fromBottom);
    }

    private void setShimmer(int i) {
        if (i == 1) {
            ly.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
        } else {
            ly.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void getTopDate() {

    }

    private void getOrderedStates() {
        setShimmer(1);
        int clientId = userPref.getInt("id", 0);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.clientOrders + "/" + clientId, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.length() == 0) {
                    setShimmer(0);
                } else {
                    String id = jsonObject.getString("id");
                    String state = jsonObject.getString("status");
                    String price = jsonObject.getString("totalprice");
                    String date = jsonObject.getString("created_at");
                    ordersDomainArrayList.add(new ordersDomain(id,price ,state , date));
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                    orderedStates.setLayoutManager(layoutManager);
                    OrdersAdapter catAdapter = new OrdersAdapter(ordersDomainArrayList);
                    orderedStates.setAdapter(catAdapter);
                    orderedStates.setHasFixedSize(true);
                    totalOrderedNumber.setText("("+state+" طلبات)");
                    totalOrderedPrice.setText("("+price+" ج م)");
                    setShimmer(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
                setShimmer(0);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void getIds(ViewGroup rootView) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        orderedStates = rootView.findViewById(R.id.ordered_state);
        orderConst = rootView.findViewById(R.id.orderConst);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmer_view);
        ly = rootView.findViewById(R.id.ly);
        ll1 = rootView.findViewById(R.id.ll1);
        totalOrderedPrice = rootView.findViewById(R.id.total_ordered_price_2);
        totalOrderedNumber = rootView.findViewById(R.id.total_ordered_number_2);
    }

    private void trydesign() {
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل", "5000 ج.م", "12/5/2012"));
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل", "5000 ج.م", "12/5/2012"));
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل", "5000 ج.م", "12/5/2012"));
        ordersDomainArrayList.add(new ordersDomain("T43dDD", "تم التويل", "5000 ج.م", "12/5/2012"));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        orderedStates.setLayoutManager(layoutManager);
        OrdersAdapter catAdapter = new OrdersAdapter(ordersDomainArrayList);
        orderedStates.setAdapter(catAdapter);
        orderedStates.setHasFixedSize(true);

    }

}