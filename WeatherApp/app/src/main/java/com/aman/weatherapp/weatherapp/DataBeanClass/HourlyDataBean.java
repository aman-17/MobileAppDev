package com.aman.weatherapp.weatherapp.DataBeanClass;

import java.io.Serializable;

public class HourlyDataBean implements Serializable {

    private String icons;
    private String temperature;
    private String hoursDtimeEp;
    private String times;
    private String conditions;

    public HourlyDataBean(String hoursDtimeEp, String times, String icons, String temperature, String conditions) {
        this.conditions = conditions;
        this.icons = icons;
        this.temperature = temperature;
        this.hoursDtimeEp = hoursDtimeEp;
        this.times = times;
    }

    public HourlyDataBean(String hoursDtimeEp, String temperature, String conditions, String icons) {

        this.conditions = conditions;
        this.icons = icons;
        this.hoursDtimeEp = hoursDtimeEp;
        this.temperature = temperature;
    }



    public String getConditions() {
        return conditions;
    }

    public String getIcons() {
        return icons;
    }
    public String getHoursDtimeEp() {
        return hoursDtimeEp;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return "hoursDatetimeEpoch='" + hoursDtimeEp + '\'';
    }
}
