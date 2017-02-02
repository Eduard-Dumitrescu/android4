package com.example.edward.koa4app.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.edward.koa4app.Models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 30-Jan-17.
 */

public class LocalDBHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "NoteApp.db";

    private static final String TABLE_Notes = "Movies";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_TEXT = "Text";
    private static final String COLUMN_UPDATED = "Updated";
    private static final String COLUMN_VERSION = "Version";

    public LocalDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query = "CREATE TABLE " + TABLE_Notes + "(" +
                COLUMN_ID + " TEXT PRIMARY KEY, " +
                COLUMN_TEXT + " TEXT, " +
                COLUMN_UPDATED + " INTEGER, " +
                COLUMN_VERSION + " INTEGER " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Notes);
        onCreate(sqLiteDatabase);
    }

    public List<Note> GetNotes()
    {
        List<Note> notesList = new ArrayList<Note>();

        SQLiteDatabase db = getWritableDatabase();
        String querry = "SELECT * FROM " + TABLE_Notes + ";";

        Cursor c = db.rawQuery(querry,null);

        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_TEXT)) != null)
            {
                String id = c.getString(c.getColumnIndex(COLUMN_ID));
                String text = c.getString(c.getColumnIndex(COLUMN_TEXT));
                int date  = c.getInt(c.getColumnIndex(COLUMN_UPDATED));
                int version =  c.getInt(c.getColumnIndex(COLUMN_VERSION));

                notesList.add(new Note(id,text,date,version));
            }
            c.moveToNext();
        }

        db.close();

        return notesList;
    }


    public void AddNote(String id,String text,int date,int version)
    {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,id);
        values.put(COLUMN_TEXT,text);
        values.put(COLUMN_UPDATED,date);
        values.put(COLUMN_VERSION,version);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_Notes,null,values);
        db.close();
    }

    public void AddNote(Note note)
    {
        AddNote(note.getId(),note.getText(),note.getLastUpdate(),note.getVersion());
    }

    public void DeleteMovieById(int id)
    {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM  " + TABLE_Notes + " WHERE " + COLUMN_ID + "=\'" + id + "\';");

    }

    public void UpdateMovieById(String id,String text,int date,int version)
    {
        String query = "UPDATE " + TABLE_Notes + " SET " +
                COLUMN_TEXT + "=\'" + text +"\', " +
                COLUMN_UPDATED + "=" + date +", " +
                COLUMN_VERSION + "=" + version + " WHERE " +
                COLUMN_ID + "=\'" + id + "\';";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }

    public void UpdateMovieById(Note note)
    {
        UpdateMovieById(note.getId(),note.getText(),note.getLastUpdate(),note.getVersion());
    }

    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String querry = "SELECT * FROM " + TABLE_Notes;

        Cursor c = db.rawQuery(querry,null);

        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_TEXT)) != null)
            {
                dbString += c.getString(c.getColumnIndex(COLUMN_ID));
                dbString += ",";
                dbString += c.getString(c.getColumnIndex(COLUMN_TEXT));
                dbString += ",";
                dbString += c.getString(c.getColumnIndex(COLUMN_UPDATED));
                dbString += ",";
                dbString += c.getString(c.getColumnIndex(COLUMN_VERSION));
                dbString += "@";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;

    }


}
