package com.aman.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class details_civil_advocacy extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";

    private TextView organizingParty,postalAddr,designation,Name;
    private ImageView PersonImage, TwitterUrl, FacebookUrl,YoutubeUrl, PartySymbol;
    officers_civil_advocacy GOfficers;
    ConstraintLayout Constlayout;
    private TextView PhoneNo,AddrDetails,Email,WebsiteUrl;

    MainActivity mainActivity;


    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_civil_advocacy);
        GOfficers = getIntent().getExtras().getParcelable(mainActivity.CHOICE);

        designation = (TextView) findViewById(R.id.activity_det_2);
        Name = (TextView) findViewById(R.id.activity_det_1);
        postalAddr = (TextView) findViewById(R.id.activity_det_addr);
        Email = (TextView) findViewById(R.id.activity_det_10);
        AddrDetails = (TextView) findViewById(R.id.activity_det_8);
        PhoneNo = (TextView) findViewById(R.id.activity_det_9);
        organizingParty = (TextView) findViewById(R.id.activity_det_3);
        PersonImage = (ImageView) findViewById(R.id.activity_det_ph);
        TwitterUrl = (ImageView) findViewById(R.id.activity_det_tw);
        YoutubeUrl = (ImageView) findViewById(R.id.activity_det_yt);
        WebsiteUrl = (TextView) findViewById(R.id.activity_det_11);
        FacebookUrl = (ImageView) findViewById(R.id.activity_det_fb);
        Constlayout = findViewById(R.id.activity_det_16);
        PartySymbol = findViewById(R.id.activity_det_13);

        if (GOfficers.getImage() != null) {

            Log.d(TAG, "onCreate: Fetch minister photo");
            Picasso.get().load(GOfficers.getImage()).placeholder(R.drawable.placeholder).into(PersonImage);

        } else {
            Log.d(TAG, "onCreate: Fetch missing photo ");
            Picasso.get().load(R.drawable.missing).into(PersonImage);
        }

        if (GOfficers.getOrganizingParty().contains("Democratic")) {
            Constlayout.setBackgroundColor(getResources().getColor(R.color.blue));
            PartySymbol.setImageDrawable(getResources().getDrawable(R.drawable.dem_logo));
            Log.d(TAG, "onCreate: officer is Democratic ");

        } else if (GOfficers.getOrganizingParty().contains("Republican")) {
            Constlayout.setBackgroundColor(getResources().getColor(R.color.red));
            PartySymbol.setImageDrawable(getResources().getDrawable(R.drawable.rep_logo));
            Log.d(TAG, "onCreate: officer is Republican ");

        } else {
            Constlayout.setBackgroundColor(getResources().getColor(R.color.black));
            Log.d(TAG, "onCreate: officer is Nonpartisan ");

        }


        designation.setText(GOfficers.getDesignation());
        organizingParty.setText("( " + GOfficers.getOrganizingParty() + " )");
        postalAddr.setText(GOfficers.getUserAddress());
        Name.setText(GOfficers.getName());
        PhoneNo.setText(GOfficers.getPhoneno());
        WebsiteUrl.setText(GOfficers.getWebsitelink());
        AddrDetails.setText(GOfficers.getAddress());
        Email.setText(GOfficers.getEmailaddress());

        if (GOfficers.getFacebooklink() != null) {
            FacebookUrl.setVisibility(View.VISIBLE);
        } else {
            FacebookUrl.setVisibility(View.GONE);
        }

        if (GOfficers.getYoutubelink() != null) {
            YoutubeUrl.setVisibility(View.VISIBLE);
        } else {
            YoutubeUrl.setVisibility(View.GONE);
        }

        if (GOfficers.getTwitterlink() != null) {
            TwitterUrl.setVisibility(View.VISIBLE);
        } else {
            TwitterUrl.setVisibility(View.GONE);
        }
        TwitterUrl.setOnClickListener(view -> OpenTwitterLink(GOfficers.getTwitterlink()));
        FacebookUrl.setOnClickListener(view -> openFacebookLink(GOfficers.getFacebooklink()));
        YoutubeUrl.setOnClickListener(view -> openYoutubeLink(GOfficers.getYoutubelink()));

        PersonImage.setOnClickListener(view -> {
            Log.d(TAG, "onCreate:Going back to PhotoActivity");
            if (GOfficers.getImage() == null) {
                Log.d(TAG, "governmentOfficer Image returning null");
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), picture_view_civil_advocacy.class).putExtra(mainActivity.CHOICE, GOfficers));
            }
        });
    }
    

    public void OpenTwitterLink(String twitterName) {
        Intent intent;
        Log.d(TAG, "TwitterApp: GOing back to twitter");

        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user/screen_name=" + twitterName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch ( Exception e) {
            Log.d(TAG, "TwitterApp: Exception in finding user hence going with normal way");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + twitterName)));
        }
    }

    public void openYoutubeLink(String youtubeName) {
        Intent intent;
        Log.d(TAG, "YoutubeApp: going back to youtube");
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + youtubeName));
            startActivity(intent);

        } catch (Exception e) {
            Log.d(TAG, "openYoutubeLink: Exception in finding user hence going with normal way");
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + youtubeName)));
        }
    }

    public void openFacebookLink(String facebookName) {
        String fbUrl = "https://www.facebook.com/" + facebookName;
        String urlTofetch;
        PackageManager packageManager = getPackageManager();

        Log.d(TAG, "FacebookApp: going back to facebook");
        int versionCode;
        try {
            versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                urlTofetch = "fb://facewebmodal/f?href=" + fbUrl;
            } else {
                urlTofetch = "fb://page/" + facebookName;
            }
        } catch (Exception e) {
            urlTofetch = fbUrl;

        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        startActivity(facebookIntent.setData(Uri.parse(urlTofetch)));
        Log.d(TAG, "openFacebookLink: going back to openFacebookLink");
    }

}