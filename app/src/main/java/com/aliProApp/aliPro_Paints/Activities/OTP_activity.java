package com.aliProApp.aliPro_Paints.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.aliProApp.aliPro_Paints.R;

public class OTP_activity extends AppCompatActivity {

    Button signUpBtn, fakeBtn;
    TextView counter;
    EditText edtPasscode1, edtPasscode2, edtPasscode3, edtPasscode4, edtPasscode5, edtPasscode6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getIds();
        tryOnWriting();
        onClick();
    }

    private void tryOnWriting() {
        StringBuilder sb = new StringBuilder();
        edtPasscode1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & edtPasscode1.length() == 1) {
                    sb.append(s);
                    edtPasscode1.clearFocus();
                    edtPasscode2.requestFocus();
                    edtPasscode2.setCursorVisible(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(edtPasscode1, colorStateList);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (sb.length() == 1) {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                    ViewCompat.setBackgroundTintList(edtPasscode1, colorStateList);
                }

            }
        });
        edtPasscode2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & edtPasscode2.length() == 1) {
                    sb.append(s);
                    edtPasscode2.clearFocus();
                    edtPasscode3.requestFocus();
                    edtPasscode3.setCursorVisible(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(edtPasscode2, colorStateList);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (sb.length() == 1) {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {
                    edtPasscode1.requestFocus();
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                    ViewCompat.setBackgroundTintList(edtPasscode2, colorStateList);
                }

            }
        });
        edtPasscode3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & edtPasscode3.length() == 1) {
                    sb.append(s);
                    edtPasscode3.clearFocus();
                    edtPasscode4.requestFocus();
                    edtPasscode4.setCursorVisible(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(edtPasscode3, colorStateList);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (sb.length() == 1) {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {
                    edtPasscode2.requestFocus();
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                    ViewCompat.setBackgroundTintList(edtPasscode3, colorStateList);
                }

            }
        });
        edtPasscode4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & edtPasscode4.length() == 1) {
                    sb.append(s);
                    edtPasscode4.clearFocus();
                    edtPasscode5.requestFocus();
                    edtPasscode5.setCursorVisible(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(edtPasscode4, colorStateList);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (sb.length() == 1) {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {
                    edtPasscode3.requestFocus();
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                    ViewCompat.setBackgroundTintList(edtPasscode4, colorStateList);
                }

            }
        });
        edtPasscode5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & edtPasscode5.length() == 1) {
                    sb.append(s);
                    edtPasscode5.clearFocus();
                    edtPasscode6.requestFocus();
                    edtPasscode6.setCursorVisible(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(edtPasscode5, colorStateList);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                if (sb.length() == 1) {

                    sb.deleteCharAt(0);

                }

            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {
                    edtPasscode4.requestFocus();
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                    ViewCompat.setBackgroundTintList(edtPasscode5, colorStateList);
                }

            }
        });
        edtPasscode6.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (sb.length() == 0 & edtPasscode6.length() == 1) {
                    sb.append(s);
                    edtPasscode6.clearFocus();
                    edtPasscode6.requestFocus();
                    edtPasscode6.setCursorVisible(true);
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(edtPasscode6, colorStateList);
                    fakeBtn.setVisibility(View.GONE);
                    signUpBtn.setVisibility(View.VISIBLE);
                } else {
                    fakeBtn.setVisibility(View.VISIBLE);
                    signUpBtn.setVisibility(View.GONE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (sb.length() == 1) {
                    sb.deleteCharAt(0);
                }
            }

            public void afterTextChanged(Editable s) {
                if (sb.length() == 0) {
                    edtPasscode5.requestFocus();
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                    ViewCompat.setBackgroundTintList(edtPasscode6, colorStateList);
                }

            }
        });
    }

    private void onClick() {
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = edtPasscode1.getText().toString() + edtPasscode2.getText().toString() +
                        edtPasscode3.getText().toString() + edtPasscode4.getText().toString()
                        + edtPasscode5.getText().toString() + edtPasscode6.getText().toString();
                Toast.makeText(OTP_activity.this, otp, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OTP_activity.this, registerActivity.class));
            }
        });
    }

    private void getIds() {
        edtPasscode1 = findViewById(R.id.ed1);
        edtPasscode2 = findViewById(R.id.ed2);
        edtPasscode3 = findViewById(R.id.ed3);
        edtPasscode4 = findViewById(R.id.ed4);
        edtPasscode5 = findViewById(R.id.ed5);
        edtPasscode6 = findViewById(R.id.ed6);
        signUpBtn = findViewById(R.id.start_signUp_btn);
        fakeBtn = findViewById(R.id.faceBtn);
        counter = findViewById(R.id.counterOTP);
    }
}