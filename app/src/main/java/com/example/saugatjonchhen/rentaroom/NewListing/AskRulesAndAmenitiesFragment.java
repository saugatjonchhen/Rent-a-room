package com.example.saugatjonchhen.rentaroom.NewListing;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskRulesAndAmenitiesFragment extends Fragment {
    View view;
    Button btn_next;
    ImageButton btn_back;
    TextView amenities_heading, rules_heading;
    Bundle b;


    public AskRulesAndAmenitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_rules_and_amenities, container, false);

        btn_back = view.findViewById(R.id.imgBtn_back_rules_amenities);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, new AskBHKFragment(), "AskBHKFragment").commit();
            }
        });

        btn_next = view.findViewById(R.id.btn_next_rules_amenities);
        amenities_heading = view.findViewById(R.id.amenities_heading);
        rules_heading = view.findViewById(R.id.rules_heading);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        amenities_heading.setTypeface(type);
        rules_heading.setTypeface(type);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskDescriptionFragment askDescriptionFragment = new AskDescriptionFragment();
                b = getArguments();
                askDescriptionFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, askDescriptionFragment, "AskDescriptionFragment").commit();
            }
        });
        return view;
    }

}
