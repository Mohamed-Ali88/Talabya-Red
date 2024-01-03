package com.aliProApp.aliPro_Paints.Activities;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aliProApp.aliPro_Paints.R;


public class NoInterNet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barColor(R.color.white);
        setContentView(R.layout.activity_no_inter_net);
    }

    private void barColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

}