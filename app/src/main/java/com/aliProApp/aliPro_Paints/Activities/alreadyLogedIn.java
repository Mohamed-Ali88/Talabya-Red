package com.aliProApp.aliPro_Paints.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class alreadyLogedIn extends AppCompatActivity {

    Button loginBtn;
    EditText numberEdText,passwordED;
    TextView gotoMakeAccount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_loged_in);
        getIds();
        onClick();
    }

    private void onClick() {
        gotoMakeAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(alreadyLogedIn.this, registerActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberEdText.getText().toString();
                String pass = passwordED.getText().toString();
                if (number.length() != 11) {
                    numberEdText.setError("هذا الرقم غير صحيح");
                    return;
                }
                if (pass.isEmpty()) {
                    passwordED.setError("اكتب الرقم السري");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                logIn();
            }
        });
    }

    private void logIn() {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.login, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONObject jsonUser = object.getJSONObject("user");
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userPref.edit();
                editor.putString("token", object.getString("token"));
                editor.putBoolean("isLogged", true);
                //userData
                editor.putInt("id", jsonUser.getInt("id"));
                editor.putString("name", jsonUser.getString("name"));
                editor.putString("phoneNumber", jsonUser.getString("phonenumber"));
                editor.putInt("role", jsonUser.getInt("role"));
                editor.putString("address", jsonUser.getString("address"));
              //  editor.putString("nationalId", jsonUser.getString("nationalid"));
                editor.apply();

                Toast("تم تسجيل الدخول بنجاح");
                startActivity(new Intent(alreadyLogedIn.this, IntroActivity.class));
                finish();
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        }, error -> {
            Toast("الرقم غير مسجل لدينا");
            progressBar.setVisibility(View.GONE);
            error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("phonenumber", numberEdText.getText().toString());
                map.put("password", passwordED.getText().toString());
                return map;
            }
        };
        progressBar.setVisibility(View.GONE);
        Volley.newRequestQueue(this).add(request);
    }

    private void getIds() {
        gotoMakeAccount = findViewById(R.id.account_tv);
        numberEdText = findViewById(R.id.set_new_number);
        passwordED = findViewById(R.id.set_password);
        loginBtn = findViewById(R.id.start_signUp_btn);
        progressBar = findViewById(R.id.Pb);
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(alreadyLogedIn.this)
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

}