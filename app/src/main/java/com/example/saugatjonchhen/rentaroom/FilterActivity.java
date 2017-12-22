package com.example.saugatjonchhen.rentaroom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FilterActivity extends AppCompatActivity {

    ImageButton imgBtn_filter_discard;
    TextView toolbar_filter_title, filter_txt_location, filter_txt_price, filter_txt_amenities, filter_txt_rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        toolbar_filter_title = findViewById(R.id.toolbar_filter_title);
        filter_txt_location = findViewById(R.id.filter_txt_location);
        filter_txt_price = findViewById(R.id.filter_txt_price);
        filter_txt_rules = findViewById(R.id.filter_txt_rules);
        filter_txt_amenities = findViewById(R.id.filter_txt_amenities);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        toolbar_filter_title.setTypeface(type);
        filter_txt_location.setTypeface(type);
        filter_txt_price.setTypeface(type);
        filter_txt_amenities.setTypeface(type);
        filter_txt_rules.setTypeface(type);

        imgBtn_filter_discard = findViewById(R.id.imgBtn_filter_discard);
        imgBtn_filter_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar_price);

        // get min and max text view
        final TextView tvMin = (TextView) findViewById(R.id.filter_txt_min);
        final TextView tvMax = (TextView) findViewById(R.id.filter_txt_max);

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("रु. " + String.valueOf(minValue));
                tvMax.setText("रु. " + String.valueOf(maxValue));
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
