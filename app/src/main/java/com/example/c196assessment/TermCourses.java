package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import static com.example.c196assessment.utilities.Constants.TERM_ID_KEY;

public class TermCourses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);

        initViewModel();
    }

    private void initViewModel() {
        //TODO

        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        Log.println(Log.INFO, "TERM ID", "Term id = " + termId);
    }
}