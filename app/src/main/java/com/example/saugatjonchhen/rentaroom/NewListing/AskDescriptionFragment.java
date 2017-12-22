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
public class AskDescriptionFragment extends Fragment {
    View view;
    Button btn_next_description;
    TextView description_heading;
    ImageButton btn_back;
    Bundle b;


    public AskDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_description, container, false);

        btn_back = view.findViewById(R.id.imgBtn_back_description);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, new AskRulesAndAmenitiesFragment(), "AskRulesAndAmenitiesFragment").commit();
            }
        });

        btn_next_description = view.findViewById(R.id.btn_next_description);
        description_heading = view.findViewById(R.id.description_heading);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        description_heading.setTypeface(type);
        btn_next_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskPhotoFragment askPhotoFragment = new AskPhotoFragment();
                b = getArguments();
                askPhotoFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, askPhotoFragment, "AskPhotoFragment").commit();
            }
        });
        return view;
    }

}
