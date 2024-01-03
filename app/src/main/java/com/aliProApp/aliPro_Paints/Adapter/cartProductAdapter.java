package com.aliProApp.aliPro_Paints.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Domain.OrderDetailsData;
import com.aliProApp.aliPro_Paints.Domain.Product;
import com.aliProApp.aliPro_Paints.Helper.ManagementCart;
import com.aliProApp.aliPro_Paints.Interface.ChangeNumberItemsListener;
import com.aliProApp.aliPro_Paints.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class cartProductAdapter extends RecyclerView.Adapter<cartProductAdapter.ViewHolder> {

    private ArrayList<Product> products;
    private ArrayList<OrderDetailsData> product2;
    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public cartProductAdapter(ArrayList<Product> products,ArrayList<OrderDetailsData> product2, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.products = products;
        this.product2 = product2;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public cartProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product_orderd, parent, false);
        return new ViewHolder(inflate);
    }

    private void removeAt(int position) {
        products.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, products.size());
    }

    @Override
    public void onBindViewHolder(@NonNull cartProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.title.setText(product.getTitle());
        holder.amount.setText( product.getDescription() );
        holder.price.setText(product.getPrice()+ " ج.م" );
        holder.totalEachItem.setText("السعر الكلي: " + Math.round((product.getQuantity() * product.getPrice()))+" ج.م");
        holder.orderAmount.setText(String.valueOf(product.getQuantity()));
        if (product.getDiscountPrice() == 0) {
            holder.price.setVisibility(View.VISIBLE);
            holder.discountCons.setVisibility(View.GONE);
            holder.saleTape.setVisibility(View.GONE);
        } else {
            holder.price.setVisibility(View.VISIBLE);
            holder.discountCons.setVisibility(View.VISIBLE);
            holder.saleTape.setVisibility(View.VISIBLE);
        }
        holder.discountPrice.setText(product.getDiscountPrice()+" ج.م");
        holder.price.setText(product.getPrice().toString()+" ج.م");
        holder.discountPrice.setText(product.getDiscountPrice()+" ج.م");
        holder.amount.setText(product.getDescription());
        Glide.with(holder.itemView.getContext()).load(products.get(position).getPic()).into(holder.productPic);
        holder.increase.setOnClickListener(view -> {
            managementCart.plusNumberFood(products, product2, position, new ChangeNumberItemsListener() {
                @Override
                public void Changed() {
                    cartProductAdapter.this.notifyDataSetChanged();
                    changeNumberItemsListener.Changed();
                }
            });
        });
        holder.minus.setOnClickListener(view -> {
             managementCart.minusNumberFood(products,product2, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.Changed();
            });
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, discountPrice, amount, orderAmount,totalEachItem;
        ImageView productPic;
        Button minus, increase;
        LinearLayout rangLy;
        ConstraintLayout discountCons,saleTape;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_title_ordered);
            saleTape = itemView.findViewById(R.id.sale_tape);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            discountCons = itemView.findViewById(R.id.discount_cons_ordered);
            rangLy = itemView.findViewById(R.id.range_ly_ordered);
            price = itemView.findViewById(R.id.product_price_basic_ordered);
            discountPrice = itemView.findViewById(R.id.product_price_sale_ordered);
            amount = itemView.findViewById(R.id.product_amount_ordered);
            productPic = itemView.findViewById(R.id.product_img_ordered);
            minus = itemView.findViewById(R.id.remove_ordered);
            increase = itemView.findViewById(R.id.add_ordered);
            orderAmount = itemView.findViewById(R.id.order_amount_ordered);
        }
    }

}
