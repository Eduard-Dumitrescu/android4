package com.example.edward.koa4app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.edward.koa4app.Models.Note;
import com.example.edward.koa4app.Services.NoteService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;
    private TextView appStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("MainActivity","Main Activity created");

        listView=(ListView)findViewById(R.id.noteList);
        appStatus=(TextView)findViewById(R.id.internetConnection);

        appStatus.setText("Online");


        final Handler h = new Handler();
        final int delay = 2000; //milliseconds




        h.postDelayed(new Runnable(){
            public void run(){

                appStatus.setText(isNetworkAvailable() ? "Online" : "Offline");

                h.postDelayed(this, delay);
            }
        }, delay);

        NoteService ns = new NoteService(){
            @Override
            protected void onPostExecute(Object o) {

                try
                {

                    //JSONObject jsonObject=new JSONObject(this.getMessage());
                    JSONArray jsonArr = new JSONArray(this.getMessage());
                    List<Note> noteList = new ArrayList<Note>();

                    for (int i=0;i<jsonArr.length();i++)
                    {
                        JSONObject obj = jsonArr.getJSONObject(i);
                        noteList.add(new Note(obj.getString("id"),obj.getString("text"),obj.getInt("updated"),obj.getInt("version")));
                    }

                    PopulateList(noteList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ns.execute();
    }

    private void PopulateList(final List<Note> noteList)
    {
        List<String> notesText = new ArrayList<String>();

        for (Note note : noteList)
        {
            notesText.add(note.getText());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                notesText );

        listView.setAdapter(arrayAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(MainActivity.this,NoteItemActivity.class);

                intent.putExtra("NoteId", noteList.get(i).getId());
                intent.putExtra("NoteText", noteList.get(i).getText());
                intent.putExtra("NoteDate", noteList.get(i).getLastUpdate());
                intent.putExtra("NoteVersion",noteList.get(i).getVersion());

                startActivity(intent);
            }

        });
    }


    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i("MainActivity","Main Activity destroyed");
    }



}
