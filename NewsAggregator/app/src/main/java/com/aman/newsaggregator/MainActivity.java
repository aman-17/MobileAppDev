package com.aman.newsaggregator;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aman.newsaggregator.frontnews.info_newsaggr;
import com.aman.newsaggregator.frontnews.info_newsaggr_loading;
import com.aman.newsaggregator.frontnews.news_aggr;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerlayout;
    private ListView listview;
    static final String service_action_call = "ACTION_SERVICE_MSG";
    static final String action_story = "ACTION_STORY_NEWS";
    static final String data_source = "DATA_SOURCE";
    static final String data_article = "NEWS_DATA_ARTICLE";
    private ArrayList<String> text_list = new ArrayList<>();
    private Menu menu;
    private ArrayList<news_aggr> articles_list = new ArrayList<>();
    private InputBroadReceiver inputBroadReceiver;
    private ArrayList<String> category_array_list = null;
    private Page_Adapter pageadap;
    private ActionBarDrawerToggle action_bar;
    private HashMap<String, info_newsaggr> hashmap = new HashMap<String, info_newsaggr>();
    private List<Fragment> fraglist;
    private ViewPager viewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mainIntent = new Intent(MainActivity.this, show_newsaggr.class);
        startService(mainIntent);
        inputBroadReceiver = new InputBroadReceiver();
        IntentFilter intentfilter = new IntentFilter(action_story);
        registerReceiver(inputBroadReceiver, intentfilter);
        drawerlayout = findViewById(R.id.drawlayout);
        listview = findViewById(R.id.drawsources);
        int drawLimit=5;
        listview.setAdapter(new ArrayAdapter<>(this, R.layout.back_page, text_list));
        listview.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { selectmenuItem(position); }
                });
        action_bar = new ActionBarDrawerToggle(this, drawerlayout, R.string.navigator_drawer, R.string.navigator_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (isNetworkAvailable()) {
            if (savedInstanceState == null) {
                info_newsaggr_loading informationLoader = new info_newsaggr_loading(this, "");
                informationLoader.getInfoData(this);
            } else {
                setTitle(savedInstanceState.getCharSequence("title"));
                setInfo((ArrayList<info_newsaggr>) savedInstanceState.getSerializable("sourcelist"),
                        savedInstanceState.getStringArrayList("categorylist"));
            }
        } else { showDialogBox(); }
        fraglist = getFragment_list();
        pageadap = new Page_Adapter(getSupportFragmentManager());
        viewer = (ViewPager) findViewById(R.id.activity_main_text0);
        viewer.setAdapter(pageadap);
        viewer.setOffscreenPageLimit(10);

        if (savedInstanceState != null) {
            for (int i = 0; i < savedInstanceState.getInt("size"); i++) { fraglist.add(getSupportFragmentManager().getFragment(savedInstanceState,
                    "NewsFragment" + Integer.toString(i))); }
        } else { viewer.setBackgroundResource(R.drawable.newsback); }
        pageadap.notifyDataSetChanged();
    }

    public void showDialogBox()
    {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        String title="Network Error";
        alertbuilder.setTitle(title);
        alertbuilder.setMessage("Network Issue. Please retry.");
        alertbuilder.setNegativeButton(Html.fromHtml("<font color='#254E58'>OK</font>"),
                new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int arg1) { }});
        alertbuilder.show();
        AlertDialog alertdialog = alertbuilder.create();
        alertdialog.show();
    }

    @Override
    protected void onPostCreate(Bundle postcreatestate) {
        super.onPostCreate(postcreatestate);
        action_bar.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration configchange) {
        super.onConfigurationChanged(configchange);
        action_bar.onConfigurationChanged(configchange);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mainmenu) {
        this.menu = mainmenu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (action_bar.onOptionsItemSelected(item)) { return true; }
        info_newsaggr_loading loader = new info_newsaggr_loading(this, "" + item);
        loader.getInfoData(this);
        return super.onOptionsItemSelected(item);
    }

    private void selectmenuItem(int number) {
        viewer.setBackground(null);
        setTitle(text_list.get(number));
        Intent maintained = new Intent(service_action_call);
        int maxCOunt=10;
        maintained.putExtra(data_source, hashmap.get(text_list.get(number)));
        sendBroadcast(maintained);
        drawerlayout.closeDrawer(listview);
    }

    public void setInfo(ArrayList<info_newsaggr> infolist, ArrayList<String> catlist) {
        hashmap.clear();
        text_list.clear();

        Collections.sort(infolist);
        for (info_newsaggr source : infolist) {
            text_list.add(source.getNews());
            hashmap.put(source.getNews(), source); }
        ((ArrayAdapter<String>) listview.getAdapter()).notifyDataSetChanged();
        if (this.category_array_list == null) {
            this.category_array_list = new ArrayList<>(catlist);
            if (menu != null) {
                this.category_array_list.add(0, "all");
                for (String c : this.category_array_list) {
                    //adding to menu
                    menu.add(c);
            }
         }
    }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu mainmenu) {
        if (this.category_array_list != null) {
            mainmenu.clear();
            for (String categorylist : this.category_array_list) { mainmenu.add(categorylist); }
        }
        return super.onPrepareOptionsMenu(mainmenu);
    }

    private List<Fragment> getFragment_list() {
        List<Fragment> fraglist = new ArrayList<Fragment>();
        return fraglist;
    }

    @Override
    protected void onDestroy() {
        try{
            super.onDestroy();
            Intent mainintent = new Intent(MainActivity.this, show_newsaggr.class);
            stopService(mainintent);
            unregisterReceiver(inputBroadReceiver);
        }
        catch(Exception e){ }
    }

    @Override
    protected void onStop() {
        try{
            super.onStop();
            Intent mainintent = new Intent(MainActivity.this, show_newsaggr.class);
            stopService(mainintent);
            unregisterReceiver(inputBroadReceiver);
            }
        catch(Exception e){ }
    }

    @Override
    protected void onPause() {
        try{
            unregisterReceiver(inputBroadReceiver);
            super.onPause();
        }
        catch(Exception e){ }
    }

    public boolean isNetworkAvailable() {
        boolean bool = true;
        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnectedOrConnecting()) { return bool; }
        else { return !bool; }
    }

    @Override
    public void onSaveInstanceState(Bundle bundlesavedInstanceState) {
        super.onSaveInstanceState(bundlesavedInstanceState);
        int total = 0;
        for (int loop = 0; loop < fraglist.size(); loop++)
        {
            if (fraglist.get(loop).isAdded())
            {
                total++;
                String newsfrag = "NewsFragment" + loop;
                getSupportFragmentManager().putFragment(bundlesavedInstanceState, newsfrag, fraglist.get(loop));
            }
        }
        bundlesavedInstanceState.putInt("size", total);
        bundlesavedInstanceState.putStringArrayList("categorylist", category_array_list);
        ArrayList<info_newsaggr> sourcelist = new ArrayList<>();
        for (String hasher : hashmap.keySet()) { sourcelist.add(hashmap.get(hasher)); }
        bundlesavedInstanceState.putSerializable("sourcelist", sourcelist);
        bundlesavedInstanceState.putCharSequence("title", getTitle());
    }

    public void setTitle() {
    }

    public class InputBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context inputcontext, Intent inputintent) {
            switch (inputintent.getAction()) {
                case action_story:
                    if (inputintent.hasExtra(data_article)) {
                        articles_list = (ArrayList) inputintent.getSerializableExtra(data_article);
                        alterFragment(articles_list);
                    }
                    break;
            }
        }

        private void alterFragment(List<news_aggr> articlelist) {
            if(!isNetworkAvailable())
              showDialogBox();
            for (int adapterloop = 0; adapterloop < pageadap.getCount(); adapterloop++)
            {
                pageadap.notifyChangeInPosition(adapterloop);
            }
            fraglist.clear();

            for (int fragloop = 0; fragloop < articlelist.size(); fragloop++)
              fraglist.add(info2_newsaggr.newInstance((articlelist.get(fragloop)), "" + fragloop, "" + articlelist.size()));
            pageadap.notifyDataSetChanged();
            viewer.setCurrentItem(0);
        }
    }

    private class Page_Adapter extends FragmentPagerAdapter {
        private long item = 0;
        public Page_Adapter(FragmentManager fm)
        {
            super(fm);
        }
        public void notifyChangeInPosition(int position)
        {
            item += getCount() + position;
        }
        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position)
        {
            return fraglist.get(position);
        }

        @Override
        public int getCount()
        {
            return fraglist.size();
        }

        @Override
        public long getItemId(int position)
        {
            return item + position;
        }
    }

}