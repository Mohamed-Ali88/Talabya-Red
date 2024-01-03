package com.aliProApp.aliPro_Paints.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.aliProApp.aliPro_Paints.R;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        barColor(R.color.colorAccent);
    }

    public Boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        } catch (NullPointerException e) {
            return false;
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                Boolean isLoggedIn = userPref.getBoolean("isLogged", false);
                if (isLoggedIn) {
                    if (isNetworkAvailable()) {
                        Intent myIntent = new Intent(IntroActivity.this, MainActivity.class);
                        startActivity(myIntent);
                    } else {
                        startActivity(new Intent(IntroActivity.this, NoInterNet.class));
                    }
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    startActivity(new Intent(IntroActivity.this,openingPage.class));
                    finish();
                }


            }
        };
        handler.postDelayed(r, 1500);
    }

    private void barColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }


}
