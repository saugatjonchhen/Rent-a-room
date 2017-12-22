package com.example.saugatjonchhen.rentaroom.NewListing;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.MainActivity;
import com.example.saugatjonchhen.rentaroom.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskLocationFragment extends Fragment {
    View view;
    private Button btn_next;
    private TextView txt_pick_a_place;
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    int PLACE_PICKER_REQUEST = 1;
    Bundle b;

    public AskLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_location, container, false);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);

        btn_next = view.findViewById(R.id.btn_next_location);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskPriceFragment askPriceFragment = new AskPriceFragment();
                askPriceFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, askPriceFragment, "AskPriceFragment").commit();
            }
        });
        txt_pick_a_place = view.findViewById(R.id.txt_pick_a_place);
        txt_pick_a_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String toastMsg = String.format("Place: %s", place.getName());
                String placeName = String.valueOf(place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                b = new Bundle();
                b.putString("placeName", placeName);
                //Toast.makeText(getActivity(), b.getString("placeName"), Toast.LENGTH_LONG).show();
            }
        }
    }


}
