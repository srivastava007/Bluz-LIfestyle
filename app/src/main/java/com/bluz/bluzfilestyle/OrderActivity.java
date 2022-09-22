package com.bluz.bluzfilestyle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "OrderActivity";
    public String mainID, fullname, mob, add1, add2, land, pin, City, State, AddType, paymentType;
    String paymentID = "";
    List<ProductModel> order = new ArrayList<>();
    EditText fullName,email , mobile, address1, address2, landmark, pincode, city, state, addressType;
    Button button;
    double CartTotal = 0.0;
    List<Product> products = new ArrayList<>();

    LoadingDialog loadingDialog;

    AppDatabase db;

    ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");

        Intent intent = getIntent();
        CartTotal = Double.parseDouble(intent.getStringExtra("CartTotal"));

        Checkout.preload(getApplicationContext());

        loadingDialog = new LoadingDialog(this);

        db = Room.databaseBuilder(this, AppDatabase.class, "cart_db").allowMainThreadQueries().build();

        productDao = db.ProductDao();

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        landmark = findViewById(R.id.landmark);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        addressType = findViewById(R.id.addressType);
        button = findViewById(R.id.proceedCheckout);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Login", Context.MODE_PRIVATE);
        mainID = sharedPreferences.getString("mainID", "");

        final Spinner spinner = findViewById(R.id.paymentMethod);

        products = productDao.getAllProducts();

        // Initializing a String Array
        String[] payments = new String[]{
                "Select a payment mode...",
                "Cash On Delivery",
                "BHIM/UPI/Debit/Credit Card"
        };

        final List<String> paymentList = new ArrayList<>(Arrays.asList(payments));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, paymentList) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    switch (selectedItemText) {
                        case "Cash On Delivery":
                            paymentType = "COD";
                            break;
                        case "BHIM/UPI/Debit/Credit Card":
                            paymentType = "RZPAY";
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        button.setOnClickListener(view -> {

            fullname = fullName.getText().toString();
            mob = mobile.getText().toString();
            add1 = address1.getText().toString();
            add2 = address2.getText().toString();
            land = landmark.getText().toString();
            pin = pincode.getText().toString();
            City = city.getText().toString();
            State = state.getText().toString();
            AddType = addressType.getText().toString();

            //new verifyPin();
            if (pincode.getCurrentTextColor() != Color.RED) {
                if (!fullname.isEmpty() && !mob.isEmpty() && !add1.isEmpty() && !add2.isEmpty() && !land.isEmpty() && !pin.isEmpty() && !City.isEmpty() && !State.isEmpty() && !AddType.isEmpty()) {
                    if (paymentType.equals("COD")) {
                        loadingDialog.startLoadingDialog();
                        placeOrder();
                    }
                    if (paymentType.equals("RZPAY")) {
                        loadingDialog.startLoadingDialog();
                        startPayment();
                    }
                } else Toast.makeText(this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Pincode is not serviceable", Toast.LENGTH_SHORT).show();
                pincode.requestFocus();
                pincode.setTextColor(Color.BLACK);
            }
        });
        db.close();
    }

    private void placeOrder() {

        for (int i = 0; i < products.size(); i++) {
            ProductModel productModel = new ProductModel();
            productModel.setProductId(products.get(i).getProductId());
            productModel.setProductPrice(products.get(i).getpPrice());
            productModel.setProductQty(products.get(i).getpQty());
            order.add(productModel);
        }
        createOrderJson();
    }

    private void createOrderJson() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bluz-lifestyle.com/order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService retrofitApiService = retrofit.create(RetrofitApiService.class);

        OrderPostPojo orderPostPojo = new OrderPostPojo();
        MyAddress myAddress = new MyAddress();
        List<Order> orderList = new ArrayList<>();

        for (int i = 0; i < order.size(); i++) {
            Order newOrder = new Order();
            newOrder.setProductId(order.get(i).getProductId());
            newOrder.setProductPrice(order.get(i).getProductPrice());
            newOrder.setProductQty(order.get(i).getProductQty());
            orderList.add(newOrder);
        }

        myAddress.setMainID(mainID);
        myAddress.setFullName(fullname);
        myAddress.setMobileNo(mob);
        myAddress.setPincode(pin);
        myAddress.setAddress1(add1);
        myAddress.setAddress2(add2);
        myAddress.setLandmark(land);
        myAddress.setCity(City);
        myAddress.setState(State);
        myAddress.setAddressType(AddType);
        myAddress.setPaymentType(paymentType);
        myAddress.setPaymentId(paymentID);

        orderPostPojo.setMyAddress(myAddress);
        orderPostPojo.setOrderList(orderList);

        Call<ResponsePojo> responsePojoCall = retrofitApiService.responcePojo(orderPostPojo);

        responsePojoCall.enqueue(new Callback<ResponsePojo>() {
            @Override
            public void onResponse(Call<ResponsePojo> call, Response<ResponsePojo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Intent intent = new Intent();
                        String orderID = response.body().getOrderNo();
                        System.out.println(orderID);
                        intent.putExtra("Order_Id", response.body().getOrderNo());
                        intent.putExtra("Reference_Number", paymentID);
                        intent.putExtra("reload",1);
                        setResult(78, intent);
                        productDao.deleteAll();
                        db.close();
                        loadingDialog.dismissDialog();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePojo> call, Throwable t) {

            }
        });

    }

    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_ZgQV28aKiVmIxp");
        checkout.setImage(R.drawable.logoforrypay);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Bluz-Lifestyle");
            options.put("description", "Text Payment");
            options.put("image", R.drawable.logo);
            //options.put("order_id", orderNum);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", CartTotal * 100);//pass amount in currency subunits
            options.put("prefill.email", email.getText().toString());
            options.put("prefill.contact", mob);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment is successful" + s, Toast.LENGTH_SHORT).show();
        paymentID = s;
        placeOrder();

    }

    @Override
    public void onPaymentError(int i, String s) {

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