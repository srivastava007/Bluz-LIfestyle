package com.bluz.bluzfilestyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;


public class HomeFragment extends Fragment {

    List<DataPOJO> jsonResponse = new ArrayList<>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    private RecyclerView response_recycler_view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        response_recycler_view = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        response_recycler_view.setLayoutManager(gridLayoutManager);

        setImage(view);
        volleyGet();
        return view;
    }

    private void volleyGet() {

        String url = "https://api.bluz-lifestyle.com/Product/All";

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
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
                    response_recycler_view.setAdapter(new ProductAdapter(jsonResponse, getContext(), dataPOJO -> {
                        Intent intent = new Intent(getContext(), ProductDetails.class);
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

    private void setImage(View view) {

        ImageSlider imageSlider = view.findViewById(R.id.imageSlide);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.a, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.bluz_1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.headphone_1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.bluz_2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.headphone_2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.bluz_3, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);

    }
}
