package com.aman.newsaggregator.frontnews;

import android.net.Uri;
import android.util.Log;

import com.aman.newsaggregator.MainActivity;
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
//Done

public class info_newsaggr_loading {
    private final String TAG = "InformationLoader";
    private String api_key = "75958f1bc3894b408fba3abd4711b6bf";

    private MainActivity mainActivity;
    private String API_url = "https://newsapi.org/v2/sources";

    private ArrayList<String> category_list = new ArrayList<>();

    private ArrayList<info_newsaggr> source_list = new ArrayList<>();
    private String buckets;

    public info_newsaggr_loading(MainActivity mainactivity, String buckets) {
        this.mainActivity = mainactivity;
        if (buckets.isEmpty() || buckets.equals("all")) { this.buckets = ""; }
        else { this.buckets = buckets; }
    }

    public void getInfoData(MainActivity mainActivity) {
        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        Uri.Builder buildURL = Uri.parse(API_url).buildUpon();
        String category="category";
        buildURL.appendQueryParameter(category, buckets);
        buildURL.appendQueryParameter("apiKey", api_key);
        String complete_url = buildURL.build().toString();

        Response.Listener<JSONObject> list =
                response -> manageOutput(response.toString());

        Response.ErrorListener error = jsonError -> {
            Log.d(TAG, "getInfoData: ");
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(new String(jsonError.networkResponse.data));
                Log.d(TAG, "getInfoData: " + jsonObject);
                manageOutput(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        JsonObjectRequest jsonArrayReq =
                new JsonObjectRequest(Request.Method.GET, complete_url,
                        null, list, error){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("User-Agent", "News-App");
                        return headers;
                    }
                };
        queue.add(jsonArrayReq);
    }

    private void parseJSON(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonObjArray = jsonObj.getJSONArray("sources");

            for (int i = 0; i < jsonObjArray.length(); i++) {
                info_newsaggr information = new info_newsaggr();
                information.setURL_news(jsonObjArray.getJSONObject(i).getString("url"));
                information.setBuckets(jsonObjArray.getJSONObject(i).getString("category"));
                information.setInfo_id(jsonObjArray.getJSONObject(i).getString("id"));
                information.setNews(jsonObjArray.getJSONObject(i).getString("name"));
                source_list.add(information);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < source_list.size(); i++) {
            mainActivity.setTitle("News Gateway" + " (" + source_list.size() + ")");
            if (!category_list.contains(source_list.get(i).getBuckets())) {
                category_list.add(source_list.get(i).getBuckets());
            }
        }
    }

    private void manageOutput(String result) {
        parseJSON(result);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() { mainActivity.setInfo(source_list, category_list); }
        }
        );
    }
}
