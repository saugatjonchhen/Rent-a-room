package com.example.saugatjonchhen.rentaroom.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.Home.HomeFragment;
import com.example.saugatjonchhen.rentaroom.MainActivity;
import com.example.saugatjonchhen.rentaroom.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingsActivity extends AppCompatActivity {

    TextView title, txt_about, txt_signout;
    ImageButton btn_back;
    LinearLayout signOut;
    LoginFragment lf = new LoginFragment();;
    HomeFragment hf = new HomeFragment();
    GoogleApiClient mGoogleApiClient;
    private SharedPreferences facebookPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        title = (TextView) findViewById(R.id.settings_toolbar_title);
        txt_about = findViewById(R.id.txt_about);
        txt_signout = findViewById(R.id.txt_signout);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        title.setTypeface(type);
        txt_about.setTypeface(type);
        txt_signout.setTypeface(type);

        btn_back = (ImageButton) findViewById(R.id.backButtonToolbarSettings);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signOut = findViewById(R.id.layout_sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lf.login_flag = false;
                LoginManager.getInstance().logOut();
                signOutFromGoogle();
                MainActivity.SaveSharedPreference.setUserName(SettingsActivity.this, "");
                facebookPrefs = getSharedPreferences("facebook", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = facebookPrefs.edit();
                editor.putString("fullName","");
                editor.putString("imageUrl","");
                editor.commit();
                lf.loginFrame = findViewById(R.id.loginFrame);
                lf.loginFrame.setVisibility(View.VISIBLE);
                lf.profileFrame = findViewById(R.id.profileFrame);
                lf.profileFrame.setVisibility(View.GONE);
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void signOutFromGoogle() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
//                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginFragment.class);
                        startActivity(i);
                    }
                });
    }
}
