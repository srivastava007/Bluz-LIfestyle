package com.bluz.bluzfilestyle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterForMyOrders extends RecyclerView.Adapter<AdapterForMyOrders.MyViewHolder> {

    private Context mContext;
    List<RecyclerViewOrderModel> recyclerViewOrderModelList;

    public AdapterForMyOrders(Context mContext, List<RecyclerViewOrderModel> recyclerViewOrderModelList) {
        this.mContext = mContext;
        this.recyclerViewOrderModelList = recyclerViewOrderModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.my_orders_item_view, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        String orderId = recyclerViewOrderModelList.get(i).getOrderID();
        holder.orderID.setText(orderId);
        holder.orderDate.setText(recyclerViewOrderModelList.get(i).getOrderDate());

        if (recyclerViewOrderModelList.get(i).getOrderStatus().equals("Placed")){
            holder.orderStatus.setTextColor(Color.GREEN);
        }else holder.orderStatus.setTextColor(Color.RED);

        holder.orderStatus.setText(recyclerViewOrderModelList.get(i).getOrderStatus());

        holder.prodName.setText(recyclerViewOrderModelList.get(i).getName());
        Glide.with(mContext)
                .load("https://bluz-lifestyle.com/" + recyclerViewOrderModelList.get(i).getImageURL())
                .into(holder.prodImage);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext,MyOrderDetails.class);
            intent.putExtra("OrderID",orderId);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recyclerViewOrderModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderID, orderDate, prodName, orderStatus;
        ImageView prodImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderID = itemView.findViewById(R.id.orderID);
            orderDate = itemView.findViewById(R.id.orderDate);
            prodName = itemView.findViewById(R.id.prodName);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            prodImage = itemView.findViewById(R.id.prodImage);

        }
    }

}
