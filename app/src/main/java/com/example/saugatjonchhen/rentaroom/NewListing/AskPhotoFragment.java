package com.example.saugatjonchhen.rentaroom.NewListing;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.BuildConfig;
import com.example.saugatjonchhen.rentaroom.Featured_listings_class_individual_item;
import com.example.saugatjonchhen.rentaroom.Login.Register;
import com.example.saugatjonchhen.rentaroom.R;
import com.example.saugatjonchhen.rentaroom.other.Utility;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskPhotoFragment extends Fragment {
    View view;
    Button btn_next;
    ImageView roomImage_1, roomImage_2, btn_back;
    Uri resultUri;
    public boolean clickeventFlag;
    private String userChoosenTask;
    private Uri mImageCaptureUri;
    private int REQUEST_CAMERA, SELECT_FILE, CROP_FROM_CAMERA;
    Bundle b;


    public AskPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ask_photo, container, false);

        btn_back = view.findViewById(R.id.imgBtn_back_photos);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.new_listings_frame, new AskDescriptionFragment(), "AskDescriptionFragment").commit();
            }
        });

        btn_next = view.findViewById(R.id.btn_preview);
        roomImage_1 = view.findViewById(R.id.roomImage_1);
        roomImage_2 = view.findViewById(R.id.roomImage_2);

        roomImage_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                clickeventFlag = true;
            }
        });

        roomImage_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                clickeventFlag = false;
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                Intent intent = new Intent(getActivity(), Featured_listings_class_individual_item.class);
                b = getArguments();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    Log.i("bitmap123",bitmap.toString());
                    Log.i("bitmap123",resultUri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String filename = "bitmap.jpeg";
                FileOutputStream stream = null;
                try {
                    stream = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                //Cleanup
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap.recycle();
                //Pop intent
                b.putString("mainImage", filename);
                b.putString("previewTest","preview");
                intent.putExtras(b);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ab", String.valueOf(resultCode));
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE && data != null) {
                mImageCaptureUri = data.getData();
                doCrop();
            } else if (requestCode == REQUEST_CAMERA) {
                doCrop();
            } else if (requestCode == CROP_FROM_CAMERA) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    if (clickeventFlag == true) {
                        roomImage_1.setImageBitmap(photo);
                    } else if (clickeventFlag == false) {
                        roomImage_2.setImageBitmap(photo);
                    }
                    File f = new File(mImageCaptureUri.getPath());
                    if (f.exists())
                        f.delete();
                }
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.i("iamhere", "iamhere0");
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                if (clickeventFlag == true) {
                    roomImage_1.setImageURI(null);
                    roomImage_1.setImageURI(resultUri);
                } else if (clickeventFlag == false) {
                    roomImage_2.setImageURI(null);
                    roomImage_2.setImageURI(resultUri);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), error + "There is an error. Please try again.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                boolean result = Utility.checkPermission(getActivity());
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

    public void doCameraTask() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i("intent", String.valueOf(intent));
        mImageCaptureUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        getActivity().grantUriPermission("com.android.camera", mImageCaptureUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void doCrop() {
        startCropImageActivity(mImageCaptureUri);
    }

    private void startCropImageActivity(Uri CapturedUri) {
        CropImage.activity(CapturedUri)
                .setAspectRatio(1, 1)
                .start(getContext(), this);
    }

}
