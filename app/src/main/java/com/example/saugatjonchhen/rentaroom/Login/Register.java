package com.example.saugatjonchhen.rentaroom.Login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.BuildConfig;
import com.example.saugatjonchhen.rentaroom.R;
import com.example.saugatjonchhen.rentaroom.other.Utility;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Register extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText fullName, userName, password, confirm_pwd, email;
    private Button register;
    private ImageView upload_image;
    private String userChoosenTask;
    private Uri mImageCaptureUri;
    private int REQUEST_CAMERA, SELECT_FILE, CROP_FROM_CAMERA;
    private Spinner spinnerDropDown;
    private String[] gender = {"Male", "Female", "Others"};
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        setToolbarTitle();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AvenirNextLTPro-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar = findViewById(R.id.toolbar_register);
        TextView txt_title = findViewById(R.id.register_toolbar_title);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        txt_title.setTypeface(type);

        ImageButton btn_back = (ImageButton) findViewById(R.id.backButtonToolbarRegister);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Drop down Gender
        spinnerDropDown = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_dropdown_item, gender);
        spinnerDropDown.setAdapter(adapter);

        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                int sid = spinnerDropDown.getSelectedItemPosition();
//                Toast.makeText(getBaseContext(), "You have selected Gender : " + gender[sid],
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //till here

        //DatePicker for DOB
        myCalendar = Calendar.getInstance();
        final EditText edittext = (EditText) findViewById(R.id.dob);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edittext);
            }
        };
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fullName = (EditText) findViewById(R.id.editText_fName);
        userName = (EditText) findViewById(R.id.editText_uName2);
        password = (EditText) findViewById(R.id.editText_pwd2);
        confirm_pwd = (EditText) findViewById(R.id.editText_confirmPwd);
        upload_image = (ImageView) findViewById(R.id.upload_image);
        email = (EditText) findViewById(R.id.editText_email);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Toast.makeText(Register.this, "Your registration is valid!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private boolean validate() {
        String fullName, userName, password, confirm_pwd, email;
        fullName = this.fullName.getText().toString();
        userName = this.userName.getText().toString();
        password = this.password.getText().toString();
        confirm_pwd = this.confirm_pwd.getText().toString();
        email = this.email.getText().toString();

        if (fullName.length() == 0) {
            this.fullName.setError("Please enter the correct Full Name.");
            return false;
        } else if (userName.length() == 0) {
            this.userName.setError("Please enter the correct User Name.");
            return false;
        } else if (email.length() == 0) {
            this.email.setError("Please enter the correct email address.");
            return false;
        } else if (password.length() <= 6 || password.length() == 0) {
            this.password.setError("Password length must be greater than 6 characters.");
            return false;
        } else if (confirm_pwd.length() <= 6 || password.length() == 0) {
            this.confirm_pwd.setError("Password length must be greater than 6 characters.");
            return false;
        } else {
            if (password.equals(confirm_pwd)) {
                return true;
            } else {
                this.confirm_pwd.setError("Password and confirm password do not match.");
                return false;
            }
        }
    }

    private void updateLabel(EditText editText) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }


    private void setToolbarTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.register);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo")) {
                        doCameraTask();
                    } else if (userChoosenTask.equals("Choose from Library")) {
                        galleryIntent();
                    }
                } else {
                    //code for deny
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("ab", String.valueOf(resultCode));
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE && data != null) {
                mImageCaptureUri = data.getData();
                doCrop();
            } else if (requestCode == REQUEST_CAMERA) {
                doCrop();
            } else if (requestCode == CROP_FROM_CAMERA) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    //uploadImage = true;
                    upload_image.setImageBitmap(photo);
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists())
                    f.delete();
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                upload_image.setImageURI(null);
                upload_image.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "There is an error. Please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                boolean result = Utility.checkPermission(Register.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) {
                        doCameraTask();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result) {
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    public void doCameraTask() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i("intent", String.valueOf(intent));
        mImageCaptureUri = FileProvider.getUriForFile(Register.this, BuildConfig.APPLICATION_ID + ".provider", new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        Register.this.grantUriPermission("com.android.camera", mImageCaptureUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doCrop() {
        startCropImageActivity(mImageCaptureUri);
    }

    private void startCropImageActivity(Uri CapturedUri) {
        CropImage.activity(CapturedUri)
                .setAspectRatio(1, 1)
                .start(this);
    }


}
