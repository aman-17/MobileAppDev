package com.aman.newsaggregator;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.aman.newsaggregator.frontnews.info_newsaggr;
import com.aman.newsaggregator.frontnews.news_aggr;
import com.aman.newsaggregator.frontnews.news_aggr_loading;

import java.io.Serializable;
import java.util.ArrayList;
//done

public class show_newsaggr extends Service {

    private boolean bool = true;
    private ServiceReceiver servicer;
    private ArrayList<news_aggr> articlelist = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        servicer = new ServiceReceiver();
        IntentFilter intentfilter = new IntentFilter(MainActivity.service_action_call);
        registerReceiver(servicer, intentfilter);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            stopSelf();
            unregisterReceiver(servicer);
            bool = false;
            super.onDestroy();
        }catch(IllegalArgumentException e){ }
    }

    public void articleupdate(ArrayList<news_aggr> arrayList) {
        articlelist.clear();
        articlelist = new ArrayList<>(arrayList);
        Intent intent = new Intent(MainActivity.action_story);
        intent.putExtra(MainActivity.data_article, (Serializable) articlelist);
        sendBroadcast(intent);
        articlelist.clear();
    }

    public class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == MainActivity.service_action_call) {
                    if (intent.hasExtra(MainActivity.data_source)) {
                        info_newsaggr newssources = (info_newsaggr) intent.getSerializableExtra(MainActivity.data_source);
                        news_aggr_loading newsLoader = new news_aggr_loading(show_newsaggr.this, "" + newssources.getInfo_id());
                        newsLoader.getInfoData();
                    }
            } }
    }}