package com.aman.civiladvocacyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationRequest getLocationRequest;

    public static String API_KEY = "AIzaSyDPT2EWuGtzf7jmoS099yWQ8XwLUHDVfjw";
    public static String TAG = "MainActivityTag";
    private Location getLastLocation;

    private static int Delay = 5;
    public static final int PERMISSIONS = 10;

    private FusedLocationProviderClient getLocProvider;
    private static int uDelay = 1000;
    private static int fDelay = 1000;
    Double lat, longitude;
    RecyclerView GovofficerList;
    HashMap<String, String> OfficerDesignation = new HashMap<>();
    TextView uAddr;
    String addr = "";
    ArrayList<officers_civil_advocacy> OfficersArrayList = new ArrayList();

    public static final String CHOICE = "selected_item";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GovofficerList = findViewById(R.id.activity_main_1);

        GovofficerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        GovofficerList.setAdapter(new adapter_civil_advocacy(this, OfficersArrayList));
        uAddr = findViewById(R.id.activity_det_addr);

        if (PermissAllowed()) {
            changeLocation();
            getLocation();
            showLocation();
        }
        uAddr.setOnClickListener(view -> SearchAddressIcon());
    }


    public void changeLocation() {
        Log.d(TAG, "ChangeLocation:");
        if (getLocProvider != null) {
            getLocProvider.removeLocationUpdates(locationCallback).addOnCompleteListener(task -> {
                        Log.d(TAG, "Paused at LocationChange!");
                    });
        }
    }

    public void getLocation() {
            Log.d(TAG, "In getLocation");
            getLocProvider = null;
            getLocProvider = LocationServices.getFusedLocationProviderClient(this);
            getLocationRequest = LocationRequest.create();
            getLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            getLocationRequest.setSmallestDisplacement(Delay);
            getLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            getLocationRequest.setInterval(uDelay);
            getLocationRequest.setFastestInterval(fDelay);
        Log.d(TAG, "FetchLocationPref: end");
    }

    private void showLocation() {
        Log.d(TAG, "in showLocation:");
        try {
            getLocProvider.requestLocationUpdates(getLocationRequest, locationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "in showLocation end");
    }


    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locResult) {
            super.onLocationResult(locResult);
            Log.d(TAG, "Location " + locResult);
            double long_loc = locResult.getLastLocation().getLongitude();
            double lat = locResult.getLastLocation().getLatitude();
            getLatLong(lat, long_loc);
        }
    };

    public void getLatLong(double lat, double long_loc) {
        this.longitude = long_loc;
        this.lat = lat;
        Log.d(TAG, "Latitude is " + lat + "Longitude is " + long_loc);
        addr = getAddress(this.lat, this.longitude);
        getJson();
    }

    @SuppressLint("SetTextI18n")
    private void getJson() {
        String url;

        Log.d(TAG, "getJson: start");

        if(helper_civil_advocacy.InternetConnection(this)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage("Data cannot be accessed/loaded without an internet connection")
                    .setPositiveButton("Yes", (dialogInterface, i) -> startActivity(getIntent()))
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .show();
        }else {
            RequestQueue NewRequestQueue = Volley.newRequestQueue(this);
            if(addr.matches("")){
                uAddr.setText("No data found for this loction");
            }else{
                uAddr.setText(addr);
                try {
                    addr = URLEncoder.encode(addr, String.valueOf(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                url = "https://www.googleapis.com/civicinfo/v2/representatives?key=" + API_KEY + "&address=" + addr;
                //url="https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyDPT2EWuGtzf7jmoS099yWQ8XwLUHDVfjw&address=60616";

                Log.d("API url is", url);
                StringRequest MakeStringRequest = new StringRequest(Request.Method.GET, url, response -> {
                    Log.d("API response is", response.toString());
                    parseJsonRes(response);
                }, error -> Log.d(TAG, "Error :" + error.toString()));

                NewRequestQueue.add(MakeStringRequest);
            }
        }
    }

    private void parseJsonRes(String res) {
        OfficersArrayList.clear();
        try {
            JSONObject NewJSONObject = new JSONObject(res);
            String officials="officials";
            JSONArray GovernOfficer = NewJSONObject.getJSONArray(officials);
            String offices="offices";
            JSONArray designation = NewJSONObject.getJSONArray(offices);
            String normalizedInputPara="normalizedInput";

            JSONObject normalInput = NewJSONObject.getJSONObject(normalizedInputPara);
            Log.d(TAG, "JSONResponse: JSON file fetch");
            String officialIndices="officialIndices";
            for (int i = 0; i < designation.length(); i++) {
                JSONArray index = designation.getJSONObject(i).getJSONArray(officialIndices);
                for (int j = 0; j < index.length(); j++) {
                    Log.d(TAG, "JSONResponse is: print");
                    String name="name";
                    OfficerDesignation.put(index.getString(j), designation.getJSONObject(i).getString(name));
                }

            }
            for (String keyset : OfficerDesignation.keySet()) {
                System.out.println(keyset + " " + OfficerDesignation.get(keyset));
            }

            for (int itr = 0; itr < GovernOfficer.length(); itr++) {
                Log.d(TAG, "printing details");
                officers_civil_advocacy GOfficers = new officers_civil_advocacy();

                JSONObject item = GovernOfficer.getJSONObject(itr);
                String party="party";
                GOfficers.setOrganizingParty(item.getString(party));
                GOfficers.setDesignation(OfficerDesignation.get(itr + ""));
                String name="name";
                GOfficers.setName(item.getString(name));


                try {
                    String email="emails";
                    GOfficers.setEmailaddress(item.getJSONArray(email).getString(0));
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    String line1="line1";
                    String city="city";
                    JSONObject TempAddress = item.getJSONArray("address").getJSONObject(0);
                    GOfficers.setAddress(TempAddress.getString(line1) + " " + TempAddress.getString(city) + " " + TempAddress.getString("state") + " " + TempAddress.getString("zip"));
                }catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    String photoUrl="photoUrl";
                    GOfficers.setImage(item.getString(photoUrl));
                }catch (Exception e){
                    e.printStackTrace();

                }

                GOfficers.setUserAddress(normalInput.getString("line1") + " " + normalInput.getString("city") + " " + normalInput.getString("state") + " " + normalInput.getString("zip"));

                try{
                    GOfficers.setWebsitelink(item.getJSONArray("urls").getString(0));

                }catch (Exception exception){
                    exception.printStackTrace();

                }
                try{
                    String channels="channels";
                    JSONArray SocialMediaJsonArray = item.getJSONArray(channels);
                    for (int i = 0; i < SocialMediaJsonArray.length(); i++) {
                        String Facebook="Facebook";
                        if (SocialMediaJsonArray.getJSONObject(i).getString("type").matches(Facebook)) {
                            GOfficers.setFacebooklink(SocialMediaJsonArray.getJSONObject(i).getString("id"));
                        }
                        String Twitter="Twitter";

                        if (SocialMediaJsonArray.getJSONObject(i).getString("type").equals(Twitter)) {
                            GOfficers.setTwitterlink(SocialMediaJsonArray.getJSONObject(i).getString("id"));
                        }
                        String YouTube="YouTube";
                        if (SocialMediaJsonArray.getJSONObject(i).getString("type").equals(YouTube)) {
                            GOfficers.setYoutubelink(SocialMediaJsonArray.getJSONObject(i).getString("id"));
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    String phones="phones";
                    GOfficers.setPhoneno(item.getJSONArray(phones).getString(0));

                }catch (Exception e){
                    e.printStackTrace();
                }
                OfficersArrayList.add(GOfficers);
            }

            GovofficerList.getAdapter().notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    String[] permiss = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    private boolean PermissAllowed() {
        List<String> PermissAllowedArrayList = new ArrayList<>();
        for (String p : permiss) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), p) != PackageManager.PERMISSION_GRANTED) {
                PermissAllowedArrayList.add(p);
            }
        }
        if (!PermissAllowedArrayList.isEmpty()) {
            ActivityCompat.requestPermissions(this, PermissAllowedArrayList.toArray(new String[PermissAllowedArrayList.size()]), PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int code, String permiList[], int[] grantRes) {
        super.onRequestPermissionsResult(code, permiList, grantRes);
        String emptyString="";
        if(code==PERMISSIONS)
        {
            if (grantRes.length > 0) {
                String line1 = emptyString;
                for (String per : permiList) {
                    if (grantRes[0] == PackageManager.PERMISSION_DENIED) {
                        line1 += "\n" + per;
                    }
                }
                if (line1.matches(emptyString)) {
                    changeLocation();
                    getLocation();
                    showLocation();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.question_mark){
            startActivity(new Intent(getApplicationContext(), activity_civil_advocacy.class));
        }
        else if(item.getItemId() == R.id.search_main){
            SearchAddressIcon();
        }
        else
        {
            Log.d(TAG, "Invalid menu");

        }
        return super.onOptionsItemSelected(item);
    }


    private String getAddress(double lat, double long_loc) {
        String emptyString="";
        String AddressString = emptyString;
        try {
            List<Address> Address = new Geocoder(this, Locale.getDefault()).getFromLocation(lat, long_loc, 1);
            if (Address != null) {
                Address ReturnedAddr = Address.get(0);
                StringBuilder RetAddrStr = new StringBuilder("");

                for (int i = 0; i <= ReturnedAddr.getMaxAddressLineIndex(); i++) {
                    RetAddrStr.append(ReturnedAddr.getAddressLine(i)).append("\n");
                }
                AddressString = RetAddrStr.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AddressString;
    }

    public  void SearchAddressIcon(){
        if(helper_civil_advocacy.InternetConnection(this)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage("Data cannot be accessed/loaded without an internet connection")
                    .setPositiveButton("Yes", (dialogInterface, i) -> startActivity(getIntent()))
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    })
                    .show();
        }else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(getApplicationContext());
            alertDialog.setTitle("Enter Address");
            alertDialog.setView(edittext);
            alertDialog.setPositiveButton("ok", (dialog, whichButton) -> {
                addr = edittext.getText().toString();
                getJson();
                Toast.makeText(MainActivity.this, edittext.getText().toString(), Toast.LENGTH_SHORT).show();
            });
            alertDialog.setNegativeButton("Cancel", (dialog, whichButton) -> {
            });
            alertDialog.show();
        }
    }
}



