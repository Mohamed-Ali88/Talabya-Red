package com.aliProApp.aliPro_Paints.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aliProApp.aliPro_Paints.R;


public class WalletFragment extends Fragment {
    TextView walletAmountMoney;
    Animation fromBottom;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_wallet, container, false);
        getIds(rootView);
        setAnimation();
        return rootView;
    }

    private void getIds(ViewGroup rootView) {
        walletAmountMoney = rootView.findViewById(R.id.wallet_money_amount);
        relativeLayout = rootView.findViewById(R.id.walletConst);

    }

    private void setAnimation() {
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.frombottom);
        relativeLayout.startAnimation(fromBottom);
    }


}