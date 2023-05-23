package com.aman.civiladvocacyapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_civil_advocacy extends RecyclerView.Adapter<adapter_civil_advocacy.ViewHolder> {

    private static final String TAG = "GovernmentOfficersAdapter";
    MainActivity mainActivity;
    Activity OfficerDetails;
    ArrayList<officers_civil_advocacy> GOfficersList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "in onCreateViewHolder");
        return new ViewHolder(OfficerDetails.getLayoutInflater().inflate(R.layout.menu_list_civil_advocacy,parent,false));
    }
    public adapter_civil_advocacy(Activity activity, ArrayList<officers_civil_advocacy> arrayList) {
        this.OfficerDetails = activity;
        Log.d(TAG, "in GOfficersAdapter");
        this.GOfficersList = arrayList;
        Log.d(TAG, "out GOfficersAdapter");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        officers_civil_advocacy Officer = GOfficersList.get(position);
        holder.designation.setText(Officer.getDesignation());
        holder.itemView.setOnClickListener(view -> OfficerDetails.startActivity(new Intent(OfficerDetails, details_civil_advocacy.class)
                .putExtra(mainActivity.CHOICE,Officer)));
        holder.Officer_Name.setText(Officer.getName()+" ("+Officer.getOrganizingParty()+")");

    }
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Start");
        return GOfficersList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView designation;

        TextView Officer_Name;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            designation = itemView.findViewById(R.id.activity_det_2);
            Officer_Name = itemView.findViewById(R.id.OfficerName);
            image = itemView.findViewById(R.id.activity_main1_ph);
        }
    }

}
