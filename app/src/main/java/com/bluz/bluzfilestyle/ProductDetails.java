package com.bluz.bluzfilestyle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProductDetails extends AppCompatActivity {

    JSONArray jsonArray;
    JSONObject jsonObject;

    String webUrl = "https://bluz-lifestyle.com";
    TextView nameTextView, productPriceTextView, offerPriceTextView, descriptionTextView, discountTextView, shortDesc;
    ImageSlider imageSlider;
    String pimgurl;
    int sno;

    Button addToCart;

    String newList = "";
    List<String> linkList;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Details");

        Intent intent = getIntent();
        String PRODUCT_ID = intent.getStringExtra("PRODUCT_ID");

        imageSlider = findViewById(R.id.productIView);
        nameTextView = findViewById(R.id.nameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        offerPriceTextView = findViewById(R.id.offerPriceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        discountTextView = findViewById(R.id.productDiscount);
        shortDesc = findViewById(R.id.shortDescTextView);

        addToCart = findViewById(R.id.addToCart);

        getFromVolley(PRODUCT_ID);

        addToCart.setOnClickListener(view -> {

            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                    "cart_db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
            ProductDao productDao = db.ProductDao();
            Boolean check = productDao.isExists(PRODUCT_ID);
            if (!check) {
                int primaryKey = sno;
                String imageID = pimgurl;
                String pName = nameTextView.getText().toString();
                String price = offerPriceTextView.getText().toString();
                int pPrice = Integer.parseInt(price);
                System.out.println(pPrice);
                int pQty = 1;
                productDao.insertRecord(new Product(primaryKey, PRODUCT_ID, imageID, pName, pPrice, pQty));
                Toast.makeText(ProductDetails.this, "Product Added to Cart", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Product already Exists", Toast.LENGTH_SHORT).show();

            db.close();
        });
    }

    private void getFromVolley(String PRODUCT_ID) {

        String url = "https://api.bluz-lifestyle.com/Product/Details?productId=" + PRODUCT_ID;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                jsonArray = response.getJSONArray("ProductList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    pimgurl = jsonObject.getString("PRODUCT_IMAGE_URL");

                    String list = jsonObject.getString("PRODUCT_IMAGE_URL_LIST");
                    newList = list.replace("[", "");
                    String newnewList = newList.replace("]", "");
                    String nList = newnewList.substring(0, newnewList.length() - 1);
                    if (!nList.endsWith("g"))
                        nList = nList.concat("g");
                    linkList = Arrays.asList(nList.split(","));

                    arrayList = new ArrayList<>(linkList);

                    ArrayList<SlideModel> slideModels = new ArrayList<>();
                    for (int a = 0; a < arrayList.size(); a++) {
                        slideModels.add(new SlideModel(webUrl.concat(arrayList.get(a)), ScaleTypes.CENTER_CROP));
                    }
                    imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

                    sno = jsonObject.getInt("SNO");
                    String pid = jsonObject.getString("PRODUCT_ID");
                    String pname = jsonObject.getString("PRODUCT_NAME");
                    String pdesc = jsonObject.getString("PRODUCT_DESCRIPTION");
                    String val1 = (jsonObject.getString("PRODUCT_PRICE"));
                    String val2 = val1.substring(0, val1.length() - 2);
                    int pprice = Integer.parseInt(val2);
                    String val3 = (jsonObject.getString("PRODUCT_OFFER_PRICE"));
                    String val4 = val3.substring(0, val3.length() - 2);
                    int poffprice = Integer.parseInt(val4);
                    int poffdisc = jsonObject.getInt("PRODUCT_OFFER_DISCOUNT");
                    String sdesc = jsonObject.getString("SORT_DESCRIPTION");

                    String disc = String.valueOf(poffdisc);
                    nameTextView.setText(pname);
                    productPriceTextView.setText(String.valueOf(pprice));
                    offerPriceTextView.setText(String.valueOf(poffprice));
                    String desc = pdesc.replace("ü", "✓");
                    descriptionTextView.setText(Html.fromHtml(desc, Html.FROM_HTML_MODE_COMPACT));
                    shortDesc.setText(sdesc);
                    discountTextView.setTextColor(Color.RED);
                    discountTextView.setText("-" + disc + "%");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}