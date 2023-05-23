package com.aman.weatherapp.weatherapp.WeekActivityClass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aman.weatherapp.weatherapp.DataBeanClass.DataBean;
import com.aman.weatherapp.weatherapp.DataBeanClass.DaysDataBean;
import com.aman.weatherapp.weatherapp.DataBeanClass.HourlyDataBean;
import com.aman.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeekActivity extends AppCompatActivity {
    DataBean dBean;
    WeeklyActAdapterhandler weeklyAdapter;
    RecyclerView recycler;
    List<DaysDataBean> daysDataList = new ArrayList<>();
    final String constant_data="data";

    final String days = " 15 Day";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view4_ac);
        Bundle bundle =getIntent().getBundleExtra(constant_data);
        dBean = (DataBean) bundle.getSerializable(constant_data);
        setTitle(dBean.getAddr()+ days);
        Date dateTime;
        SimpleDateFormat dateF = new SimpleDateFormat("EEEE, MM/dd", Locale.getDefault());

        ArrayList<DaysDataBean> daysBeanList = (ArrayList<DaysDataBean>) dBean.getDaysList();

        for(int x = 0; x < daysBeanList.size(); x++){

            ArrayList<HourlyDataBean> hourlydataList = (ArrayList<HourlyDataBean>) daysBeanList.get(x).getHourlyList();
            long datetime = Long.parseLong(daysBeanList.get(x).getDtTimeEp());
            dateTime = new Date(datetime * 1000);
            DaysDataBean daysBean = new DaysDataBean(
                    dateF.format(dateTime),
                    daysBeanList.get(x).getTempratureMax()+"/"+daysBeanList.get(x).getTemperatureMin(),
                    daysBeanList.get(x).getDesc(),
                    "("+daysBeanList.get(x).getPrecipprobility()+"% precip.)",
                    "UV Index: "+ daysBeanList.get(x).getuVIndex(),
                    daysBeanList.get(x).getIcons(),
                    hourlydataList.get(8).getTemperature(),
                    hourlydataList.get(13).getTemperature(),
                    hourlydataList.get(17).getTemperature(),
                    hourlydataList.get(22).getTemperature()
            );
            daysDataList.add(daysBean);
            if(String.valueOf(daysDataList.size()).matches("15"))
                break;
        }

        recycler = findViewById(R.id.v1_ac_view);
        weeklyAdapter = new WeeklyActAdapterhandler(this, daysDataList);
        recycler.setAdapter(weeklyAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}