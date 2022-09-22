package com.bluz.bluzfilestyle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<myViewHolder> {

    List<Product> products;
    TextView rateView;
    RelativeLayout noItem, hasItem;

    String baseUrl = "https://bluz-lifestyle.com";

    public CartItemAdapter(List<Product> products, TextView rateView, RelativeLayout noItem, RelativeLayout hasItem) {
        this.products = products;
        this.rateView = rateView;
        this.noItem = noItem;
        this.hasItem = hasItem;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);
        return new myViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String img = baseUrl.concat(String.valueOf(products.get(position).getImageID()));
        Glide.with(holder.imageView).load(img).into(holder.imageView);
        holder.cartProductName.setText(String.valueOf(products.get(position).getpName()));
        holder.cartProductPrice.setText(String.valueOf(products.get(position).getpPrice()));
        holder.productQTY.setText(String.valueOf(products.get(position).getpQty()));
        holder.productID.setText(String.valueOf(products.get(position).getProductId()));
        int qty = products.get(position).getpQty();
        if (qty == 1) {
            holder.imageMinusButton.setBackgroundResource(R.drawable.delete);
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}

class myViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView cartProductName, cartProductPrice, productQTY, productID;
    ImageButton deleteButton, imageMinusButton, imagePlusButton;
    private CartItemAdapter cartItemAdapter;

    public myViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        cartProductName = itemView.findViewById(R.id.cartProductName);
        cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
        productQTY = itemView.findViewById(R.id.cartProductQty);
        productID = itemView.findViewById(R.id.productID);
        deleteButton = itemView.findViewById(R.id.deleteButton);
        imageMinusButton = itemView.findViewById(R.id.imageMinusButton);
        imagePlusButton = itemView.findViewById(R.id.imagePlusButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();

                if (cartItemAdapter.getItemCount() == 0) {
                    cartItemAdapter.hasItem.setVisibility(View.INVISIBLE);
                    cartItemAdapter.noItem.setVisibility(View.VISIBLE);
                }

            }
        });

        imagePlusButton.setOnClickListener(view -> {
            imageMinusButton.setBackgroundResource(R.drawable.minus);
            deleteButton.setVisibility(View.VISIBLE);
            AppDatabase db = Room.databaseBuilder(productID.getContext(),
                    AppDatabase.class, "cart_db").allowMainThreadQueries().build();
            ProductDao productDao = db.ProductDao();
            int qty = cartItemAdapter.products.get(getAbsoluteAdapterPosition()).getpQty();
            String productID = cartItemAdapter.products.get(getAbsoluteAdapterPosition()).getProductId();
            qty++;
            productDao.updateData(qty, productID);
            cartItemAdapter.products.get(getAbsoluteAdapterPosition()).setpQty(qty);
            cartItemAdapter.notifyDataSetChanged();
            updatePrice();
            db.close();
        });

        imageMinusButton.setOnClickListener(view -> {

            AppDatabase db = Room.databaseBuilder(productID.getContext(),
                    AppDatabase.class, "cart_db").allowMainThreadQueries().build();
            ProductDao productDao = db.ProductDao();
            int qty = cartItemAdapter.products.get(getAbsoluteAdapterPosition()).getpQty();
            String productID = cartItemAdapter.products.get(getAbsoluteAdapterPosition()).getProductId();
            if (qty == 1) {
                imageMinusButton.setBackgroundResource(R.drawable.delete);
                deleteButton.setVisibility(View.INVISIBLE);
                deleteProduct();
                if (cartItemAdapter.getItemCount() == 0) {
                    cartItemAdapter.hasItem.setVisibility(View.INVISIBLE);
                    cartItemAdapter.noItem.setVisibility(View.VISIBLE);
                }
            } else {
                imageMinusButton.setBackgroundResource(R.drawable.minus);
                qty--;
                productDao.updateData(qty, productID);
                cartItemAdapter.products.get(getAbsoluteAdapterPosition()).setpQty(qty);
                cartItemAdapter.notifyDataSetChanged();
                updatePrice();
                db.close();
            }
        });

    }

    private void deleteProduct() {

        AppDatabase db = Room.databaseBuilder(productID.getContext(),
                AppDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();
        productDao.deleteById(cartItemAdapter.products.get(getAbsoluteAdapterPosition()).getProductId());
        cartItemAdapter.products.remove(getAbsoluteAdapterPosition());
        cartItemAdapter.notifyItemRemoved(getAbsoluteAdapterPosition());
        updatePrice();
        db.close();

    }

    public void updatePrice() {
        double sum = 0;
        for (int i = 0; i < cartItemAdapter.products.size(); i++)
            sum = sum + (cartItemAdapter.products.get(i).getpPrice() * cartItemAdapter.products.get(i).getpQty());
        cartItemAdapter.rateView.setText(String.valueOf(sum));
    }

    public myViewHolder linkAdapter(CartItemAdapter cartItemAdapter) {
        this.cartItemAdapter = cartItemAdapter;
        return this;
    }
}


