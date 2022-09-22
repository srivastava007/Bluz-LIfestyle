package com.bluz.bluzfilestyle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    TextView rateView;

    RelativeLayout noItem, hasItem;
    List<Product> products = new ArrayList<>();
    CartItemAdapter cartItemAdapter;

    String orderNumber, refNumber;

    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78) {
                        Intent intent = result.getData();
                        orderNumber = intent.getStringExtra("Order_Id");
                        refNumber = intent.getStringExtra("Reference_Number");
                        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                                "cart_db").allowMainThreadQueries().build();

                        ProductDao productDao = db.ProductDao();
                        productDao.deleteAll();

                        noItem.setVisibility(View.VISIBLE);
                        hasItem.setVisibility(View.INVISIBLE);

                        products.clear();
                        cartItemAdapter.notifyDataSetChanged();
                        rateView.setText("0.0");
                        showAlertDialog(R.layout.my_success_dialog);
                    }
                }
            }
    );

    public CartFragment() {
        // Required empty public constructor
    }

    private void showAlertDialog(int myLayout) {

        builderDialog = new AlertDialog.Builder(getContext());
        View layout = getLayoutInflater().inflate(myLayout, null);

        Button button = layout.findViewById(R.id.buttonOk);
        TextView textOrderId = layout.findViewById(R.id.textOrderID);
        TextView textRefId = layout.findViewById(R.id.textRefID);
        textOrderId.setText("Your Order Number is : " + orderNumber);
        if (!refNumber.equals(""))
            textRefId.setText("Your Reference Number is : " + refNumber);
        else
            textRefId.setVisibility(View.INVISIBLE);
        builderDialog.setView(layout);
        alertDialog = builderDialog.create();
        alertDialog.show();

        button.setOnClickListener(view -> {

            alertDialog.dismiss();
        });
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        noItem = view.findViewById(R.id.noItem);
        hasItem = view.findViewById(R.id.cart);

        view.findViewById(R.id.buttonCheckout).setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
            if (sharedPreferences.getString("isLoggedIn", "").equals("true")) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                String cartTotal = rateView.getText().toString();
                intent.putExtra("CartTotal", cartTotal);
                activityResultLauncher.launch(intent);

            } else {

                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.login);

                Toast.makeText(getContext(), "Kindly Login First", Toast.LENGTH_LONG).show();

            }
        });

        rateView = view.findViewById(R.id.rateView);
        getRoomData(view);

        if (cartItemAdapter.getItemCount() == 0) {
            noItem.setVisibility(View.VISIBLE);
            hasItem.setVisibility(View.INVISIBLE);
        } else {
            noItem.setVisibility(View.INVISIBLE);
            hasItem.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void getRoomData(View view) {

        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "cart_db").allowMainThreadQueries().build();

        ProductDao productDao = db.ProductDao();
        recyclerView = view.findViewById(R.id.cartItemRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        products = productDao.getAllProducts();

        cartItemAdapter = new CartItemAdapter(products, rateView, noItem, hasItem);
        recyclerView.setAdapter(cartItemAdapter);


        double sum = 0;
        for (int i = 0; i < products.size(); i++)
            sum = sum + (products.get(i).getpPrice() * products.get(i).getpQty());
        rateView.setText(String.valueOf(sum));
        db.close();
    }

}
