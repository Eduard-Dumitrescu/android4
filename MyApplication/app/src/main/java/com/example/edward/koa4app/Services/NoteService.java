package com.example.edward.koa4app.Services;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edward on 30-Jan-17.
 */

public class NoteService extends AsyncTask {

    private String message;

    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NoteService()
    {
        this.statusCode=0;
    }

    @Override
    protected Object doInBackground(Object[] params)
    {
        try
        {

            URL url= null;

            url = new URL("http://10.0.2.2:3000/note");

            HttpURLConnection httpCon = null;

            httpCon = (HttpURLConnection) url.openConnection();

//            httpCon.setDoOutput(true);
//            httpCon.setDoInput(true);
//            httpCon.setUseCaches(false);


            /// pt header
            httpCon.setRequestProperty( "Content-Type", "application/json" );
            httpCon.setRequestProperty("Accept", "application/json");
//            httpCon.setRequestProperty("plizwork","YOU ARE SO FUCKING STUPID");

            httpCon.setRequestMethod("GET");

            httpCon.connect();

            this.statusCode=httpCon.getResponseCode();

            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader( httpCon.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            this.message = sb.toString();

            this.statusCode=(int)httpCon.getResponseCode();

            Log.e("myerror","StatusCode + "+httpCon.getResponseCode());

        }
        catch (IOException e) {
            e.printStackTrace();
        }




        return  null;
    }
}

