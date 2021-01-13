package com.example.c196assessment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.AssessmentAlertEntity;
import com.example.c196assessment.database.CourseAlertEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    AppDatabase mDb;
    List<AssessmentAlertEntity> assessmentAlerts;
    List<CourseAlertEntity> courseAlerts;
    LocalDate todaysDate = LocalDate.now();
    LocalDate assessmentAlertDate;
    LocalDate courseAlertDate;
    LocalDate courseAlertEndDate;

    @OnClick(R.id.enterButton)
    void enterButtonHandler() {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.progressButton)
    void progressButtonHandler() {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDb = AppDatabase.getInstance(getApplicationContext());
        assessmentAlerts = mDb.assessmentAlertDao().getAllAlerts();
        courseAlerts = mDb.courseAlertDao().getAllAlerts();
        for(int i = 0; i < assessmentAlerts.size(); i++) {
            assessmentAlertDate = convertToLocalDateTimeViaInstant(assessmentAlerts.get(i).getDate());
            Log.println(Log.INFO, "TAG", "ALERT DATE LD : " + assessmentAlertDate);

            if (assessmentAlertDate.compareTo(todaysDate) == 0) {
                createAlert(assessmentAlerts.get(i), i);
            }
        }
        //createAlert();
        for(int i = 0; i < courseAlerts.size(); i++) {
            courseAlertDate = convertToLocalDateTimeViaInstant(courseAlerts.get(i).getStartDate());
            courseAlertEndDate = convertToLocalDateTimeViaInstant(courseAlerts.get(i).getEndDate());
            Log.println(Log.INFO, "TAG", "course alert today = " + courseAlertDate);
            Log.println(Log.INFO, "TAG", "todays date = " + todaysDate);
            if (courseAlertDate.compareTo(todaysDate) == 0) {
                createCourseAlert(courseAlerts.get(i), i);
            }
        }
    }

    public void createAlert(AssessmentAlertEntity alert, int i) {
        String title = "Assessment Due:";
        String body = alert.getMessage();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Sample Notification");
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.ic_course_icon);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(i, builder.build());
    }

    public void createCourseAlert(CourseAlertEntity alert, int i) {
        String title = "Course Starting:";
        String body = alert.getMessage();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Sample Notification");
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.ic_course_icon);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(i, builder.build());
    }


    public LocalDate convertToLocalDateTimeViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
}
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
    return java.util.Date.from(dateToConvert.atStartOfDay()
      .atZone(ZoneId.systemDefault())
      .toInstant());
}

}