package com.aliProApp.aliPro_Paints.Adapter;

import android.content.Context;
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

public class productHorizontalAdapter extends RecyclerView.Adapter<productHorizontalAdapter.ViewHolder> {

    Context context;
    private ArrayList<Product> products;

    public productHorizontalAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public productHorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_horizontal, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull productHorizontalAdapter.ViewHolder holder, int position) {
        holder.title.setText(products.get(position).getTitle());
        if (products.get(position).getDiscountPrice() == 0) {
            holder.price.setVisibility(View.VISIBLE);
            holder.discountCons.setVisibility(View.GONE);
            holder.saleTape.setVisibility(View.GONE);
        } else {
            holder.price.setVisibility(View.VISIBLE);
            holder.saleTape.setVisibility(View.VISIBLE);
            holder.discountCons.setVisibility(View.VISIBLE);
        }
        holder.price.setText(products.get(position).getPrice() + " ج.م");
        holder.discountPrice.setText(products.get(position).getDiscountPrice() + " ج.م");
        holder.amount.setText(products.get(position).getDescription() );
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
        TextView title, price, discountPrice, amount;
        ImageView productPic;
        Button add;
        ConstraintLayout discountCons,saleTape;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_title_horizontal);
            saleTape = itemView.findViewById(R.id.sale_tape);
            discountCons = itemView.findViewById(R.id.discount_cons_horizontal);
            price = itemView.findViewById(R.id.product_price_basic_horizontal);
            discountPrice = itemView.findViewById(R.id.product_price_sale_horizontal);
            amount = itemView.findViewById(R.id.product_amount_horizontal);
            productPic = itemView.findViewById(R.id.product_img_horizontal);
            add = itemView.findViewById(R.id.add_product_horizontal);
        }
    }

}
