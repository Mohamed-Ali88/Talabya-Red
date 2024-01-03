package com.aliProApp.aliPro_Paints.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aliProApp.aliPro_Paints.Activities.ShowInvoicesData;
import com.aliProApp.aliPro_Paints.Domain.ordersDomain;
import com.aliProApp.aliPro_Paints.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private ArrayList<ordersDomain> orderedDomain;

    public OrdersAdapter(ArrayList<ordersDomain> orderDomain) {
        this.orderedDomain = orderDomain;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_my_orders, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        ordersDomain ordersDomain = orderedDomain.get(position);
        holder.orderId.setText(ordersDomain.getOrderId());
        holder.orderState.setText(ordersDomain.getTotalOrderPrice());
        holder.orderDate.setText(ordersDomain.getRequestDate());
        holder.orderTotalPrice.setText(ordersDomain.getOrderState());
        holder.fadfa.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), ShowInvoicesData.class);
           // intent.putExtra("object", .get(position));
           // holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderedDomain.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, orderState, orderDate, orderTotalPrice;
        LinearLayout fadfa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fadfa = itemView.findViewById(R.id.fadfa);
            orderId = itemView.findViewById(R.id.order_id);
            orderState = itemView.findViewById(R.id.order_state);
            orderDate = itemView.findViewById(R.id.request_date);
            orderTotalPrice = itemView.findViewById(R.id.total_order_price);

        }
    }

}
