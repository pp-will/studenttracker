package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.NoteEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.c196assessment.utilities.Constants.NOTE_ID_KEY;

public class EmailActivity extends AppCompatActivity {

    AppDatabase mDb;
    TextView emailText;
    EditText editTextEmailAddress;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Bundle extras = getIntent().getExtras();
        int noteId = extras.getInt(NOTE_ID_KEY);
        Log.println(Log.INFO, "TAG", "note id from email ::: " + noteId);

        mDb = AppDatabase.getInstance(getApplicationContext());
        NoteEntity note = mDb.noteDao().getNoteDetails(noteId);
        setTitle(getString(R.string.send_note_as_email));

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        emailText = findViewById(R.id.emailText);
        emailText.setText(note.getNoteText());

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                String TO = editTextEmailAddress.getText().toString();

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.note_sent_via_email_subject));
                emailIntent.putExtra(Intent.EXTRA_TEXT, note.getNoteText());

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(EmailActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}