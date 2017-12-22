package com.example.saugatjonchhen.rentaroom.NewListing;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskPriceFragment extends Fragment {
    View view;
    private ImageButton btn_back;
    private Button btn_next;
    private TextView new_listings_txt_price;
    private EditText edit_txt_ask_price;
    public Bundle b;


    public AskPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_price, container, false);
        btn_back = view.findViewById(R.id.imgBtn_back_price);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, new AskLocationFragment(), "AskLocationFragment").commit();
            }
        });
        btn_next = view.findViewById(R.id.btn_next_price);
        new_listings_txt_price = view.findViewById(R.id.new_listings_txt_price);
        edit_txt_ask_price = view.findViewById(R.id.edit_txt_ask_price);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        new_listings_txt_price.setTypeface(type);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskBHKFragment askBHKFragment = new AskBHKFragment();
                b = getArguments();
                String price = edit_txt_ask_price.getText().toString();
                b.putString("price", price);
                askBHKFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, askBHKFragment, "AskBHKFragment").commit();
            }
        });
        return view;
    }


}
