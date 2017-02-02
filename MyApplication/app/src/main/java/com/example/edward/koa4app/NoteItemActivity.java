package com.example.edward.koa4app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.edward.koa4app.Models.Note;
import com.example.edward.koa4app.Services.NoteUpdateService;

import org.w3c.dom.Text;

import java.util.Date;

public class NoteItemActivity extends AppCompatActivity
{
    private TextView noteId;
    private EditText noteText;
    private TextView noteDate;
    private TextView noteVersion;

    private Note currentNote;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_item);

        Log.i("NoteItemActivity","Note activity created");

        noteId =(TextView)findViewById(R.id.noteId);
        noteText =(EditText) findViewById(R.id.noteText);
        noteDate =(TextView)findViewById(R.id.noteDate);
        noteVersion = (TextView)findViewById(R.id.noteVersion);

        currentNote = new Note(getIntent().getStringExtra("NoteId"),getIntent().getStringExtra("NoteText"),getIntent().getIntExtra("NoteDate",0),getIntent().getIntExtra("NoteVersion",0));

        noteId.setText("Id-ul este : "+this.currentNote.getId());
        noteText.setText(this.currentNote.getText());
        noteDate.setText("Updated : "+ new Date(this.currentNote.getLastUpdate()).toString());
        noteVersion.setText("Veriunea notitei : " + this.currentNote.getVersion());
    }

    public void updateNote(View view)
    {
        this.currentNote.setText(noteText.getText().toString());
        NoteUpdateService nus = new NoteUpdateService(this.currentNote);
        nus.execute();

        Intent intent = new Intent(NoteItemActivity.this,MainActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        Log.i("NoteItemActivity","Note activity destroyed");
    }
}
