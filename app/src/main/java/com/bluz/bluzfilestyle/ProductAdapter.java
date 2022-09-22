package com.bluz.bluzfilestyle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    String symbol = "₹";
    Context context;
    String webUrl = "https://bluz-lifestyle.com/";
    private List<DataPOJO> retrievedResponses;
    private ItemClickListener itemClickListener;

    public ProductAdapter(List<DataPOJO> retrievedResponses, Context context, ItemClickListener itemClickListener) {
        this.retrievedResponses = retrievedResponses;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.productImage)
                .load(webUrl + (retrievedResponses.get(position).getPRODUCT_IMAGE_URL()))
                .into(holder.productImage);
        holder.productName.setText(retrievedResponses.get(position).getPRODUCT_NAME());
        holder.productRating.setRating(retrievedResponses.get(position).getPRODUCT_RATING());
        holder.productPrice.setText(symbol.concat(retrievedResponses.get(position).getPRODUCT_PRICE()));
        holder.offerPrice.setText(symbol.concat(retrievedResponses.get(position).getPRODUCT_OFFER_PRICE()));

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(retrievedResponses.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return retrievedResponses.size();
    }

    public interface ItemClickListener {
        void onItemClick(DataPOJO dataPOJO);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        RatingBar productRating;
        TextView productPrice;
        TextView offerPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productRating = itemView.findViewById(R.id.productRating);
            productPrice = itemView.findViewById(R.id.productPrice);
            offerPrice = itemView.findViewById(R.id.offerPrice);

        }
    }
}
