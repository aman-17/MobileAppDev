package com.aman.weatherapp.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.weatherapp.weatherapp.WeekActivityClass.WeekActivity;
import com.aman.weatherapp.weatherapp.WeekActivityClass.MainActivityAdapterhandler;
import com.aman.weatherapp.weatherapp.DataBeanClass.CurrConditionDataBean;
import com.aman.weatherapp.weatherapp.DataBeanClass.DataBean;
import com.aman.weatherapp.weatherapp.DataBeanClass.DaysDataBean;
import com.aman.weatherapp.weatherapp.DataBeanClass.HourlyDataBean;
import com.aman.weatherapp.R;
import com.aman.weatherapp.weatherapp.DataBeanClass.DataService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView desc;
    TextView date;
    TextView currTemp;
    TextView currFeels;
    TextView dirWind;

    String defaultLocation = "Chicago, Illinois";

    String country = "us";
    TextView currHumidity;
    TextView currUVIndex;
    TextView currVisibility;
    ImageView currIcon;
    TextView tempAtMorning;

    TextView tempeAtAfternoon;
    TextView tempAtEvening;
    TextView tempeAtNight;
    RecyclerView recycler;
    TextView thisSunrise, thisSunset;
    MainActivityAdapterhandler mainActivityadahandler;
    SwipeRefreshLayout swipeRefreshLay;
    Date dateF;
    final String SaveData="SaveData";
    DataBean data;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLay = findViewById(R.id.swipeRefresh);

        currFeels = findViewById(R.id.v4_main);
        desc = findViewById(R.id.v5_main);
        date = findViewById(R.id.v1_main);
        currTemp = findViewById(R.id.v2_main);
        currUVIndex = findViewById(R.id.v9_main);
        currVisibility = findViewById(R.id.v10_main);
        dirWind = findViewById(R.id.v6_main);
        currHumidity = findViewById(R.id.v7_main);
        tempAtEvening = findViewById(R.id.v13_main);
        tempeAtNight = findViewById(R.id.v14_main);
        tempAtMorning = findViewById(R.id.v11_main);
        tempeAtAfternoon = findViewById(R.id.v12_main);
        currIcon = findViewById(R.id.v3_main);
        recycler = findViewById(R.id.v1_ac_view);
        thisSunrise = findViewById(R.id.v1_sunrise_main);
        thisSunset = findViewById(R.id.v1_sunset_main);

        if(checkinternetConn()){
            setContentView(R.layout.activity_main);

            currFeels = findViewById(R.id.v4_main);
            desc = findViewById(R.id.v5_main);
            date = findViewById(R.id.v1_main);
            currTemp = findViewById(R.id.v2_main);

            currUVIndex = findViewById(R.id.v9_main);
            currVisibility = findViewById(R.id.v10_main);
            dirWind = findViewById(R.id.v6_main);
            currHumidity = findViewById(R.id.v7_main);

            tempAtEvening = findViewById(R.id.v13_main);
            tempeAtNight = findViewById(R.id.v14_main);
            tempAtMorning = findViewById(R.id.v11_main);
            tempeAtAfternoon = findViewById(R.id.v12_main);

            currIcon = findViewById(R.id.v3_main);
            recycler = findViewById(R.id.v1_ac_view);
            thisSunrise = findViewById(R.id.v1_sunrise_main);
            thisSunset = findViewById(R.id.v1_sunset_main);
            SharedPreferences sharedPreferences = getSharedPreferences(SaveData, MODE_PRIVATE);
            defaultLocation = sharedPreferences.getString("location", defaultLocation);
            country = sharedPreferences.getString("unit", country);
            DataService.weatherInputData(this, defaultLocation, country);
        }else {
            setContentView(R.layout.view3_int);
        }
        swipeRefreshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLay.setRefreshing(false);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkinternetConn(){
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return (network != null && network.isConnectedOrConnecting());
    }
    public int getimage(String image){
        image = image.replace("-", "_");
        int imageID =
                this.getResources().getIdentifier(image, "drawable", this.getPackageName());
        if (imageID == 0) {
            Log.d("Main", "Image not Found" + image);
        }
        return imageID;
    }
    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }

    public String checkTime(String str, SimpleDateFormat date){
        return date.format(new Date(Long.parseLong(str) * 1000));
    }

    public void updateData(DataBean dataBean) {
        data = dataBean;
        Log.d("MainActivity", "updateData: updating data");
        String dateFormat="EEE MMM dd h:mm a, yyyy";
        SimpleDateFormat fullDate = new SimpleDateFormat(dateFormat, Locale.getDefault());

        CurrConditionDataBean currConditionDataBean = dataBean.getCurrentConditionBean();
        setTitle(dataBean.getAddr());

        dateF = new Date(Long.parseLong(currConditionDataBean.getDatetime()) * 1000);
        System.out.println(fullDate.format(dateF));
        date.setText(fullDate.format(dateF));

        setTextViews(currConditionDataBean);
        ArrayList<DaysDataBean> daysBeanArrayList = (ArrayList<DaysDataBean>) dataBean.getDaysList();
        ArrayList<HourlyDataBean> hourlyBeanArrayList = (ArrayList<HourlyDataBean>) daysBeanArrayList.get(0).getHourlyList();
        setTemperatures(hourlyBeanArrayList);

        String timeonlyF="h:mm a";
        String dayF="EEEE";
        String hourOnlyF="h a";
        SimpleDateFormat timeOnly = new SimpleDateFormat(timeonlyF, Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat(dayF, Locale.getDefault());
        SimpleDateFormat hourOnly = new SimpleDateFormat(hourOnlyF, Locale.getDefault());
        thisSunrise.setText("Sunrise: "+ checkTime(currConditionDataBean.getSunriseEp(), timeOnly));
        thisSunset.setText("Sunset: "+ checkTime(currConditionDataBean.getSunsetEp(), timeOnly));

        ArrayList<HourlyDataBean> hourlyRecyclerDatabean = new ArrayList<>();
        boolean flag = false;
        for(int i = 0; i < daysBeanArrayList.size(); i++){
            ArrayList<HourlyDataBean>  daysinList = (ArrayList<HourlyDataBean>) daysBeanArrayList.get(i).getHourlyList();
            for(int j = 0; j < daysinList.size(); j++) {
                String day;
                if(flag) {
                    if (i == 0) {
                        day = "Today";
                    } else {
                        day = checkTime(daysinList.get(j).getHoursDtimeEp(), dayFormat);
                    }
                    HourlyDataBean hourlyDataBean = new HourlyDataBean(
                            day,
                            checkTime(daysinList.get(j).getHoursDtimeEp(), timeOnly),
                            daysinList.get(j).getIcons(),
                            daysinList.get(j).getTemperature(),
                            daysinList.get(j).getConditions()
                    );
                    hourlyRecyclerDatabean.add(hourlyDataBean);
                }
                if(checkTime(currConditionDataBean.getDatetime(), hourOnly).equals(checkTime(daysinList.get(j).getHoursDtimeEp(), hourOnly))){
                    flag = true;
                }
                if (String.valueOf(hourlyRecyclerDatabean.size()).matches("48"))
                    break;
            }
            if (String.valueOf(hourlyRecyclerDatabean.size()).matches("48"))
                break;
        }
        mainActivityadahandler = new MainActivityAdapterhandler(this, hourlyRecyclerDatabean);
        recycler.setAdapter(mainActivityadahandler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setTemperatures(ArrayList<HourlyDataBean> hourlyDataBeanArrayList) {
        tempAtMorning.setText(hourlyDataBeanArrayList.get(8).getTemperature());
        tempeAtAfternoon.setText(hourlyDataBeanArrayList.get(13).getTemperature());
        tempAtEvening.setText(hourlyDataBeanArrayList.get(17).getTemperature());
        tempeAtNight.setText(hourlyDataBeanArrayList.get(23).getTemperature());
    }

    private void setTextViews(CurrConditionDataBean currConditionDataBean) {
        currFeels.setText("Feels Like "+ currConditionDataBean.getFeelsLikeTemp());
        currIcon.setImageResource(getimage(currConditionDataBean.getIcons()));
        currTemp.setText(currConditionDataBean.getTemperature());
        String desc = currConditionDataBean.getCondition() + " ( "+ currConditionDataBean.getCloudCoverage()+ "% clouds)";
        this.desc.setText(desc.substring(0, 1).toUpperCase() + desc.substring(1));
        dirWind.setText("Winds: "+getDirection(Double.parseDouble(currConditionDataBean.getWindDirection()))+" at "+ currConditionDataBean.getwSpeed()+
                " mph gusting to "+ currConditionDataBean.getWindG()+" mph");
        currHumidity.setText("Humidity: "+ currConditionDataBean.getHumid()+"%");
        currUVIndex.setText("UV Index: "+ currConditionDataBean.getuVIndex());
        currVisibility.setText("Visibility: "+ currConditionDataBean.getVisible()+" mi");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String defaultCountry="us";
        if(R.id.units == item.getItemId())
        {
            if(!checkinternetConn()){
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                return true;
            }else{
                if(country.matches(defaultCountry)){
                    country = "metric";
                    item.setIcon(R.drawable.units_c);
                }else{
                    country = defaultCountry;
                    item.setIcon(R.drawable.units_f);
                }

                DataService.weatherInputData(this, defaultLocation, country);
                return true;
            }
        }
        else if(R.id.calender==item.getItemId())
        {
            if(!checkinternetConn()){
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                return true;
            }else{
                String para="data";
                Intent intent = new Intent(this, WeekActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(para, (Serializable) data);
                intent.putExtra(para, bundle);
                startActivity(intent);
                return true;

            }
        }

        else if(R.id.location==item.getItemId())
        {
            if(!checkinternetConn()){

                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                EditText txtUrl = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("Enter a Location")
                        .setMessage("For US locations, enter as 'City',or 'City,State'.\n"+"For international locations enter as 'City,Country'\n")
                        .setView(txtUrl)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String loc = txtUrl.getText().toString();
                                if(!loc.isEmpty())
                                {
                                    defaultLocation =loc;
                                }
                                setData();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setData();
                            }
                        })
                        .show();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
    public void setData(){
        String para="SaveData";
        String location="location";
        String unit="unit";
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences(para, MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(unit, country);
        editor.putString(location,defaultLocation);
        editor.apply();
        DataService.weatherInputData(this, defaultLocation, country);
    }
}