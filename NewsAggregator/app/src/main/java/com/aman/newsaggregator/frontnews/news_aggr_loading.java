package com.aman.newsaggregator.frontnews;

import android.net.Uri;
import android.util.Log;

import com.aman.newsaggregator.show_newsaggr;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//done

public class news_aggr_loading {


    private String API_url = "https://newsapi.org/v2/top-headlines?";
    private String apikey = "75958f1bc3894b408fba3abd4711b6bf";
    private show_newsaggr display;
    private final String TAG = "NewsLoader";
    private String channels;
    private ArrayList<news_aggr> newslist = new ArrayList<>();


    public news_aggr_loading(show_newsaggr newsdisplay, String ch) {

        this.display = newsdisplay;
        this.channels = ch;
    }

    public void getInfoData() {

        RequestQueue reqQueue = Volley.newRequestQueue(display);

        Uri.Builder builder = Uri.parse(API_url).buildUpon();
        String sources="sources";
        builder.appendQueryParameter(sources, channels);
        builder.appendQueryParameter("apiKey", apikey);
        String endurl = builder.build().toString();

        Response.Listener<JSONObject> listener =
                response -> handleJsonOutputs(response.toString());

        Response.ErrorListener Json_error = error1 -> {
            Log.d(TAG, "getInfoData: ");
            JSONObject jsonObj;
            try {
                jsonObj = new JSONObject(new String(error1.networkResponse.data));
                Log.d(TAG, "getInfoData: " + jsonObj);
                handleJsonOutputs(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        JsonObjectRequest jsonArrayRequest =
                new JsonObjectRequest(Request.Method.GET, endurl,
                        null, listener, Json_error){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> map_headers = new HashMap<>();
                        map_headers.put("User-Agent", "News-App");
                        return map_headers;
                    }
                };

        reqQueue.add(jsonArrayRequest);
    }
    private void handleJsonOutputs(String result) {
        JSONParser(result);
        display.articleupdate(newslist);
    }

    private void JSONParser(String str) {
        try {
            JSONObject jsonObj = new JSONObject(str);
            String articles="articles";
            JSONArray jObjArray = jsonObj.getJSONArray(articles);
            for (int i = 0; i < jObjArray.length(); i++) {
                news_aggr news = new news_aggr();
                news.setNewsWriter(jObjArray.getJSONObject(i).getString("author"));
                news.setNewstitle(jObjArray.getJSONObject(i).getString("title"));
                news.setNewsURL(jObjArray.getJSONObject(i).getString("url"));
                news.setPicture(jObjArray.getJSONObject(i).getString("urlToImage"));
                news.setNewstime(jObjArray.getJSONObject(i).getString("publishedAt"));
                news.setNewsdata(jObjArray.getJSONObject(i).getString("description"));
                newslist.add(news);
            }
        } catch (Exception exception) { exception.printStackTrace(); }
    }
}
