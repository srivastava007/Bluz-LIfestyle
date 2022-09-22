package com.bluz.bluzfilestyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CategoryFragment extends Fragment {

    CardView audioCard, chargerCard, cableCard;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        audioCard = view.findViewById(R.id.audioCard);
        chargerCard = view.findViewById(R.id.chargerCard);
        cableCard = view.findViewById(R.id.cableCard);

        audioCard.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CategoryDetail.class);
            intent.putExtra("Title", "Audio");
            startActivity(intent);
        });

        chargerCard.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), CategoryDetail.class);
            intent.putExtra("Title", "Charger");
            startActivity(intent);
        });

        cableCard.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), CategoryDetail.class);
            intent.putExtra("Title", "Cable");
            startActivity(intent);
        });

        return view;
    }

}
