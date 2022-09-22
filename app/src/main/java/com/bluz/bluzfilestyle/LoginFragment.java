package com.bluz.bluzfilestyle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    String number, otp, mainId = "";
    Button generateOTP, verifyOTP;
    ImageButton logOutButton;
    TextView phone;
    View view;
    EditText phoneText, otpText;
    ConstraintLayout loginLayout;
    RelativeLayout loggedInLayout;
    RecyclerView recyclerView;
    loginModel model;
    String statusGenOTP = "", statusVerOTP = "";
    GetOtpResponsePojo getOtpResponsePojo;
    List<RecyclerViewOrderModel> recyclerViewOrderModels;
    ProgressBar progressBar;
    AdapterForMyOrders adapter;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_login, container, false);

        recyclerViewOrderModels = new ArrayList<>();

        model = new loginModel();
        phoneText = view.findViewById(R.id.editTextPhone); //attaching phone number field
        otpText = view.findViewById(R.id.editTextOTP); //attaching otp field
        generateOTP = view.findViewById(R.id.generateOTP); //attaching button
        verifyOTP = view.findViewById(R.id.verifyOTP);

        getOtpResponsePojo = new GetOtpResponsePojo();

        logOutButton = view.findViewById(R.id.LogOutButton);
        phone = view.findViewById(R.id.phone);

        loggedInLayout = view.findViewById(R.id.loggedInlayout);
        loginLayout = view.findViewById(R.id.loginLayout);
        recyclerView = view.findViewById(R.id.recyclerViewforOrders);

        progressBar = view.findViewById(R.id.progressBar);

        logOutButton.setOnClickListener(view -> {
            SharedPreferences preferences = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("isLoggedIn", "false");
            editor.apply();
            loggedInLayout.setVisibility(View.INVISIBLE);
            loginLayout.setVisibility(View.VISIBLE);

            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().detach(LoginFragment.this).attach(LoginFragment.this).commit();
            phoneText.setText("");
            otpText.setText("");

            phoneText.setEnabled(true);
            phoneText.requestFocus();
            otpText.setEnabled(false);
            generateOTP.setEnabled(true);
            generateOTP.setVisibility(View.VISIBLE);
            verifyOTP.setVisibility(View.INVISIBLE);
            verifyOTP.setEnabled(false);

        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("isLoggedIn", "").equals("true")) {

            number = sharedPreferences.getString("phoneNumber", "");
            mainId = sharedPreferences.getString("mainID","");
            loggedInLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.INVISIBLE);
            phone.setText(number);
            getData();

        } else {
            loggedInLayout.setVisibility(View.INVISIBLE);
            loginLayout.setVisibility(View.VISIBLE);
            generateOTP.setOnClickListener(view -> {
                number = phoneText.getText().toString();
                if (number.length() == 10) {
                    getOTP();
                } else
                    Toast.makeText(getContext(), "Please Enter A Valid Number!", Toast.LENGTH_SHORT).show();
            });
        }
        return view;
    }

    private void getOTP() {

        String url = "https://api.bluz-lifestyle.com/Login/SendOTP?mobileNo=" + number;

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, response -> {
            try {
                getOtpResponsePojo.setStatus((Integer) response.get("status"));
                if (response.getString("status").equals("200")) {
                    statusGenOTP = response.getString("status");
                    phoneText.setEnabled(false);
                    otpText.setEnabled(true);
                    otpText.requestFocus();
                    generateOTP.setEnabled(false);
                    generateOTP.setVisibility(View.INVISIBLE);
                    verifyOTP.setVisibility(View.VISIBLE);
                    verifyOTP.setEnabled(true);
                    verifyOTP.setOnClickListener(view12 -> verifyNumber());

                } else
                    Toast.makeText(getContext(), "Problem in Generating OTP...", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {});
        requestQueue.add(jsonObjectRequest);
    }

    private void verifyNumber() {

        otp = otpText.getText().toString();
        String url = "https://api.bluz-lifestyle.com/Login/VerifyOtp?mobileNo=" + number + "&otp=" + otp;

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, response -> {
                    try {
                        if (response.getString("status").equals("200")) {
                            statusVerOTP = response.getString("status");
                            mainId = response.getString("mainId");
                            model.setMainId(mainId);

                            getData();

                            SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences1.edit();
                            editor.putString("isLoggedIn", "true");
                            editor.putString("mainID", mainId);
                            editor.putString("phoneNumber", number);
                            editor.apply();
                            loginLayout.setVisibility(View.INVISIBLE);
                            loggedInLayout.setVisibility(View.VISIBLE);
                            phone.setText(phoneText.getText().toString());
                        } else Toast.makeText(getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {});
        requestQueue.add(jsonObjectRequest);
    }

    private void getData(){

        recyclerViewOrderModels = new ArrayList<>();

        GetMyOrdersPojo getMyOrdersPojo = new GetMyOrdersPojo();

        getMyOrdersPojo.setMobileNo(number);
        getMyOrdersPojo.setMainId(mainId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bluz-lifestyle.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService retrofitApiService = retrofit.create(RetrofitApiService.class);

        Call<MyOrderResponsePojo> myOrderResponsePojoCall = retrofitApiService.myOrderResponsePojo(getMyOrdersPojo);

        myOrderResponsePojoCall.enqueue(new Callback<MyOrderResponsePojo>() {
            @Override
            public void onResponse(Call<MyOrderResponsePojo> call, Response<MyOrderResponsePojo> response) {
                if (response.isSuccessful()){

                    for (int i = 0; i < response.body().getOrdersList().size(); i++) {

                        for (int j = 0; j < response.body().getOrdersList().get(i).getProductList().size(); j++) {

                            RecyclerViewOrderModel model = new RecyclerViewOrderModel();

                            String orderid = response.body().getOrdersList().get(i).getOrderNo().toString();
                            String orderdate = response.body().getOrdersList().get(i).getOrderPlacedDate();
                            String orderstatus = response.body().getOrdersList().get(i).getOrderStatus();
                            String name = response.body().getOrdersList().get(i).getProductList().get(j).getProductName();
                            String url = response.body().getOrdersList().get(i).getProductList().get(j).getProductImageUrl();

                            model.setName(name);
                            model.setOrderID(orderid);
                            model.setOrderStatus(orderstatus);
                            model.setOrderDate(orderdate);
                            model.setImageURL(url);

                            recyclerViewOrderModels.add(model);

                        }
                    }
                    putDataInRecyclerView(recyclerViewOrderModels);
                }
            }

            @Override
            public void onFailure(Call<MyOrderResponsePojo> call, Throwable t) {}
        });

    }

    private void putDataInRecyclerView(List<RecyclerViewOrderModel> recyclerViewOrderModels) {

        adapter = new AdapterForMyOrders(getContext(), recyclerViewOrderModels);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
    }
}
