package com.aliProApp.aliPro_Paints.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Adapter.cartProductAdapter;
import com.aliProApp.aliPro_Paints.Domain.mmm;
import com.aliProApp.aliPro_Paints.Helper.ManagementCart;
import com.aliProApp.aliPro_Paints.Interface.ChangeNumberItemsListener;
import com.aliProApp.aliPro_Paints.Interface.cartPrductPost;
import com.aliProApp.aliPro_Paints.R;
import com.google.gson.Gson;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {
    ConstraintLayout codeConst;
    Animation fromBottom;
    ImageView noItemsCart;
    RecyclerView orderedProducts;
    TextView totalPrice;
    LinearLayout l1, l2;
    Button makeOrder;
    private ManagementCart managementCart;
    View v1;
    ScrollView scrollerViewCart;
    RadioButton dateOne, dateTwo, dateThere;
    double itemTotal = 0.0;
    SharedPreferences userPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);
        getViews(rootView);
        setAnimation();
        getFromCart();
        calculateCart();
        Clicked();
        return rootView;
    }

    public void getViews(ViewGroup view) {
        userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
        codeConst = view.findViewById(R.id.cart_cont);
        scrollerViewCart = view.findViewById(R.id.scrollerViewCart);
        l1 = view.findViewById(R.id.linearLayout1);
        l2 = view.findViewById(R.id.linearLayout2);
        v1 = view.findViewById(R.id.v1);
        noItemsCart = view.findViewById(R.id.no_items_cart);
        orderedProducts = view.findViewById(R.id.ordered_products);
        totalPrice = view.findViewById(R.id.total_price);
        dateOne = view.findViewById(R.id.date_1);
        dateTwo = view.findViewById(R.id.date_2);
        dateThere = view.findViewById(R.id.date_3);
        makeOrder = view.findViewById(R.id.makeOrder);
        managementCart = new ManagementCart(getActivity());
    }

    private void setAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.frombottom);
        codeConst.startAnimation(fromBottom);
    }

    private void getFromCart() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        orderedProducts.setLayoutManager(linearLayoutManager);
        RecyclerView.Adapter adapter = new cartProductAdapter(managementCart.getListCart(), managementCart.getListCart2(), getContext(), new ChangeNumberItemsListener() {
            @Override
            public void Changed() {
                calculateCart();
            }
        });
        orderedProducts.setAdapter(adapter);

        if (managementCart.getListCart().isEmpty()) {
            noItemsCart.setVisibility(View.VISIBLE);
            scrollerViewCart.setVisibility(View.GONE);
        } else {
            noItemsCart.setVisibility(View.GONE);
            scrollerViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        itemTotal = Math.round((managementCart.getTotalFee() * 100.0) / 100.0);
        totalPrice.setText(itemTotal + " ج.م");
    }

    private void Clicked() {
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemTotal == 0.0) {
                    noItemsCart.setVisibility(View.VISIBLE);
                    scrollerViewCart.setVisibility(View.GONE);
                    Toast("لا يتوفر اي منتجات في عربة مشترياتك");
                } else {
                    noItemsCart.setVisibility(View.GONE);
                    scrollerViewCart.setVisibility(View.VISIBLE);
                    CheckFaceOrder();
                    Log.e("-==============", new Gson().toJson(managementCart.getListCart2()));
                }
            }
        });
    }

    private void CheckFaceOrder() {
        if (itemTotal < 3000.0) {
            showDialog("قيمة الفاتورة لم تبلغ الحد الادني للطلب", R.drawable.ic_baseline_gpp_bad_24);
        } else {
            showDialog("هل تريد تاكيد الطلب و عمل فاتورة", R.drawable.ic_baseline_check_24);
        }
    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(getContext())
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

    private void showDialog(String message, int icon) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        makeOrder();
                        dialogInterface.cancel();
                    }
                });
        alert.show();
    }

    private void makeOrder() {
        int clientId = userPref.getInt("id", 0);
        mmm mm = new mmm(clientId + "", totalPrice.getText().toString(), new Gson().toJson(managementCart.getListCart2()));
        sendNetworkrespose(mm);
//        StringRequest request = new StringRequest(Request.Method.POST, Constant.makeOrder, response -> {
//
//        }, error -> {
//            error.printStackTrace();
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                String Token = userPref.getString("token", "");
//                HashMap<String, String> map = new HashMap<>();
//                map.put("Authorization", "Bearer " + Token);
//                return map;
//            }
//
//            @Override
//            public Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("clientid", String.valueOf(clientId));
//                map.put("totalprice", totalPrice.getText().toString());
//                map.put("orderDetail", new Gson().toJson(managementCart.getListCart2()));
//                return map;
//            }
//        };
//        Volley.newRequestQueue(getActivity()).add(request);
    }

    private void sendNetworkrespose(mmm mm) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://www.orderr.website/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        cartPrductPost post = retrofit.create(cartPrductPost.class);
        Call<mmm> call = post.creatOrder(mm);
        call.enqueue(new Callback<mmm>() {
            @Override
            public void onResponse(Call<mmm> call, Response<mmm> response) {
                Log.e("ffff",response.message());
            }

            @Override
            public void onFailure(Call<mmm> call, Throwable t) {
                Log.e("fffff", t.getMessage());
            }
        });
    }

}