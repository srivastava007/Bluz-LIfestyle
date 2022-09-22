package com.bluz.bluzfilestyle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrderDetails extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String main_id = "";
    int order_id = 0;
    GetOrderDetailPojo getOrderDetailPojo = new GetOrderDetailPojo();
    TextView Order_ID, Full_Name, Mobile, Address, Order_Status, Payment_Status;
    Button cancelButton;

    RelativeLayout relativeLayout;

    ProgressBar progressBar;

    List<OrderDetailProduct> orderDetailProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_details);

        relativeLayout = findViewById(R.id.RR);
        relativeLayout.setVisibility(View.INVISIBLE);

        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");

        Intent intent = getIntent();
        order_id = Integer.parseInt(intent.getStringExtra("OrderID"));
        main_id = sharedPreferences.getString("mainID", "");


        Order_ID = findViewById(R.id.textViewOrderId);
        Full_Name = findViewById(R.id.textViewFullName);
        Mobile = findViewById(R.id.textViewPhoneNumber);
        Address = findViewById(R.id.textViewAddress);
        Order_Status = findViewById(R.id.textViewOrderStatus);
        Payment_Status = findViewById(R.id.textViewPaymentStatus);
        cancelButton = findViewById(R.id.buttonCancelOrder);
        progressBar = findViewById(R.id.loading);

        getOrderDetailPojo.setOrderNo(order_id);
        getOrderDetailPojo.setMainId(main_id);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bluz-lifestyle.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService retrofitApiService = retrofit.create(RetrofitApiService.class);

        Call<OrderDetailResponsePojo> orderDetailResponsePojoCall = retrofitApiService.orderDetailsResponsePojo(getOrderDetailPojo);

        orderDetailResponsePojoCall.enqueue(new Callback<OrderDetailResponsePojo>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<OrderDetailResponsePojo> call, @NonNull Response<OrderDetailResponsePojo> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Order_ID.setText(String.valueOf(order_id));
                    Full_Name.setText(response.body().getOrderDetails().getCustomerDetails().getFullName());
                    Mobile.setText(response.body().getOrderDetails().getCustomerDetails().getMobileNo());
                    Address.setText(response.body().getOrderDetails().getCustomerDetails().getAddress());
                    Order_Status.setText(response.body().getOrderDetails().getCustomerDetails().getOrderStatus());
                    String paymentType = response.body().getOrderDetails().getCustomerDetails().getPaymentStatus();
                    if (paymentType.equals("RZPAY"))
                        Payment_Status.setText("Online");
                    else
                        Payment_Status.setText("COD");

                    orderDetailProductList = response.body().getOrderDetails().getProductList();

                    String isCancelled = response.body().getOrderDetails().getCustomerDetails().getOrderStatus();

                    if (isCancelled.equalsIgnoreCase("Cancelled")) {
                        cancelButton.setEnabled(false);
                        cancelButton.setText("Order Cancelled");
                        cancelButton.setBackgroundColor(Color.GRAY);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailResponsePojo> call, @NonNull Throwable t) {

            }
        });

        cancelButton.setOnClickListener(view -> showAlertDialogBox());

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void showAlertDialogBox() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("Do you want to cancel the order number "+Order_ID.getText()+"?")
                .setPositiveButton("Ok", (dialogInterface, i) -> cancelOrder())
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        dialog.show();
    }

    private void cancelOrder() {

        CancelOrderPostPojo cancelOrderPostPojo = new CancelOrderPostPojo();
        cancelOrderPostPojo.setMainId(main_id);
        cancelOrderPostPojo.setOrderNo(Integer.valueOf(Order_ID.getText().toString()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bluz-lifestyle.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService retrofitApiService = retrofit.create(RetrofitApiService.class);

        Call<CancelOrderResponsePojo> cancelOrderResponsePojoCall = retrofitApiService.cancelOrderResponsePojo(cancelOrderPostPojo);
        cancelOrderResponsePojoCall.enqueue(new Callback<CancelOrderResponsePojo>() {
            @Override
            public void onResponse(@NonNull Call<CancelOrderResponsePojo> call, @NonNull Response<CancelOrderResponsePojo> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().toString().equals("200")) {
                        Toast.makeText(MyOrderDetails.this, "Your order has been cancelled", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelOrderResponsePojo> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}