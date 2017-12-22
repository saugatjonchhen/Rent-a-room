package com.example.saugatjonchhen.rentaroom.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.R;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditProfile extends AppCompatActivity {

    private Spinner spinnerDropDown;
    private String[] gender = {"Male", "Female", "Others"};
    ImageView edit_user_profile_image;
    TextView edit_text_name, edit_text_email, toolbar_editProfile_title;
    SharedPreferences facebookPrefs;
    Spinner edit_dropdown_gender;
    ImageButton imgBtn_discard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        imgBtn_discard = findViewById(R.id.imgBtn_discard);
        imgBtn_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar_editProfile_title = findViewById(R.id.toolbar_editProfile_title);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        toolbar_editProfile_title.setTypeface(type);

        Toolbar toolbar = findViewById(R.id.toolbar_editProfile);

        edit_text_name = findViewById(R.id.edit_text_name);
        edit_user_profile_image = findViewById(R.id.edit_user_profile_image);
        edit_text_email = findViewById(R.id.edit_text_email);
        edit_dropdown_gender = findViewById(R.id.edit_dropdown_gender);
        facebookPrefs = getSharedPreferences("facebook", Context.MODE_PRIVATE);
        String fullName = facebookPrefs.getString("fullName", "").toString();
        String email = facebookPrefs.getString("email", "").toString();
        String gender1 = facebookPrefs.getString("gender", "").toString();
        if (!fullName.equals("")) {
            edit_text_name.setText(fullName);
        }
        if (!email.equals("")) {
            edit_text_email.setText(email);
        }
        if (!(facebookPrefs.getString("imageUrl", "").toString()).equals("")) {
            Picasso.with(getApplicationContext()).load(facebookPrefs.getString("imageUrl", "")).into(edit_user_profile_image);
        }

        //Drop down Gender
        spinnerDropDown = (Spinner) findViewById(R.id.edit_dropdown_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_dropdown_item, gender);
        spinnerDropDown.setAdapter(adapter);

        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                int sid = spinnerDropDown.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //till here
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
