package com.example.androidnotes;

import java.io.Serializable;

public class Notes implements Serializable {

    private String title;
    private String content;
    private String date;


    public Notes() {
        this.title = MainActivity.empty_string;
        this.content = MainActivity.empty_string;
        this.date = MainActivity.empty_string;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Title is:-"+title+ "Content is:-"+content+"Date is:-"+ date;
    }
}
