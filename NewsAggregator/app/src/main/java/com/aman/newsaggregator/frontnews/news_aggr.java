package com.aman.newsaggregator.frontnews;

import java.io.Serializable;
//Done

public class news_aggr implements Serializable {
    private String newsWriter;
    private String newstitle;
    private String newsdata;
    private String newsURL;
    private String newsImage;
    private String newstime;

    public news_aggr() { }

    public String getNewstitle() {

        return newstitle;
    }

    public void setNewstitle(String newstitle) {

        this.newstitle = newstitle;
    }
    public String getNewsWriter() {

        return newsWriter;
    }

    public void setNewsWriter(String newsWriter) {

        this.newsWriter = newsWriter;
    }


    public String getNewsURL() {

        return newsURL;
    }

    public void setNewsURL(String newsURL) {

        this.newsURL = newsURL;
    }
    public String getNewsdata() {

        return newsdata;
    }

    public void setNewsdata(String newsdata) {

        this.newsdata = newsdata;
    }

    public String getNewstime() {

        return newstime;
    }

    public void setNewstime(String newstime) {

        this.newstime = newstime;
    }
    public String getPicture() {

        return newsImage;
    }

    public void setPicture(String image) {

        this.newsImage = image;
    }
}
