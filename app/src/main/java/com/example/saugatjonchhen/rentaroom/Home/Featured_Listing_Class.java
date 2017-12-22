package com.example.saugatjonchhen.rentaroom.Home;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by Saugat Jonchhen on 11/27/2017.
 */

public class Featured_Listing_Class {

    protected Bitmap circularProfile, mainImage;
    protected String price, placeName;

    public Featured_Listing_Class(String price, String placeName, Bitmap circularProfile, Bitmap mainImage){
        this.circularProfile = circularProfile;
        this.mainImage = mainImage;
        this.price = price;
        this.placeName= placeName;
    }
}
