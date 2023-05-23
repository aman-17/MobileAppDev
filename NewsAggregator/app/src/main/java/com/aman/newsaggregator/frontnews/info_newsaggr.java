package com.aman.newsaggregator.frontnews;
import java.io.Serializable;
public class info_newsaggr implements Serializable, Comparable<info_newsaggr> {
    private String info_id;
    private String news;
    private String URL_news;
    private String buckets;
    public void setInfo_id(String info_id) {

        this.info_id = info_id;
    }
    public String getNews() {

        return news;
    }
    public info_newsaggr() {
    }
    public String getInfo_id() {

        return info_id;
    }
    public void setURL_news(String URL_news) {

        this.URL_news = URL_news;
    }
    public String getBuckets() {

        return buckets;
    }
    public void setNews(String news) {

        this.news = news;
    }
    public String getURL_news() {

        return URL_news;
    }
    public void setBuckets(String NewsCategory) {

        this.buckets = NewsCategory;
    }
    public int compareTo(info_newsaggr other) {

        return news.compareTo(other.news);
    }

}
