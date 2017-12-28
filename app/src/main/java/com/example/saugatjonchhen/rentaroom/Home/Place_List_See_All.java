package com.example.saugatjonchhen.rentaroom.Home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.saugatjonchhen.rentaroom.R;

public class Place_List_See_All extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__list__see__all);

        Bundle b = getIntent().getExtras();
        String placeName = b.getString("name");

        if (placeName.equals("Kathmandu")) {
            Toast.makeText(this, "Kathmandu", Toast.LENGTH_SHORT).show();
        } else if (placeName.equals("Bhaktapur")) {
            Toast.makeText(this, "Bhaktapur", Toast.LENGTH_SHORT).show();
        } else if (placeName.equals("Lalitpur")) {
            Toast.makeText(this, "Lalitpur", Toast.LENGTH_SHORT).show();
        } else if (placeName.equals("Pokhara")) {
            Toast.makeText(this, "Pokhara", Toast.LENGTH_SHORT).show();
        }
    }
}
