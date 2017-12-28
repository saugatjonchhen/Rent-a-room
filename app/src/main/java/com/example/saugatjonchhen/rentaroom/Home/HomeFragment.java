package com.example.saugatjonchhen.rentaroom.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.Login.LoginFragment;
import com.example.saugatjonchhen.rentaroom.MainActivity;
import com.example.saugatjonchhen.rentaroom.NewListing.NewListingsActivity;
import com.example.saugatjonchhen.rentaroom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private List<Place_List> placeList = new ArrayList<Place_List>();
    private List<Featured_Listing_Class> featuredPlaceList = new ArrayList<Featured_Listing_Class>();
    View view;
    private RecyclerView rv, rv2, rVSeeAll;
    public FrameLayout homeFrame, SeeAllFrame;
    public static Integer flag = 0;
    public FloatingActionButton floating_btn_add_new_listing;
    LoginFragment lf = new LoginFragment();


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createListForFeaturedPlaces();
        createListForFeaturedListings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        loadAllAdapters();
        checkFlagStatus();
        TextView seeAll = (TextView) view.findViewById(R.id.seeAll);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                flag = 1;
                checkFlagStatus();
            }
        });
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        TextView placeName = view.findViewById(R.id.place_txt);
        TextView placeName2 = view.findViewById(R.id.place_txt2);
        TextView seeall = view.findViewById(R.id.seeAll);
        placeName.setTypeface(type);
        placeName2.setTypeface(type);
        seeall.setTypeface(type);
        floating_btn_add_new_listing = view.findViewById(R.id.floating_btn_add_new_listing);
        if ((MainActivity.SaveSharedPreference.getUserName(getActivity()).length() == 0)) {
            floating_btn_add_new_listing.setVisibility(View.INVISIBLE);
        } else {
            floating_btn_add_new_listing.setVisibility(View.VISIBLE);
        }
        floating_btn_add_new_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewListingsActivity.class));
            }
        });

        return view;
    }


    private void createListForFeaturedPlaces() {
        Bitmap ktm = BitmapFactory.decodeResource(getResources(),
                R.drawable.kathmandu);
        Bitmap bhkt = BitmapFactory.decodeResource(getResources(),
                R.drawable.bhaktapur);
        Bitmap lalit = BitmapFactory.decodeResource(getResources(),
                R.drawable.patan);
        Bitmap pokhara = BitmapFactory.decodeResource(getResources(),
                R.drawable.pokhara);
        placeList.add(new Place_List("Kathmandu", ktm));
        placeList.add(new Place_List("Bhaktapur", bhkt));
        placeList.add(new Place_List("Lalitpur", lalit));
        placeList.add(new Place_List("Pokhara", pokhara));
    }

    private void createListForFeaturedListings() {
        Bitmap main_img = BitmapFactory.decodeResource(getResources(),
                R.mipmap.pokhara_room); //for better image choose drawable
        //main_img = scaleDownBitmap(main_img,250,getActivity());

        Bitmap circular_img = BitmapFactory.decodeResource(getResources(),
                R.mipmap.circular_img);

        Bitmap main_img2 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.thamel_room); //for better image choose drawable
        //main_img = scaleDownBitmap(main_img2,250,getActivity());

        Bitmap circular_img2 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.login_img);

        featuredPlaceList.add(new Featured_Listing_Class("रु 10000", "LakeSide, Pokhara", circular_img, main_img));
        featuredPlaceList.add(new Featured_Listing_Class("रु 15000", "Thamel ,Kathmandu", circular_img2, main_img2));
    }

    public void checkFlagStatus() {
        if (flag == 0) {
            homeFrame = view.findViewById(R.id.homeFrame);
            homeFrame.setVisibility(View.VISIBLE);
            SeeAllFrame = view.findViewById(R.id.SeeAllFrame);
            SeeAllFrame.setVisibility(View.GONE);
            MainActivity.hideToolbarBackButton();

        } else if (flag == 1) {
            homeFrame = view.findViewById(R.id.homeFrame);
            homeFrame.setVisibility(View.GONE);
            SeeAllFrame = view.findViewById(R.id.SeeAllFrame);
            SeeAllFrame.setVisibility(View.VISIBLE);
            MainActivity.setToolbarBackButton();

        }
    }

    public void loadAllAdapters() {
        //places recycler view 1 loader
        rv = (RecyclerView) view.findViewById(R.id.recycler_view_horizontal);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);
        Place_List_Adapter pa = new Place_List_Adapter(placeList, getActivity());
        rv.setAdapter(pa);
        //listings recycler view 2 loader
        rv2 = (RecyclerView) view.findViewById(R.id.featured_listings_view);
        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv2.setLayoutManager(llm2);
        Featured_Listing_Class_Adapter flca = new Featured_Listing_Class_Adapter(featuredPlaceList, getActivity());
        rv2.setAdapter(flca);

        //see all recycler view loader
        rVSeeAll = (RecyclerView) view.findViewById(R.id.all_featured_listings_view);
        LinearLayoutManager llmseeAll = new LinearLayoutManager(getActivity());
        llmseeAll.setOrientation(LinearLayoutManager.VERTICAL);
        rVSeeAll.setLayoutManager(llmseeAll);
        See_All_Featured_Listing_Class_Adapter saflca = new See_All_Featured_Listing_Class_Adapter(featuredPlaceList, getActivity());
        rVSeeAll.setAdapter(saflca);
    }
}
