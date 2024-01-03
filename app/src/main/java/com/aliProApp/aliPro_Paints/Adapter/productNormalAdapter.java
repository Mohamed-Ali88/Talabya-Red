package com.aliProApp.aliPro_Paints.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Activities.showActivityData;
import com.aliProApp.aliPro_Paints.Domain.Product;
import com.aliProApp.aliPro_Paints.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class productNormalAdapter extends RecyclerView.Adapter<productNormalAdapter.ViewHolder> {

    private int orderAmount = 0;
    private ArrayList<Product> products;

    public productNormalAdapter(ArrayList<Product> productDomain) {
        this.products = productDomain;
    }

    @NonNull
    @Override
    public productNormalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull productNormalAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText(product.getPrice() + " ج.م");
        if (product.getDiscountPrice() == 0.0) {
            holder.price.setVisibility(View.VISIBLE);
            holder.discountCons.setVisibility(View.GONE);
        } else {
            holder.price.setVisibility(View.VISIBLE);
            holder.discountCons.setVisibility(View.VISIBLE);
        }
        holder.discountPrice.setText(product.getDiscountPrice() + " ج.م");
        holder.amount.setText(product.getDescription());
        Glide.with(holder.itemView.getContext()).load(products.get(position).getPic()).into(holder.productPic);
        holder.add.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), showActivityData.class);
            intent.putExtra("object", products.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price, discountPrice, amount, orderAmount;
        public ImageView productPic;
        public Button add;
        public ConstraintLayout discountCons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_title);
            discountCons = itemView.findViewById(R.id.discount_cons);
            price = itemView.findViewById(R.id.product_price_basic);
            discountPrice = itemView.findViewById(R.id.product_price_sale);
            amount = itemView.findViewById(R.id.product_amount);
            productPic = itemView.findViewById(R.id.product_img);
            add = itemView.findViewById(R.id.add_product);
        }
    }

}
