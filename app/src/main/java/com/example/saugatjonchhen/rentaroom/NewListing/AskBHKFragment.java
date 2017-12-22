package com.example.saugatjonchhen.rentaroom.NewListing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskBHKFragment extends Fragment {
    View view;
    private Button btn_next;
    private ImageButton btn_back;
    public Bundle b;

    public AskBHKFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_bhk, container, false);

        btn_back = view.findViewById(R.id.imgBtn_back_bhk);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, new AskPriceFragment(), "AskPriceFragment").commit();
            }
        });

        btn_next = view.findViewById(R.id.btn_next_bhk);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskRulesAndAmenitiesFragment askRulesAndAmenitiesFragment = new AskRulesAndAmenitiesFragment();
                b = getArguments();
                askRulesAndAmenitiesFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, askRulesAndAmenitiesFragment, "AskRulesAndAmenitiesFragment").commit();
            }
        });

        return view;
    }

}
