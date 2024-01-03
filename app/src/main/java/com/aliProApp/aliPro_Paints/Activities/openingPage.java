package com.aliProApp.aliPro_Paints.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aliProApp.aliPro_Paints.R;

public class openingPage extends AppCompatActivity {
    TextView newUser,oldUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_page);
        getIds();
        onClicked();

    }

    private void onClicked() {
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(openingPage.this,setNumber.class));
            }
        });
        oldUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(openingPage.this,alreadyLogedIn.class));
            }
        });
    }

    private void getIds() {
        newUser = findViewById(R.id.new_user);
        oldUser = findViewById(R.id.old_user);
    }
}