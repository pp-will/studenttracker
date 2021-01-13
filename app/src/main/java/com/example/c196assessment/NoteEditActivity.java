package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.NoteEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import static com.example.c196assessment.utilities.Constants.COURSE_ID_KEY;
import static com.example.c196assessment.utilities.Constants.NOTE_ID_KEY;
import static com.example.c196assessment.utilities.Constants.NOTE_TEXT;

public class NoteEditActivity extends AppCompatActivity {

    AppDatabase mDb;
    NoteEntity note;
    FloatingActionButton mFab;
    FloatingActionButton emailFab;
    EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDb = AppDatabase.getInstance(getApplicationContext());

        mFab = findViewById(R.id.fab);
       // emailFab = findViewById(R.id.emailFab);
        noteText = findViewById(R.id.noteText);
        int courseId = extras.getInt(COURSE_ID_KEY);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteText = noteText.getText().toString();
                if(extras.containsKey(NOTE_ID_KEY)) {
                    int noteId = extras.getInt(NOTE_ID_KEY);
                    note = mDb.noteDao().getNoteDetails(noteId);
                    note.setNoteText(newNoteText);
                    mDb.noteDao().updateNote(note);
                    Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                    int courseId = note.getCourseId();
                    intent.putExtra(COURSE_ID_KEY, courseId);
                    startActivity(intent);
                } else {
                    Date date = new Date();
                    note = new NoteEntity(courseId, newNoteText, date);
                    mDb.noteDao().insertNote(note);
                    Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                    intent.putExtra(COURSE_ID_KEY, courseId);
                    startActivity(intent);
                }
            }
        });

       /* emailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = noteText.getText().toString();
                int noteId = extras.getInt(NOTE_ID_KEY);
                Intent
            }
        });*/

        if (extras.containsKey(NOTE_ID_KEY)) {
            Log.println(Log.INFO, "TAG", "EDIT NOTE!!!");

            int noteId = extras.getInt(NOTE_ID_KEY);
            note = mDb.noteDao().getNoteDetails(noteId);
            setTitle("Edit Note");
            noteText.setText(note.getNoteText());
        } else {
            Log.println(Log.INFO, "TAG", "NEW NOTE!!!");
            setTitle("New Note");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_email_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);
        mDb = AppDatabase.getInstance(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        int courseId = note.getCourseId();
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, NotesActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        } else if (id == R.id.action_email) {
            int noteId = note.getId();
            Intent intent = new Intent(this, EmailActivity.class);
            intent.putExtra(NOTE_ID_KEY, noteId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}