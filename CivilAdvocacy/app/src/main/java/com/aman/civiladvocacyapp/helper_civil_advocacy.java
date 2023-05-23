package com.aman.civiladvocacyapp;

import android.content.Context;
import android.net.ConnectivityManager;

public class helper_civil_advocacy {
    public static  boolean InternetConnection(Context context) {
        ConnectivityManager NetworkCheck = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return NetworkCheck.getActiveNetworkInfo() == null || !NetworkCheck.getActiveNetworkInfo().isConnected();
    }

}
