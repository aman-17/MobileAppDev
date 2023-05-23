package com.aman.civiladvocacyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class picture_view_civil_advocacy extends AppCompatActivity {
    private ImageView Image,PartyIcon;
    private officers_civil_advocacy Officer;
    private TextView Address,designation,ministerName,organizingParty;
    ConstraintLayout layout;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_civil_advocacy);


        ministerName = (TextView)findViewById(R.id.activity_det_1);
        organizingParty = (TextView)findViewById(R.id.activity_det_3);
        Address = (TextView)findViewById(R.id.activity_det_addr);
        designation = (TextView)findViewById(R.id.activity_det_2);

        layout = findViewById(R.id.activity_det_16);
        Image = (ImageView)findViewById(R.id.activity_det_ph);
        PartyIcon = (ImageView)findViewById(R.id.activity_det_13);

        Officer = getIntent().getExtras().getParcelable(mainActivity.CHOICE);
        if(Officer.getImage() != null){
            Picasso.get().load(Officer.getImage()).placeholder(R.drawable.placeholder).into(Image);

        }else{
            Picasso.get().load(R.drawable.missing).into(Image);
        }

        ministerName.setText(Officer.getName());
        organizingParty.setText(Officer.getOrganizingParty());
        Address.setText(Officer.getUserAddress());
        designation.setText(Officer.getDesignation());

        if(Officer.getOrganizingParty().contains("Republican")){
            Log.d(MainActivity.TAG, "onCreate: Republican ");
            layout.setBackgroundColor(getResources().getColor(R.color.red));
            PartyIcon.setImageDrawable(getResources().getDrawable(R.drawable.rep_logo));
        }else if(Officer.getOrganizingParty().contains("Democratic")){
            Log.d(MainActivity.TAG, "onCreate: Democratic ");
            layout.setBackgroundColor(getResources().getColor(R.color.blue));
            PartyIcon.setImageDrawable(getResources().getDrawable(R.drawable.dem_logo));
        }else{
            Log.d(MainActivity.TAG, "onCreate: invalid party");
            layout.setBackgroundColor(getResources().getColor(R.color.black));
        }

    }
}


