package com.example.saugatjonchhen.rentaroom.Home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.Featured_listings_class_individual_item;
import com.example.saugatjonchhen.rentaroom.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Saugat Jonchhen on 11/27/2017.
 */

public class See_All_Featured_Listing_Class_Adapter extends RecyclerView.Adapter<See_All_Featured_Listing_Class_Adapter.MyViewHolder> {
    public Context context;
    public List<Featured_Listing_Class> featuredPlaceList;

    public See_All_Featured_Listing_Class_Adapter(List<Featured_Listing_Class> featuredPlaceList, Context context){
        this.featuredPlaceList = featuredPlaceList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.see_all_view,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Featured_Listing_Class flc = featuredPlaceList.get(position);
        holder.circularProfile.setImageBitmap(flc.circularProfile);
        holder.mainImage.setImageBitmap(flc.mainImage);
        holder.placeName.setText(flc.placeName);
        holder.price.setText(flc.price);

        holder.mainImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Featured_listings_class_individual_item.class);
                Bundle b = new Bundle();
                b.putParcelable("mainImage",flc.mainImage);
                b.putParcelable("circularImage",flc.circularProfile);
                intent.putExtra("placeName", flc.placeName);
                intent.putExtra("price", flc.price);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return featuredPlaceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView circularProfile, mainImage;
        public TextView price, placeName;

        public MyViewHolder(View view) {
            super(view);
            circularProfile = (CircleImageView) view.findViewById(R.id.profile_image_view);
            mainImage = (ImageView) view.findViewById(R.id.image_main_view);
            price = (TextView) view.findViewById(R.id.price_view);
            placeName = (TextView) view.findViewById(R.id.listing_name_view);

        }
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

}
