package com.aman.weatherapp.weatherapp.DataBeanClass;

import java.io.Serializable;
import java.util.List;

public class DaysDataBean implements Serializable {

    private String temperatureMin;
    private String precipprobility;

    private String dtTimeEp;
    private String tempratureMax;

    private String icons;
    private List<HourlyDataBean> hourlyList;
    private String uVIndex;
    private String desc;

    private String eveningtime;
    private String nighttime;
    private String morningtime;
    private String afternoontime;

    public DaysDataBean(String dtTimeEp, String tempratureMax, String desc, String precipprobility, String uVIndex, String icons, String morningtime, String afternoontime, String eveningtime, String nighttime) {

        this.precipprobility = precipprobility;
        this.uVIndex = uVIndex;
        this.dtTimeEp = dtTimeEp;
        this.tempratureMax = tempratureMax;

        this.morningtime = morningtime;
        this.afternoontime = afternoontime;
        this.desc = desc;
        this.icons = icons;
        this.eveningtime = eveningtime;
        this.nighttime = nighttime;
    }

    public DaysDataBean(String dtTimeEp, String tempratureMax, String temperatureMin, String precipprobility, String uVIndex, String desc, String icons, List<HourlyDataBean> hourlyList) {

        this.temperatureMin = temperatureMin;
        this.precipprobility = precipprobility;
        this.dtTimeEp = dtTimeEp;
        this.tempratureMax = tempratureMax;

        this.icons = icons;
        this.hourlyList = hourlyList;
        this.uVIndex = uVIndex;
        this.desc = desc;
    }



    public String getTemperatureMin() {
        return temperatureMin;
    }

    public String getPrecipprobility() {
        return precipprobility;
    }

    public String getDtTimeEp() {
        return dtTimeEp;
    }


    public String getTempratureMax() {
        return tempratureMax;
    }



    public String getIcons() {
        return icons;
    }

    public List<HourlyDataBean> getHourlyList() {
        return hourlyList;
    }

    public String getuVIndex() {
        return uVIndex;
    }

    public String getDesc() {
        return desc;
    }


    public String getEveningtime() {
        return eveningtime;
    }

    public String getNighttime() {
        return nighttime;
    }
    public String getMorningtime() {
        return morningtime;
    }

    public String getAfternoontime() {
        return afternoontime;
    }


    @Override
    public String toString() {
        return "datetimeEpoch='" + dtTimeEp + '\'';
    }
}
