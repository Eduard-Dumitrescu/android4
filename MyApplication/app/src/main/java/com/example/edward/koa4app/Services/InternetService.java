package com.example.edward.koa4app.Services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edward on 30-Jan-17.
 */

public class InternetService
{
    public boolean checkActiveInternetConnection()
    {
        if (isNetworkAvailable())
        {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                //Log.e(LOG_TAG, "Error: ", e);
            }
        } else {
            //Log.d(LOG_TAG, "No network present");
        }
        return false;
    }

    private boolean isNetworkAvailable()
    {
       // ConnectivityManager connectivityManager
        //        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
       // NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
       // return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return true;
    }

}
