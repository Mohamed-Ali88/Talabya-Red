package com.aliProApp.aliPro_Paints.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aliProApp.aliPro_Paints.Activities.openingPage;
import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;


public class ProfileFragment extends Fragment {
    TextView shopName, userName, address, govName, areaName, hayName, shopType, number;
    Animation fromBottom;
    LinearLayout constraint;
    Button logOut;
    SharedPreferences userPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_blank, container, false);
        findIds(rootView);
        setAnimation();
        OnClicked();
        showDataUser();
        return rootView;
    }

    private void OnClicked() {
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
    }

    private void setAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.frombottom);
        constraint.startAnimation(fromBottom);
    }

    private void LogOut() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.logOut, response -> {

            startActivity(new Intent(getContext(), openingPage.class));
            SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = userPref.edit();
            editor.putBoolean("isLogged", false);
            editor.apply();
            Toast("تم تسجيل الخروج");
            getActivity().finish();
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

    private void showDataUser() {
        try {
            String name = userPref.getString("name", "");
            String Address = userPref.getString("address", "");
            String phoneNumber = userPref.getString("phoneNumber", "");
            String[] parts = Address.split(" : ");
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];
            String part4 = parts[3];

            userName.setText(name);
            shopName.setText(name);
            number.setText(phoneNumber);
            govName.setText(part1);
            hayName.setText(part2);
            areaName.setText(part3);
            address.setText(part4);
            shopType.setText("سوبر ماركت");

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findIds(View rootView) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        userName = rootView.findViewById(R.id.user_name);
        logOut = rootView.findViewById(R.id.logOut);
        shopName = rootView.findViewById(R.id.shop_name);
        constraint = rootView.findViewById(R.id.constraint);
        number = rootView.findViewById(R.id.number);
        address = rootView.findViewById(R.id.address);
        govName = rootView.findViewById(R.id.gov_name);
        areaName = rootView.findViewById(R.id.zoon_name);
        hayName = rootView.findViewById(R.id.hay_name);
        shopType = rootView.findViewById(R.id.shop_type);
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(getContext())
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

}