package com.example.edward.koa4app.Services;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.edward.koa4app.Models.Note;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



public class NoteUpdateService extends AsyncTask
{

    private String message;
    private int statusCode;
    private Note note;

    public NoteUpdateService(Note note)
    {
        this.statusCode = 0;
        this.note = note;
    }

    public NoteUpdateService(String id,String text,int lastUpdated,int version)
    {
        this.note = new Note(id,text,lastUpdated,version);
        this.statusCode = 0;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    protected Object doInBackground(Object[] params) {
        try {
            JSONObject msg = new JSONObject();


            msg.put("id",this.note.getId());
            msg.put("text",this.note.getText());
            msg.put("updated",this.note.getLastUpdate());
            msg.put("version",this.note.getVersion());

            URL url= null;

            url = new URL("http://10.0.2.2:3000/note/"+this.note.getId());

            HttpURLConnection httpCon = null;

            httpCon = (HttpURLConnection) url.openConnection();

//            httpCon.setDoOutput(true);
//            httpCon.setDoInput(true);
//            httpCon.setUseCaches(false);


            /// pt header
            httpCon.setRequestProperty( "Content-Type", "application/json" );
            httpCon.setRequestProperty("Accept", "application/json");
//          httpCon.setRequestProperty("plizwork","YOU ARE SO FUCKING STUPID");

            httpCon.setRequestMethod("PUT");

            httpCon.connect();


            // trimitem la server
            OutputStream os = httpCon.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            osw.write(msg.toString());
            osw.flush();
            osw.close();

            /*
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader( httpCon.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            this.message = sb.toString();*/

            this.statusCode=(int)httpCon.getResponseCode();

            Log.e("myerror","StatusCode + "+httpCon.getResponseCode());

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return  null;
    }
}
