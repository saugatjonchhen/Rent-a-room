package com.example.saugatjonchhen.rentaroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.Login.LoginFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Featured_listings_class_individual_item extends AppCompatActivity {

    protected ImageView cImage, mImage;
    protected TextView pName, rentprice;
    protected Bitmap circularProfile, mainImage;
    protected String price, placeName;
    protected int fav_flag = 0;
    protected TextView description, amenities, rules, placeName_individual_item;
    protected Button btn_bottom;
    protected Bundle bb;


    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_listings_class_individual_item);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        description = findViewById(R.id.description_title_individual_item);
        amenities = findViewById(R.id.txt_amenities);
        rules = findViewById(R.id.txt_rules);
        btn_bottom = findViewById(R.id.btn_bottom);
        placeName_individual_item = findViewById(R.id.placeName_individual_item);
        description.setTypeface(type);
        amenities.setTypeface(type);
        rules.setTypeface(type);
        placeName_individual_item.setTypeface(type);

        Intent intent = getIntent();
        bb = this.getIntent().getExtras();
        circularProfile = bb.getParcelable("circularImage");
        price = intent.getStringExtra("price");
        placeName = intent.getStringExtra("placeName");
        if (bb.containsKey("previewTest")) {
            if (bb.getString("previewTest").equals("preview")) {
                btn_bottom.setText("Post Listing");
                String ru = "रु.";
                price = ru.concat(price);
                try {
                    FileInputStream is = this.openFileInput(bb.getString("mainImage"));
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    mainImage = bmp;
                    SharedPreferences facebookPrefs = getSharedPreferences("facebook", Context.MODE_PRIVATE);
                    String ab = facebookPrefs.getString("imageUrl", "");
                    cImage = (CircleImageView) findViewById(R.id.profile_image_individual_item);
                    Picasso.with(this).load(ab).into(cImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            mainImage = bb.getParcelable("mainImage");
        }


        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), placeName);
        supportPostponeEnterTransition();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_collapsable));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbarLayout.setTitle(placeName);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        cImage = (CircleImageView) findViewById(R.id.profile_image_individual_item);
        mImage = findViewById(R.id.image_main_individual_item);
        pName = findViewById(R.id.placeName_individual_item);
        rentprice = findViewById(R.id.price_individual_item);
        if (!bb.containsKey("previewTest")) {
            cImage.setImageBitmap(circularProfile);
        }
        mImage.setImageBitmap(mainImage);
        rentprice.setText(price);
        pName.setText(placeName);

        final ImageButton fav = (ImageButton) findViewById(R.id.toolbar_favourite_button);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav_flag == 0) {
                    fav.setImageResource(R.drawable.love_selected_01);
                    fav_flag = 1;
                } else if (fav_flag == 1) {
                    fav.setImageResource(R.drawable.love_01);
                    fav_flag = 0;
                }
            }
        });

        btn_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bb.containsKey("previewTest")) {
                    Toast.makeText(Featured_listings_class_individual_item.this, "It contains", Toast.LENGTH_SHORT).show();
                    
                } else {
                    Toast.makeText(Featured_listings_class_individual_item.this, "Doesnot do anything for now!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
