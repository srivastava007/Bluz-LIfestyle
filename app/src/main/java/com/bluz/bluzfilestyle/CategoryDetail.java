package com.bluz.bluzfilestyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.Objects;

public class CategoryDetail extends AppCompatActivity {

    List<DataPOJO> jsonResponse = new ArrayList<>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    String title;
    String BaseURL = "https://api.bluz-lifestyle.com/Product/Category?cat=";
    private RecyclerView response_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        title = getIntent().getStringExtra("Title");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        response_recycler_view = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        response_recycler_view.setLayoutManager(gridLayoutManager);

        String url = "";
        switch (title) {
            case "Audio":
                url = BaseURL.concat("AUDIO");
                volleyGet(url);
                break;
            case "Charger":
                url = BaseURL.concat("CHARGER");
                volleyGet(url);
                break;
            case "Cable":
                url = BaseURL.concat("CABLES");
                volleyGet(url);
                break;
        }

    }

    private void volleyGet(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                jsonArray = response.getJSONArray("ProductList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    int sno = jsonObject.getInt("SNO");
                    int pcatid = jsonObject.getInt("PRODUCT_CATEGORY_ID");
                    String pcatname = jsonObject.getString("PRODUCT_SUB_CATEGOTY_NAME");
                    String pname = jsonObject.getString("PRODUCT_NAME");
                    String pdesc = jsonObject.getString("PRODUCT_DESCRIPTION");
                    int prate = jsonObject.getInt("PRODUCT_RATING");
                    String pprice = jsonObject.getString("PRODUCT_PRICE");
                    String poffprice = jsonObject.getString("PRODUCT_OFFER_PRICE");
                    int poffdisc = jsonObject.getInt("PRODUCT_OFFER_DISCOUNT");
                    String pimgurl = jsonObject.getString("PRODUCT_IMAGE_URL");
                    String pimgurllist = jsonObject.getString("PRODUCT_IMAGE_URL_LIST");
                    String pid = jsonObject.getString("PRODUCT_ID");
                    int stat = jsonObject.getInt("status");
                    String sdesc = jsonObject.getString("SORT_DESCRIPTION");

                    jsonResponse.add(new DataPOJO(sno, pcatid, pcatname, pname, pdesc, prate, pprice, poffprice, poffdisc, pimgurl, pimgurllist, pid, stat, sdesc));
                    response_recycler_view.setAdapter(new ProductAdapter(jsonResponse, this, dataPOJO -> {
                        Intent intent = new Intent(this, ProductDetails.class);
                        intent.putExtra("SNO", dataPOJO.getSNO());
                        intent.putExtra("PRODUCT_CATEGORY_ID", dataPOJO.getPRODUCT_CATEGORY_ID());
                        intent.putExtra("PRODUCT_SUB_CATEGOTY_NAME", dataPOJO.getPRODUCT_SUB_CATEGOTY_NAME());
                        intent.putExtra("PRODUCT_NAME", dataPOJO.getPRODUCT_NAME());
                        intent.putExtra("PRODUCT_DESCRIPTION", dataPOJO.getPRODUCT_DESCRIPTION());
                        intent.putExtra("PRODUCT_RATING", dataPOJO.getPRODUCT_RATING());
                        intent.putExtra("PRODUCT_PRICE", dataPOJO.getPRODUCT_PRICE());
                        intent.putExtra("PRODUCT_OFFER_PRICE", dataPOJO.getPRODUCT_OFFER_PRICE());
                        intent.putExtra("PRODUCT_OFFER_DISCOUNT", dataPOJO.getPRODUCT_OFFER_DISCOUNT());
                        intent.putExtra("PRODUCT_IMAGE_URL", dataPOJO.getPRODUCT_IMAGE_URL());
                        intent.putExtra("PRODUCT_IMAGE_URL_LIST", dataPOJO.getPRODUCT_IMAGE_URL_LIST());
                        intent.putExtra("PRODUCT_ID", dataPOJO.getPRODUCT_ID());
                        intent.putExtra("status", dataPOJO.getStatus());
                        intent.putExtra("SORT_DESCRIPTION", dataPOJO.getSORT_DESCRIPTION());

                        startActivity(intent);
                    }));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);


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