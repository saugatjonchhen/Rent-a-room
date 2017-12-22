package com.example.saugatjonchhen.rentaroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.Home.HomeFragment;
import com.example.saugatjonchhen.rentaroom.Login.LoginFragment;
import com.example.saugatjonchhen.rentaroom.fragments.ChatFragment;
import com.example.saugatjonchhen.rentaroom.other.TabPageAdapter;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public static ImageButton btn_back, filterButtonToolbar;
    public HomeFragment hf = new HomeFragment();
    public LoginFragment lf = new LoginFragment();
    public ChatFragment cf = new ChatFragment();
    private int backFlag = 0;
    private static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        toolbarTitle.setTypeface(type);

        btn_back = (ImageButton) findViewById(R.id.backButtonToolbar);
        filterButtonToolbar = findViewById(R.id.filterButtonToolbar);

        filterButtonToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hf.flag = 0;
                hf.homeFrame = findViewById(R.id.homeFrame);
                hf.homeFrame.setVisibility(View.VISIBLE);
                hf.SeeAllFrame = findViewById(R.id.SeeAllFrame);
                hf.SeeAllFrame.setVisibility(View.GONE);
                MainActivity.hideToolbarBackButton();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.explore_artboard_1).setText("Explore"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.favorite_artboard_1).setText("Favourite")); //setText("Tab 2 Item"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.chat_artboard_1).setText("Chat")); //setText("Tab 3 Item"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile_artboard_1).setText("Profile")); //setText("Tab 4 Item"));
        changeTabsFont();
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new TabPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.red);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                if (tab.getPosition() == 0) {
                    goHome();
                    filterButtonToolbar.setVisibility(View.VISIBLE);
                    hf.floating_btn_add_new_listing = findViewById(R.id.floating_btn_add_new_listing);
                    if ((SaveSharedPreference.getUserName(MainActivity.this).length() == 0)) {
                        hf.floating_btn_add_new_listing.setVisibility(View.INVISIBLE);
                    } else {
                        hf.floating_btn_add_new_listing.setVisibility(View.VISIBLE);
                    }
                }

                if (tab.getPosition() == 2) {
                    cf.chat_loggedOutFrame = findViewById(R.id.chat_loggedOutFrame);
                    cf.chat_loggedInFrame = findViewById(R.id.chat_loggedInFrame);
                    if ((SaveSharedPreference.getUserName(MainActivity.this).length() == 0)) {
                        cf.chat_loggedOutFrame.setVisibility(View.VISIBLE);
                        cf.chat_loggedInFrame.setVisibility(View.GONE);
                    }else{
                        cf.chat_loggedInFrame.setVisibility(View.VISIBLE);
                        cf.chat_loggedOutFrame.setVisibility(View.GONE);
                    }
                }

                if (tab.getPosition() == 3) {
                    checkLogin();
                    SharedPreferences facebookPrefs = getSharedPreferences("facebook", Context.MODE_PRIVATE);
                    if (!(facebookPrefs.getString("fullName", "").toString()).equals("") && !(facebookPrefs.getString("imageUrl", "").toString()).equals("")) {
                        //Log.i("checkStatusHere", "I am here!!");
                        String fullName = facebookPrefs.getString("fullName", "").toString();
                        //Log.i("checkStatusHere", fullName);
                        lf.profileTest = findViewById(R.id.tvtext);
                        lf.user_profile_image = findViewById(R.id.user_profile_image);
                        lf.profileTest.setText(fullName);
                        Picasso.with(getApplicationContext()).load(facebookPrefs.getString("imageUrl", "")).into(lf.user_profile_image);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.black);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                hf.floating_btn_add_new_listing = findViewById(R.id.floating_btn_add_new_listing);
                if (tab.getPosition() == 0) {
                    filterButtonToolbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("checkStatusHere", "I am here!!");
                if (tab.getPosition() == 0) {
                    goHome();
                    hf.floating_btn_add_new_listing = findViewById(R.id.floating_btn_add_new_listing);
                    if ((SaveSharedPreference.getUserName(MainActivity.this).length() == 0)) {
                        hf.floating_btn_add_new_listing.setVisibility(View.INVISIBLE);
                    } else {
                        hf.floating_btn_add_new_listing.setVisibility(View.VISIBLE);
                    }
                }
                if (tab.getPosition() == 2) {
                    cf.chat_loggedOutFrame = findViewById(R.id.chat_loggedOutFrame);
                    cf.chat_loggedInFrame = findViewById(R.id.chat_loggedInFrame);
                    if ((SaveSharedPreference.getUserName(MainActivity.this).length() == 0)) {
                        cf.chat_loggedOutFrame.setVisibility(View.VISIBLE);
                        cf.chat_loggedInFrame.setVisibility(View.GONE);
                    }else{
                        cf.chat_loggedInFrame.setVisibility(View.VISIBLE);
                        cf.chat_loggedOutFrame.setVisibility(View.GONE);
                    }
                }
                if (tab.getPosition() == 3) {
                    checkLogin();
                    Log.i("checkStatusHere", "I am here!!");
                    SharedPreferences facebookPrefs = getSharedPreferences("facebook", Context.MODE_PRIVATE);
                    if (!(facebookPrefs.getString("fullName", "").toString()).equals("") && !(facebookPrefs.getString("imageUrl", "").toString()).equals("")) {
                        //Log.i("checkStatusHere", "I am here!!");
                        String fullName = facebookPrefs.getString("fullName", "").toString();
                        // Log.i("checkStatusHere", fullName);
                        lf.profileTest = findViewById(R.id.tvtext);
                        lf.user_profile_image = findViewById(R.id.user_profile_image);
                        lf.profileTest.setText(fullName);
                        Picasso.with(getApplicationContext()).load(facebookPrefs.getString("imageUrl", "")).into(lf.user_profile_image);
                    }
                }
            }

        });

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.saugatjonchhen.rentaroom",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void changeTabsFont() {
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(type);
                }
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static void setToolbarBackButton() {
        btn_back.setVisibility(View.VISIBLE);
    }

    public static void hideToolbarBackButton() {
        btn_back.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (hf.flag == 1) {
            goHome();
            backFlag = 0;
        } else {
            if (backFlag == 1) {
                backFlag = 0;
                super.onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "Press Back Button Once Again To Exit", Toast.LENGTH_SHORT).show();
                backFlag++;
            }
        }
    }

    public void goHome() {
        hf.flag = 0;
        hf.homeFrame = findViewById(R.id.homeFrame);
        hf.homeFrame.setVisibility(View.VISIBLE);
        hf.SeeAllFrame = findViewById(R.id.SeeAllFrame);
        hf.SeeAllFrame.setVisibility(View.GONE);
        MainActivity.hideToolbarBackButton();
    }

    public void checkLogin() {
        lf.loginFrame = findViewById(R.id.loginFrame);
        lf.profileFrame = findViewById(R.id.profileFrame);
//        if ((lf.login_flag == true)) {
//            lf.loginFrame.setVisibility(View.GONE);
//            lf.profileFrame.setVisibility(View.VISIBLE);
//        } else if (lf.login_flag == false) {
//            lf.loginFrame.setVisibility(View.VISIBLE);
//            lf.profileFrame.setVisibility(View.GONE);
//        }
        if ((SaveSharedPreference.getUserName(MainActivity.this).length() == 0) && lf.login_flag == false) {
            // call Login Activity
            lf.loginFrame.setVisibility(View.VISIBLE);
            lf.profileFrame.setVisibility(View.GONE);
        } else {
            // Stay at the current activity.
            lf.loginFrame.setVisibility(View.GONE);
            lf.profileFrame.setVisibility(View.VISIBLE);
        }
//        if (lf.login_flag == false) {
//
//        } else if (lf.login_flag == true) {
//        }
    }

    public static void chat_btn_click(){
        TabLayout.Tab tab = tabLayout.getTabAt(3);
        tab.select();

    }

    public static class SaveSharedPreference {
        static final String PREF_USER_NAME = "username";

        public static SharedPreferences getSharedPreferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public static void setUserName(Context ctx, String userName) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(PREF_USER_NAME, userName);
            editor.commit();
        }

        public static String getUserName(Context ctx) {
            return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
        }
    }

}
