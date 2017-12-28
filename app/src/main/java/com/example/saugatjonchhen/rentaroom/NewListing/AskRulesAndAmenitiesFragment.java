package com.example.saugatjonchhen.rentaroom.NewListing;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox amenities_wifi, amenities_tv_channel, amenities_air_condition, amenities_elevator, amenities_furnished, amenities_parking, amenities_security, rules_no_drinking, rules_no_drugs, rules_no_pets, rules_no_smoking, rules_cats, rules_dogs;
    TextView txt_amenities_wifi, txt_amenities_tv_channel, txt_amenities_air_condition, txt_amenities_elevator, txt_amenities_furnished, txt_amenities_parking, txt_amenities_security, txt_rules_no_drinking, txt_rules_no_drugs, txt_rules_no_pets, txt_rules_no_smoking, txt_rules_cats, txt_rules_dogs;


    public AskRulesAndAmenitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_rules_and_amenities, container, false);
        initializeEverything();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, new AskBHKFragment(), "AskBHKFragment").commit();
            }
        });

        amenities_onCheck();
        rules_onCheck();


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

    public void initializeEverything() {
        btn_back = view.findViewById(R.id.imgBtn_back_rules_amenities);

        btn_next = view.findViewById(R.id.btn_next_rules_amenities);
        amenities_heading = view.findViewById(R.id.amenities_heading);
        rules_heading = view.findViewById(R.id.rules_heading);

        amenities_wifi = view.findViewById(R.id.amenities_wifi);
        amenities_tv_channel = view.findViewById(R.id.amenities_tv_channel);
        amenities_air_condition = view.findViewById(R.id.amenities_air_conditioner);
        amenities_elevator = view.findViewById(R.id.amenities_elevator);
        amenities_furnished = view.findViewById(R.id.amenities_furnished);
        amenities_parking = view.findViewById(R.id.amenities_parking);
        amenities_security = view.findViewById(R.id.amenities_security);
        rules_no_drinking = view.findViewById(R.id.rules_no_drinking);
        rules_no_drugs = view.findViewById(R.id.rules_no_drugs);
        rules_no_pets = view.findViewById(R.id.rules_no_pets);
        rules_no_smoking = view.findViewById(R.id.rules_no_smoking);
        rules_cats = view.findViewById(R.id.rules_cats_allowed);
        rules_dogs = view.findViewById(R.id.rules_dogs_allowed);

        txt_amenities_wifi = view.findViewById(R.id.txt_amenities_wifi);
        txt_amenities_tv_channel = view.findViewById(R.id.txt_amenities_tv_channel);
        txt_amenities_air_condition = view.findViewById(R.id.txt_amenities_air_conditioner);
        txt_amenities_elevator = view.findViewById(R.id.txt_amenities_elevator);
        txt_amenities_furnished = view.findViewById(R.id.txt_amenities_furnished);
        txt_amenities_parking = view.findViewById(R.id.txt_amenities_parking);
        txt_amenities_security = view.findViewById(R.id.txt_amenities_security);
        txt_rules_no_drinking = view.findViewById(R.id.txt_rules_no_drinking);
        txt_rules_no_drugs = view.findViewById(R.id.txt_rules_no_drugs);
        txt_rules_no_pets = view.findViewById(R.id.txt_rules_no_pets);
        txt_rules_no_smoking = view.findViewById(R.id.txt_rules_no_smoking);
        txt_rules_cats = view.findViewById(R.id.txt_rules_cats_allowed);
        txt_rules_dogs = view.findViewById(R.id.txt_rules_dogs_allowed);

    }

    public void amenities_onCheck() {
        amenities_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_wifi.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_wifi.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        amenities_tv_channel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_tv_channel.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_tv_channel.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        amenities_air_condition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_air_condition.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_air_condition.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        amenities_elevator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_elevator.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_elevator.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        amenities_furnished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_furnished.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_furnished.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        amenities_parking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_parking.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_parking.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        amenities_security.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_amenities_security.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_amenities_security.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });
    }

    public void rules_onCheck() {
        rules_no_drinking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_rules_no_drinking.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_rules_no_drinking.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        rules_no_drugs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_rules_no_drugs.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_rules_no_drugs.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        rules_no_pets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_rules_no_pets.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_rules_no_pets.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        rules_no_smoking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_rules_no_smoking.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_rules_no_smoking.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        rules_cats.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_rules_cats.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_rules_cats.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });

        rules_dogs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txt_rules_dogs.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    txt_rules_dogs.setTextColor(getResources().getColor(R.color.normal));
                }
            }
        });
    }

}
