package com.aman.weatherapp.weatherapp.DataBeanClass;

import java.io.Serializable;

public class CurrConditionDataBean implements Serializable {
    private String datetime;
    private String temperature;
    private String feelsLikeTemp;
    private String humid;
    private String windG;
    private String wSpeed;
    private String windDirection;
    private String visible;
    private String cloudCoverage;
    private String uVIndex;
    private String condition;
    private String icons;
    private String sunriseEp;
    private String sunsetEp;

    public CurrConditionDataBean(String datetime, String temperature, String feelsLikeTemp, String humid, String windG, String wSpeed, String windDirection, String visible, String cloudCoverage, String uVIndex, String condition, String icons, String sunriseEp, String sunsetEp) {
        this.datetime = datetime;
        this.temperature = temperature;
        this.feelsLikeTemp = feelsLikeTemp;
        this.humid = humid;
        this.windG = windG;
        this.wSpeed = wSpeed;
        this.windDirection = windDirection;
        this.visible = visible;
        this.cloudCoverage = cloudCoverage;
        this.uVIndex = uVIndex;
        this.condition = condition;
        this.icons = icons;
        this.sunriseEp = sunriseEp;
        this.sunsetEp = sunsetEp;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public String getHumid() {
        return humid;
    }

    public String getWindG() {
        return windG;
    }

    public String getwSpeed() {
        return wSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getVisible() {
        return visible;
    }

    public String getCloudCoverage() {
        return cloudCoverage;
    }

    public String getuVIndex() {
        return uVIndex;
    }

    public String getCondition() {
        return condition;
    }

    public String getIcons() {
        return icons;
    }

    public String getSunriseEp() {
        return sunriseEp;
    }

    public String getSunsetEp() {
        return sunsetEp;
    }

    @Override
    public String toString() {
        return
                "datetimeEpoch='" + datetime;
    }
}
