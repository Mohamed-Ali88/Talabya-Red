package com.aliProApp.aliPro_Paints.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.aliProApp.aliPro_Paints.Domain.itemDomain;
import com.aliProApp.aliPro_Paints.Helper.Constant;
import com.aliProApp.aliPro_Paints.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class registerActivity extends AppCompatActivity {

    private final int FINE_PERMISSION_CODE = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText custName, shopName, custAddress, custArea, passwordId;
    TextView title_product_page, errorName1, errorName2, errorshop1, errorshop2, errorAddress1, errorAddress2, errorArea1, errorArea2, errorNationalID1, errorNationalID2;
    Spinner govSpinner, citySpinner, typeSpinner;
    Button sendAccountDate;
    ProgressBar progressBar;
    ImageView back;
    SharedPreferences userPref;
    double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getIds();
        //  setRegions();
        onWriting();
        setViews();
        onClicked();
        setView3();
        checkLocation();
        trySetRegions();
    }

    private void onClicked() {
        govSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = govSpinner.getSelectedItem().toString();
                try {
                    String jsonDateString = readJSONDataFromFile(R.raw.governorates);
                    JSONArray jsonArray = new JSONArray(jsonDateString);
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject itemObj = jsonArray.getJSONObject(i1);
                        String value = itemObj.getString("governorate_name_ar");
                        if (selected.equals(value)) {
                        }
                    }
                } catch (Exception e) {
                    //do no thing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sendAccountDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String governorate = govSpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();
                String shopType = typeSpinner.getSelectedItem().toString();
                String name = custName.getText().toString();
                String area = custArea.getText().toString();
                String shop = shopName.getText().toString();
                String address = custAddress.getText().toString();
                String password = passwordId.getText().toString();
                if (name.isEmpty() || name.length() < 4) {
                    return;
                }
                if (password.length() < 6) {
                    return;
                }
                if (area.isEmpty() || area.length() < 4) {
                    return;
                }
                if (shopType.isEmpty() || shopType.length() < 3) {
                    return;
                }
                if (shop.isEmpty() || shop.length() < 4) {
                    return;
                }
                if (address.isEmpty() || address.length() < 4) {
                    return;
                }
                if (lat == 0.0 || lon == 0.0) {
                    getLastLocation();
                    return;
                }
                String allAddress = governorate + " : " + city + " : " + area + " : " + address + " : " + shopType + " : " + lat + " : " + lon;
                register(name, allAddress, password);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void register(String name, String address, String password) {
        Bundle extras = getIntent().getExtras();
        String phone = extras.getString("phone");

        StringRequest request = new StringRequest(Request.Method.POST, Constant.register, response -> {
            try {
                login(phone, password);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast("تم استخدام هذه البيانات من قبل");
            error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("password", password);
                map.put("role", "1");
                map.put("phonenumber", phone);
                map.put("address", address);
                return map;
            }
        };
        progressBar.setVisibility(View.GONE);
        Volley.newRequestQueue(this).add(request);
    }

    private void login(String phone, String password) {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.login, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONObject jsonUser = object.getJSONObject("user");
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userPref.edit();
                editor.putString("token", object.getString("token"));
                Log.e("token", object.getString("token"));
                editor.putBoolean("isLogged", true);
                //userData
                editor.putInt("id", jsonUser.getInt("id"));
                editor.putString("name", jsonUser.getString("name"));
                editor.putString("phoneNumber", jsonUser.getString("phonenumber"));
                editor.putInt("role", jsonUser.getInt("role"));
                editor.putString("address", jsonUser.getString("address"));
                editor.apply();

                Toast("تم تسجيل الدخول بنجاح");
                startActivity(new Intent(registerActivity.this, IntroActivity.class));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast("تاكد من صحة البيانات");
            progressBar.setVisibility(View.GONE);
            error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("phonenumber", phone);
                map.put("password", password);
                return map;
            }
        };
        progressBar.setVisibility(View.GONE);
        Volley.newRequestQueue(this).add(request);
    }

    private void setViews() {
        ArrayList<itemDomain> contacts = new ArrayList<>();
        try {
            String jsonDateString = readJSONDataFromFile(R.raw.governorates);
            JSONArray jsonArray = new JSONArray(jsonDateString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObj = jsonArray.getJSONObject(i);
                String value = itemObj.getString("governorate_name_ar");
                itemDomain itemDomain = new itemDomain(value);
                contacts.add(itemDomain);
            }
            ArrayAdapter<itemDomain> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            govSpinner.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setRegions() {
        ArrayList<itemDomain> contacts = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.getSubRegion, response -> {
            try {
                JSONArray array = new JSONArray(response);
                Toast(array.length() + "");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    String value = jsonObj.getString("region_name");
                    int id = jsonObj.getInt("id");
                    contacts.add(new itemDomain(value));
                }
                ArrayAdapter<itemDomain> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(adapter2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("government_name", "القاهرة");
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void setSupRegions() {
        ArrayList<itemDomain> contacts = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.getSubRegion, response -> {
            try {
                JSONObject jsonObj = new JSONObject(response);
                String value = jsonObj.getString("region_name");
                itemDomain itemDomain = new itemDomain(value);
                contacts.add(itemDomain);
                ArrayAdapter<itemDomain> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(adapter2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                return super.getParams();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

    private void setViews2(String id) {
        ArrayList<itemDomain> contacts = new ArrayList<>();
        try {
            String jsonDateString = readJSONDataFromFile(R.raw.cities);
            JSONArray jsonArray = new JSONArray(jsonDateString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObj1 = jsonArray.getJSONObject(i);
                String itemObj2 = itemObj1.getString("governorate_id");
                if (itemObj2.equals(id)) {
                    String value = itemObj1.getString("city_name_ar");
                    itemDomain itemDomain = new itemDomain(value);
                    contacts.add(itemDomain);
                }
            }
            ArrayAdapter<itemDomain> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(adapter2);

        } catch (Exception e) {
            title_product_page.setText(e.getMessage() + "");
        }
    }

    private void setView3() {
        ArrayList<itemDomain> contacts = new ArrayList<>();
        itemDomain itemDomain = new itemDomain("سوبر ماركت");
        contacts.add(itemDomain);
        ArrayAdapter<itemDomain> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);


    }

    private String readJSONDataFromFile(int file) throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            String jsonString = null;
            inputStream = getResources().openRawResource(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);

            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }

    private void onWriting() {
        custName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (custName.getText().length() < 4) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(custName, colorStateList);
                    errorName1.setVisibility(View.VISIBLE);
                    errorName2.setVisibility(View.VISIBLE);
                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(custName, colorStateList);
                    errorName1.setVisibility(View.GONE);
                    errorName2.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        shopName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (shopName.getText().length() < 3) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(shopName, colorStateList);
                    errorshop1.setVisibility(View.VISIBLE);
                    errorshop2.setVisibility(View.VISIBLE);
                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(shopName, colorStateList);
                    errorshop1.setVisibility(View.GONE);
                    errorshop2.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                ViewCompat.setBackgroundTintList(custName, colorStateList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        custAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (custAddress.getText().length() < 4) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(custAddress, colorStateList);
                    errorAddress1.setVisibility(View.VISIBLE);
                    errorAddress2.setVisibility(View.VISIBLE);
                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(custAddress, colorStateList);
                    errorAddress1.setVisibility(View.GONE);
                    errorAddress2.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                ViewCompat.setBackgroundTintList(custName, colorStateList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        custArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (custArea.getText().length() < 4) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(custArea, colorStateList);
                    errorArea1.setVisibility(View.VISIBLE);
                    errorArea2.setVisibility(View.VISIBLE);
                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(custArea, colorStateList);
                    errorArea1.setVisibility(View.GONE);
                    errorArea2.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                ViewCompat.setBackgroundTintList(custName, colorStateList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordId.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordId.getText().length() < 6) {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    ViewCompat.setBackgroundTintList(passwordId, colorStateList);
                    errorNationalID1.setVisibility(View.VISIBLE);
                    errorNationalID2.setVisibility(View.VISIBLE);
                } else {
                    ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.primary));
                    ViewCompat.setBackgroundTintList(passwordId, colorStateList);
                    errorNationalID1.setVisibility(View.GONE);
                    errorNationalID2.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.black));
                ViewCompat.setBackgroundTintList(custName, colorStateList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        custName = findViewById(R.id.custName);
        progressBar = findViewById(R.id.Pb);
        back = findViewById(R.id.back);
        passwordId = findViewById(R.id.password);
        sendAccountDate = findViewById(R.id.sendAccountDate);
        title_product_page = findViewById(R.id.title_product_page);
        shopName = findViewById(R.id.shopName);
        custAddress = findViewById(R.id.custAddress);
        custArea = findViewById(R.id.area);
        errorName1 = findViewById(R.id.tv1);
        errorName2 = findViewById(R.id.tv2);
        errorshop1 = findViewById(R.id.tv3);
        errorshop2 = findViewById(R.id.tv4);
        errorAddress1 = findViewById(R.id.tv7);
        errorAddress2 = findViewById(R.id.tv8);
        errorArea1 = findViewById(R.id.tv5);
        errorArea2 = findViewById(R.id.tv6);
        govSpinner = findViewById(R.id.custGover);
        citySpinner = findViewById(R.id.custCity);
        typeSpinner = findViewById(R.id.shopType);
        errorNationalID1 = findViewById(R.id.tv9);
        errorNationalID2 = findViewById(R.id.tv10);

    }

    private void checkLocation() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(view);
            final AlertDialog test = dialog.create();
            Button cancel = (Button) view.findViewById(R.id.cancel);
            Button open = (Button) view.findViewById(R.id.ok);
            cancel.setOnClickListener(new android.view.View.OnClickListener() {
                public void onClick(View v) {
                    registerActivity.this.finish();
                }
            });
            open.setOnClickListener(new android.view.View.OnClickListener() {
                public void onClick(View v) {
                    registerActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    test.dismiss();
                    getLastLocation();
                }
            });
            test.setCancelable(false);
            test.show();

        } else {
            getLastLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast("Required Permission");
                lat = 0.0;
                lon = 0.0;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    } else {
                        checkLocation();
                    }
                }
            });
        } else {
            askPermission();
        }


    }

    private void askPermission() {
        ActivityCompat.requestPermissions(registerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
    }

    public void Toast(String Message) {
        new StyleableToast.Builder(registerActivity.this).text(Message).textColor(Color.WHITE).backgroundColor(getResources().getColor(R.color.colorAccent)).show();
    }

    public void getSubRegions(int id) {
        ArrayList<itemDomain> contacts = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.getSubRegion + "/" + id, response -> {
            try {
                JSONObject jsonObj = new JSONObject(response);
                String value = jsonObj.getString("name");
                itemDomain itemDomain = new itemDomain(value);
                contacts.add(itemDomain);
                ArrayAdapter<itemDomain> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(adapter2);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void trySetRegions() {
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<itemDomain> contacts = new ArrayList<>();
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("government_name", "القاهرة");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();
        Log.e("fasdfasdfadsd", mRequestBody);
        StringRequest request = new StringRequest(Request.Method.GET, Constant.getSubRegion, response -> {
            try {
                JSONArray array = new JSONArray(response);
                Toast.makeText(registerActivity.this, array.length() + "", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject itemObj1 = array.getJSONObject(i);
                    String value = itemObj1.getString("region_name");
                    itemDomain itemDomain = new itemDomain(value);
                    contacts.add(itemDomain);
                }
                ArrayAdapter<itemDomain> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, contacts);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(adapter2);
                progressBar.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> error.printStackTrace()) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null :
                            mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}