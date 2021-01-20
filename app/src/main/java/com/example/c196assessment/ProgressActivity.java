package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.AssessmentEntity;
import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.utilities.AssessmentData;
import com.example.c196assessment.utilities.CourseData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.c196assessment.utilities.Constants.COURSE_ID_KEY;

public class ProgressActivity extends AppCompatActivity {

    AppDatabase mDb;
    List<AssessmentData> assessmentDataList;
    List<CourseData> courseDataList;
    TextView pendingStatusDisplay;
    TextView passedStatusDisplay;
    TextView failedStatusDisplay;
    TextView courseStatusDisplay;
    TextView planToTakeStatusDisplay;
    TextView completedStatusDisplay;
    TextView droppedStatusDisplay;

    ListView courseList;
    ListView assessmentList;
    List<CourseEntity> courses;
    //List<CourseEntity> filteredCourses;
    List<AssessmentEntity> assessments;

    double completedCourseCount = 0;
    double completedAssessmentCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        setTitle(getString(R.string.track_progress));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDb = AppDatabase.getInstance(getApplicationContext());
        assessmentDataList = mDb.assessmentDao().getAssessmentProgress();
        courseDataList = mDb.courseDao().getCourseProgress();
        courses = mDb.courseDao().getListOfCourses();
        assessments = mDb.assessmentDao().getAssessmentList();


        //init UI
        pendingStatusDisplay = findViewById(R.id.pendingStatusDisplay);
        passedStatusDisplay = findViewById(R.id.passedStatusDisplay);
        failedStatusDisplay = findViewById(R.id.failedStatusDisplay);
        courseStatusDisplay = findViewById(R.id.courseStatusDisplay);
        planToTakeStatusDisplay = findViewById(R.id.planToTakeStatusDisplay);
        completedStatusDisplay = findViewById(R.id.completedStatusDisplay);
        droppedStatusDisplay = findViewById(R.id.droppedStatusDisplay);
        courseList = findViewById(R.id.coursesList);
        assessmentList = findViewById(R.id.assessmentList);

        List<CourseEntity> filteredCourses = new ArrayList<>();
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();

        for(int i = 0; i < assessmentDataList.size(); i++) {
            if (assessmentDataList.get(i).getStatus().equals("Pending")) {
                pendingStatusDisplay.setText(String.valueOf(assessmentDataList.get(i).getCount()));
            } else if (assessmentDataList.get(i).getStatus().equals("Passed")) {
                passedStatusDisplay.setText(String.valueOf(assessmentDataList.get(i).getCount()));
                completedAssessmentCount = assessmentDataList.get(i).getCount();
            } else if (assessmentDataList.get(i).getStatus().equals("Failed")) {
                failedStatusDisplay.setText(String.valueOf(assessmentDataList.get(i).getCount()));
            }
        }

        for(int i = 0; i < courseDataList.size(); i++) {
            if (courseDataList.get(i).getStatus().equals("In Progress")) {
                courseStatusDisplay.setText(String.valueOf(courseDataList.get(i).getCount()));
            } else if (courseDataList.get(i).getStatus().equals("Plan to Take")) {
                planToTakeStatusDisplay.setText(String.valueOf(courseDataList.get(i).getCount()));
            } else if (courseDataList.get(i).getStatus().equals("Completed")) {
                completedStatusDisplay.setText(String.valueOf(courseDataList.get(i).getCount()));
                completedCourseCount = courseDataList.get(i).getCount();
            } else if (courseDataList.get(i).getStatus().equals("Dropped")) {
                droppedStatusDisplay.setText(String.valueOf(courseDataList.get(i).getCount()));
            }
        }

        //courses list

        for(int i = 0; i < courses.size(); i++) {
            Date today = new Date();
            //
            LocalDate todayLD = LocalDate.now();
            LocalDate courseLD = convertToLocalDateTimeViaInstant(courses.get(i).getStartDate());
            Date startDate = courses.get(i).getStartDate();
            if(courseLD.compareTo(todayLD) == 0 || courseLD.compareTo(todayLD) > 0) {
                filteredCourses.add(courses.get(i));
            }
        }
        String[] courseNames = new String[filteredCourses.size()];
        if(!filteredCourses.isEmpty()) {
            for(int i = 0; i < filteredCourses.size(); i++) {
                courseNames[i] = filteredCourses.get(i).getCourseName() + " - " + filteredCourses.get(i).getStartDate();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
        courseList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //assessments list

        for(int i = 0; i < assessments.size(); i++) {
            Date today = new Date();
            LocalDate assessmentTodayLD = LocalDate.now();
            LocalDate assessmentGoalDateLD = convertToLocalDateTimeViaInstant(assessments.get(i).getGoalDate());
            Date goalDate = assessments.get(i).getGoalDate();
            if(assessmentGoalDateLD.compareTo(assessmentTodayLD) == 0 || assessmentGoalDateLD.compareTo(assessmentTodayLD) > 0) {
                filteredAssessments.add(assessments.get(i));
            }
        }
        String[] assessmentNames = new String[filteredAssessments.size()];
        if(!filteredAssessments.isEmpty()) {
            for(int i = 0; i < filteredAssessments.size(); i++) {
                assessmentNames[i] = filteredAssessments.get(i).getAssessmentTitle() + " - " + filteredAssessments.get(i).getGoalDate();
            }
        }
        ArrayAdapter<String> assessmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessmentNames);
        assessmentList.setAdapter(assessmentAdapter);
        assessmentAdapter.notifyDataSetChanged();

        // Notifications

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Sample Notification", "Sample Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_progress, menu);
        return true;
    }

    public LocalDate convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_notify) {
            sampleNotification();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sampleNotification() {
        String title = "Sample notification";
        String subject = "Sample notification subject";
        String body = "This is a test notification from Progress screen";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProgressActivity.this, "Sample Notification");
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.ic_course_icon);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ProgressActivity.this);
        managerCompat.notify(1, builder.build());

    }
}