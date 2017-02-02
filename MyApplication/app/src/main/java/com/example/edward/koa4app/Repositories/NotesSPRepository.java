package com.example.edward.koa4app.Repositories;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.edward.koa4app.Models.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Edward on 30-Jan-17.
 */

public class NotesSPRepository
{
    private String jsonString;
    private List<Note> noteList;
    private Context context;

    public NotesSPRepository() {}

    public NotesSPRepository(Context context) {
        this.context = context;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Boolean isCached()
    {
        SharedPreferences pref = this.context.getApplicationContext().getSharedPreferences("NotesList", context.MODE_PRIVATE);

        return pref.contains("notesListJSON") ? true : false;
    }

    private List<Note> GetNotes()
    {
       this.noteList = new ArrayList<Note>();
        try {
            JSONArray jsonArr = new JSONArray(this.getJsonString());


            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject obj = jsonArr.getJSONObject(i);
                this.noteList.add(new Note(obj.getString("id"), obj.getString("text"), obj.getInt("updated"), obj.getInt("version")));
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return this.noteList;
    }

    public void EditNote(Note givenNote)
    {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        sb.append("[");
        for (Note note: this.noteList)
        {
            counter++;
            if(givenNote.getId() == note.getId())
            {
                note.setText(givenNote.getText());
                note.setLastUpdate((int)System.currentTimeMillis());
                note.setVersion(note.getVersion()+1);
            }
            sb.append(note.toJson());
            if(counter != this.noteList.size())
                sb.append(",");
        }
        sb.append("]");

        this.jsonString = sb.toString();
    }

}
