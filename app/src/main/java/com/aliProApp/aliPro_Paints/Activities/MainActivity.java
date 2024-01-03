package com.aliProApp.aliPro_Paints.Activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aliProApp.aliPro_Paints.Fragments.ProfileFragment;
import com.aliProApp.aliPro_Paints.Fragments.CartFragment;
import com.aliProApp.aliPro_Paints.Fragments.HomeFragment;
import com.aliProApp.aliPro_Paints.Fragments.OrdersFragment;
import com.aliProApp.aliPro_Paints.Fragments.WalletFragment;
import com.aliProApp.aliPro_Paints.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNav;
    LinearLayout cartLy;
    TextView pageTitle, cartPrice;
    ImageView gotoCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIds();
        bottomNavigationStuff();
    }

    private void getIds() {
        pageTitle = findViewById(R.id.page_title_product);
        bottomNav = findViewById(R.id.bottomNav);
        cartLy = findViewById(R.id.cartLy);
        cartPrice = findViewById(R.id.cart_price);
    }


    private void bottomNavigationStuff() {
        bottomNav.add(new MeowBottomNavigation.Model(4, R.drawable.wallet));
        bottomNav.add(new MeowBottomNavigation.Model(5, R.drawable.credit_card));
        bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.shopping_cart));
        bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.user));


        bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                if (item.getId() == 1) {
                    fragment = new CartFragment();
                    setPageTitle("العربة");
                    barColor(R.color.white);
                } else if (item.getId() == 2) {
                    fragment = new ProfileFragment();
                    setPageTitle("الملف الشخصي");
                    barColor(R.color.white);
                } else if (item.getId() == 3) {
                    fragment = new HomeFragment();
                    barColor(R.color.white);
                    setPageTitle("الصفحة الرئيسية");
                } else if (item.getId() == 4) {
                    fragment = new WalletFragment();
                    barColor(R.color.white);
                    setPageTitle("المحفظة");
                } else if (item.getId() == 5) {
                    fragment = new OrdersFragment();
                    barColor(R.color.white);
                    setPageTitle("مشترياتي");
                }
                loadFragment(fragment);
            }
        });

        bottomNav.show(3, true);

        bottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment, null)
                .commit();
    }

    private void barColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

    private void setPageTitle(String title) {
        pageTitle.setText(title);
    }
}