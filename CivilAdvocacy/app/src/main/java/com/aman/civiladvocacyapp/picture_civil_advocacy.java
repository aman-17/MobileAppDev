package com.aman.civiladvocacyapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class picture_civil_advocacy extends AppCompatActivity {
    MainActivity mainAct;
    officers_civil_advocacy Officers;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list_civil_advocacy);
        Officers = getIntent().getExtras().getParcelable(mainAct.CHOICE);
        ImageView Photo = findViewById(R.id.activity_main1_ph);

        if (Officers.getImage() != null) {
            Log.d(MainActivity.TAG, "onCreate: Fetch minister photo");
            Picasso.get().load(Officers.getImage()).placeholder(R.drawable.placeholder).into(Photo);
        } else {
            Log.d(MainActivity.TAG, "onCreate: Fetch missing photo ");
            Picasso.get().load(R.drawable.missing).into(Photo);
        }
    }
}
