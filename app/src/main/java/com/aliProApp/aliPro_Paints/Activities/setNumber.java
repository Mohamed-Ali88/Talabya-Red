package com.aliProApp.aliPro_Paints.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.aliProApp.aliPro_Paints.R;

public class setNumber extends AppCompatActivity {
    EditText numberEdText;
    Button setNumberBtn, faceBtn;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_number);
        getIds();
        onWriting();
        onClick();
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setNumber.this, registerActivity.class);
                intent.putExtra("phone",numberEdText.getText().toString().trim() );
                startActivity(intent);
                finish();
            }

        });
    }

    private void onWriting() {
        numberEdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numberEdText.getText().length() != 11) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.white));
                    ViewCompat.setBackgroundTintList(numberEdText, colorStateList);
                    setNumberBtn.setVisibility(View.GONE);
                    faceBtn.setVisibility(View.VISIBLE);
                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(numberEdText, colorStateList);
                    faceBtn.setVisibility(View.GONE);
                    setNumberBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getIds() {
        faceBtn = findViewById(R.id.faceBtn);
        numberEdText = findViewById(R.id.set_new_number);
        setNumberBtn = findViewById(R.id.start_signUp_btn);
        back = findViewById(R.id.back);
    }
}