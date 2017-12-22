package com.example.saugatjonchhen.rentaroom.Login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.saugatjonchhen.rentaroom.MainActivity;
import com.example.saugatjonchhen.rentaroom.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    View view;
    EditText userName, password;
    Button login, register, logOut;
    SignInButton glogin;
    LoginButton fblogin;
    TextView my_listings;
    public String profile_picture_url;
    public TextView profileTest;
    public ImageView user_profile_image;
    CallbackManager callbackManager;
    public FrameLayout loginFrame, profileFrame;
    GoogleSignInClient mGoogleSignInClient;
    public static boolean login_flag;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;
    private Button editProfile;
    private ImageButton btn_settings;
    private SharedPreferences facebookPrefs;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getActivity());
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initializeEverything();
        facebookLogin();
        googleLogin();


        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.SaveSharedPreference.setUserName(getActivity(), "Saugat");
                login_flag = true;
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_flag = true;
                signIn();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Register.class));
            }
        });

        //After login in profile frame
        //no use for now
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login_flag = false;
//                LoginManager.getInstance().logOut();
//                signOutFromGoogle();
//                loginFrame.setVisibility(View.VISIBLE);
//                profileFrame.setVisibility(View.GONE);
//            }
//        });

        //edit profile button click
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        updateUI(account);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            if (data != null && resultCode != 0) {
//                Log.i("resultcode", String.valueOf(resultCode));
//                Log.i("data123", String.valueOf(data));
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                login_flag = true;
                loginFrame.setVisibility(View.GONE);
                profileFrame.setVisibility(View.VISIBLE);
                MainActivity.SaveSharedPreference.setUserName(getActivity(), "Saugat");
            } else {
                login_flag = false;
                profileFrame.setVisibility(View.GONE);
                loginFrame.setVisibility(View.VISIBLE);
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            Drawable img = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.googleicon);
            img.setBounds(0, 0, 55, 55);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Regular.otf");
                tv.setTextSize(15);
                tv.setTypeface(type);
                tv.setBackgroundResource(R.drawable.googlebackground);
                tv.setText("Continue with Google");
                //tv.setTextColor(getResources().getColor(R.color.white));
                tv.setPadding(35, 16, 35, 16);
                tv.setCompoundDrawables(img, null, null, null);
                tv.setGravity(Gravity.CENTER);
                return;
            }
        }
    }

    public void facebookLogin() {
        fblogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        fblogin.setFragment(this);
        callbackManager = CallbackManager.Factory.create();


        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Log.i("success1", "I'm Here Success");
                //profileTest.setText("Login Success\n" + loginResult.getAccessToken());
                login_flag = true;
                loginFrame.setVisibility(View.GONE);
                profileFrame.setVisibility(View.VISIBLE);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Insert your code here
                                FacebookRequestError error = response.getError();
                                if (error != null) {
                                    Log.i("error123", error.getErrorMessage());
                                } else {
                                    //Log.i("jobject", response.toString());
                                    try {
                                        String firstName = object.getString("first_name");
                                        String lastName = object.getString("last_name");
                                        String email = object.getString("email");
                                        String gender = object.getString("gender");
                                        String fullName = firstName.concat(" " + lastName);
                                        String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                        profile_picture_url = url;
                                        profileTest.setText(fullName);
                                        Picasso.with(getActivity()).load(url).into(user_profile_image);
                                        facebookPrefs = getActivity().getSharedPreferences("facebook", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = facebookPrefs.edit();
                                        editor.putString("fullName", fullName);
                                        editor.putString("imageUrl", url);
                                        editor.putString("email", email);
                                        editor.putString("gender", gender);
                                        editor.commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,picture.type(large),first_name,last_name,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
//                Log.i("cancel1", "I'm Here cancel");
                profileTest.setText("Login Cancelled\n");
            }

            @Override
            public void onError(FacebookException error) {
//                Log.i("error1", "I'm Here error");
                profileTest.setText("Login Cancelled\n" + error.getMessage());
            }
        });
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            String personName = account.getDisplayName();
            Uri personImage = account.getPhotoUrl();
            String personEmail = account.getEmail();
            profile_picture_url = String.valueOf(personImage);
            profileTest.setText(personName);
            SharedPreferences facebookPrefs = getActivity().getSharedPreferences("facebook", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = facebookPrefs.edit();
            editor.putString("fullName", personName);
            if (personImage != null) {
                editor.putString("imageUrl", personImage.toString());
            }
            editor.putString("email", personEmail);
            editor.commit();
            if (personImage != null) {
                user_profile_image.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(personImage).into(user_profile_image);
            } else {
                user_profile_image.setVisibility(View.GONE);
            }
        }
//        else {
//            login_flag = false;
//            profileFrame.setVisibility(View.GONE);
//            loginFrame.setVisibility(View.VISIBLE);
//        }

    }

//    public void signOutFromGoogle() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        revokeAccess();
//                    }
//                });
//    }
//
//    private void revokeAccess() {
//        mGoogleSignInClient.revokeAccess()
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//                        updateUI(account);
//                        Toast.makeText(getApplicationContext(), "Logged Out Successfully", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//    }

    private void googleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // Set the dimensions of the sign-in button.

    }

    private void initializeEverything() {
        userName = (EditText) view.findViewById(R.id.editText_uName);
        password = (EditText) view.findViewById(R.id.editText_pwd);
        login = (Button) view.findViewById(R.id.btn_login);
        register = (Button) view.findViewById(R.id.btn_register_login);
        //logOut = view.findViewById(R.id.btn_log_out);
        profileTest = view.findViewById(R.id.tvtext);
        loginFrame = view.findViewById(R.id.loginFrame);
        profileFrame = view.findViewById(R.id.profileFrame);
        glogin = (SignInButton) view.findViewById(R.id.google_sign_in_button);
        fblogin = (LoginButton) view.findViewById(R.id.fb_login_button);
        signInButton = view.findViewById(R.id.google_sign_in_button);
        setGooglePlusButtonText(glogin, getString(R.string.common_signin_button_text_long));

        my_listings = view.findViewById(R.id.my_listings);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        my_listings.setTypeface(type);
        btn_settings = view.findViewById(R.id.btn_settings);
        user_profile_image = view.findViewById(R.id.user_profile_image);
        editProfile = view.findViewById(R.id.btn_editProfile);
    }


}
