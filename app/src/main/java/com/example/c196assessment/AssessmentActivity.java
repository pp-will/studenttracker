package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.AssessmentEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.c196assessment.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.example.c196assessment.utilities.Constants.COURSE_ID_KEY;

public class AssessmentActivity extends AppCompatActivity {

    ListView listView;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        setTitle("Course Assessments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);
        mDb = AppDatabase.getInstance(getApplicationContext());
        listView = findViewById(R.id.assessmentsListView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), AssessmentEditActivity.class);
            int assessmentId;
            List<AssessmentEntity> assessmentsList = mDb.assessmentDao().getAssessmentsForCourse(courseId);
            assessmentId = assessmentsList.get(position).getId();
            intent.putExtra(ASSESSMENT_ID_KEY, assessmentId);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        });

        updateList(courseId);

        FloatingActionButton mFab = findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssessmentEditActivity.class);
                intent.putExtra(COURSE_ID_KEY, courseId);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);

        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, EditCourse.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList(int courseId) {
        List<AssessmentEntity> allAssessments = mDb.assessmentDao().getAssessmentsForCourse(courseId);
        String[] assessments = new String[allAssessments.size()];
        if (!allAssessments.isEmpty()) {
            for(int i = 0; i < allAssessments.size(); i++) {
                assessments[i] = allAssessments.get(i).getAssessmentType() + ": " +allAssessments.get(i).getAssessmentTitle();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessments);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}