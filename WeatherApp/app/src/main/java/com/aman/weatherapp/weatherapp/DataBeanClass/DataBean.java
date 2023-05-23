package com.aman.weatherapp.weatherapp.DataBeanClass;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable {
    private String addr;
    private String time;

    private List<DaysDataBean> daysList;
    private CurrConditionDataBean currentConditionBean;
    private String timezzoneOffset;


    public DataBean(String addr, String time, String timezzoneOffset, List<DaysDataBean> daysList, CurrConditionDataBean currentConditionList) {
        this.addr = addr;
        this.daysList = daysList;
        this.currentConditionBean = currentConditionList;
        this.time = time;
        this.timezzoneOffset = timezzoneOffset;

    }

    public String getAddr() {
        return addr;
    }

    public String getTime() {
        return time;
    }

    public String getTimezzoneOffset() {
        return timezzoneOffset;
    }

    public List<DaysDataBean> getDaysList() {
        return daysList;
    }

    public CurrConditionDataBean getCurrentConditionBean() {
        return currentConditionBean;
    }

    @Override
    public String toString() {
        return "address='" + addr + '\'' ;
    }
}
