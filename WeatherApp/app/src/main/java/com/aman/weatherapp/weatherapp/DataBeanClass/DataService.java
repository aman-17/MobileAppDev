package com.aman.weatherapp.weatherapp.DataBeanClass;

import android.net.Uri;

import com.aman.weatherapp.weatherapp.MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataService {
    private static MainActivity mainActivity;
    private static RequestQueue queue;
    private static String unit;
    private static final String farh_var = "°F";
    private static final String celc_var = "°C";

    public static void weatherInputData(MainActivity main_act, String loc_act, String conv_unit_act) {
        mainActivity = main_act;
        queue = Volley.newRequestQueue(mainActivity);
        unit = conv_unit_act;
        Uri.Builder string_build = Uri.parse("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/").buildUpon();
        string_build.appendPath(loc_act);
        string_build.appendQueryParameter("unitGroup", conv_unit_act);
        string_build.appendQueryParameter("lang","en");
        string_build.appendQueryParameter("key", "B8CA3UL3D2YL39N3S6962UHJV");
        String link_api = string_build.build().toString();
        Response.Listener<JSONObject> jsonObjectListener = response -> json_var(response.toString());
        Response.ErrorListener errorListener = error -> {
            mainActivity.updateData(null);
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link_api, null,jsonObjectListener,errorListener);
        queue.add(jsonObjectRequest);
    }

    private static void json_var(String toString) {
        try {
            JSONObject json_file = new JSONObject(toString);
            DataBean data_bean;
            ArrayList<DaysDataBean> total_days_list = new ArrayList<>();
            JSONArray total_days_arr = json_file.getJSONArray("days");
            for(int j = 0; j < total_days_arr.length(); j++) {
                ArrayList<HourlyDataBean> total_hr_list = new ArrayList<>();
                JSONObject days = (JSONObject) total_days_arr.get(j);
                JSONArray hours = days.getJSONArray("hours");
                for(int i = 0; i < hours.length(); i++) {
                    JSONObject jHourly = (JSONObject) hours.get(i);
                    HourlyDataBean total_hr_databean = new HourlyDataBean(
                            jHourly.getString("datetimeEpoch"),
                            (int)Math.ceil(jHourly.getDouble("temp"))+(unit.equals("us") ? farh_var : celc_var),
                            jHourly.getString("conditions"),
                            jHourly.getString("icon")
                    );
                    total_hr_list.add(total_hr_databean);
                }
                DaysDataBean total_days_databean = new DaysDataBean(
                        days.getString("datetimeEpoch"),
                        (int)Math.ceil(days.getDouble("tempmax"))+(unit.equals("us") ? farh_var : celc_var),
                        (int)Math.ceil(days.getDouble("tempmin"))+(unit.equals("us") ? farh_var : celc_var),
                        days.getString("precipprob"),
                        days.getString("uvindex"),
                        days.getString("description"),
                        days.getString("icon"),
                        total_hr_list
                );
                total_days_list.add(total_days_databean);
            }
            JSONObject current = json_file.getJSONObject("currentConditions");
            String wind_str;
            if(current.getString("windgust")!=null && !current.getString("windgust").equals("null")) {
                wind_str = (int) Math.ceil(current.getDouble("windgust")) + "";
            }
            else {
                wind_str = 0 + "";
            }
            String winddir=current.getString("winddir");

            String dateTime=current.getString("datetimeEpoch");
            String temp=(int)Math.ceil(current.getDouble("temp"))+(unit.equals("us") ? farh_var : celc_var);
            String feelslike=(int)Math.ceil(current.getDouble("feelslike"))+(unit.equals("us") ? farh_var : celc_var);
            String index=(int)Math.ceil(current.getDouble("uvindex"))+"";
            String sunrise=current.getString("sunriseEpoch");
            String sunset=current.getString("sunsetEpoch");
            CurrConditionDataBean currConditionDataBean = new CurrConditionDataBean(dateTime,temp,feelslike,
                    (int)Math.ceil(current.getDouble("humidity"))+"", wind_str,
                    (int)Math.ceil(current.getDouble("windspeed"))+"",winddir,
                    current.getString("visibility"),
                    (int)Math.ceil(current.getDouble("cloudcover"))+"",index,
                    current.getString("conditions"),
                    current.getString("icon"),
                    sunrise, sunset);

            String addr=json_file.getString("address");
                    String tzpffset=json_file.getString("tzoffset");
            data_bean = new DataBean(
                    addr,
                    json_file.getString("timezone"),
                    tzpffset,
                    total_days_list,
                    currConditionDataBean
            );
            mainActivity.updateData(data_bean);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
