package com.aman.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aman.civiladvocacyapp.R;

public class activity_civil_advocacy extends AppCompatActivity {

    private static final String TAG = "AboutActivityTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: AboutAppActivity initiated");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_civil_advocacy);
        findViewById(R.id.google_civic_api).setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developers.google.com/civic-information"));
            Log.d(TAG, "onCreate: Going back");
            startActivity(browserIntent);
        });
    }
}