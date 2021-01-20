package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196assessment.database.AlertEntity;
import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.AssessmentAlertEntity;
import com.example.c196assessment.database.AssessmentEntity;
import com.example.c196assessment.utilities.AlertReminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.c196assessment.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.example.c196assessment.utilities.Constants.COURSE_ID_KEY;

public class AssessmentEditActivity extends AppCompatActivity {

    AppDatabase mDb;
    AssessmentEntity assessment;
    FloatingActionButton mFab;
    EditText editTextAssessmentTitle;
    EditText editTextDate;
    Spinner assessmentTypeSpinner;
    Switch alertSwitch;
    Spinner statusSpinner;
    AlertEntity alert;
    AssessmentAlertEntity assessmentAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);

        //notify test
        AlarmManager alarms = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);

        AlertReminder receiver = new AlertReminder();
        IntentFilter filter = new IntentFilter("ALARM_ACTION");
        registerReceiver(receiver, filter);

        Intent intent = new Intent("ALARM_ACTION");
        intent.putExtra("param", "My scheduled action");
        PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
        // I choose 3s after the launch of my application
        alarms.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000, operation);
        // end notify test

        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDb = AppDatabase.getInstance(getApplicationContext());

        mFab = findViewById(R.id.fab);
        editTextAssessmentTitle = findViewById(R.id.editTextAssessmentTitle);
        editTextDate = findViewById(R.id.editTextDate);
        assessmentTypeSpinner = findViewById(R.id.assessmentTypeSpinner);
        statusSpinner = findViewById(R.id.statusSpinner);
        assessmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //assessment.setAssessmentType(selectedItemView.);
                Object item = parentView.getItemAtPosition(position);
                Log.println(Log.INFO, "TAG", "SPINN = " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                Log.println(Log.INFO, "TAG", "SPINN Status = " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        alertSwitch = findViewById(R.id.alertSwitch);

        // Assessment Type Spinner

        String[] assessmentTypeOptions = {"Performance", "Objective"};
        ArrayAdapter<String> assessmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentTypeOptions);
        assessmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(assessmentTypeAdapter);

        String[] statusOptions = {"Pending", "Passed", "Failed"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        // END Assessment Type Spinner

        if (extras.containsKey(ASSESSMENT_ID_KEY)) {
            setTitle(getString(R.string.edit_assessment));
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            assessment = mDb.assessmentDao().getAssessmentDetails(assessmentId);
            editTextAssessmentTitle.setText(assessment.getAssessmentTitle());

            if (assessment.getAlertSet() == 1) {
                alertSwitch.setChecked(true);
            }

            int spinnerPosition = assessmentTypeAdapter.getPosition(assessment.getAssessmentType());
            assessmentTypeSpinner.setSelection(spinnerPosition);

            int statusSpinnerPosition = statusAdapter.getPosition(assessment.getStatus());
            statusSpinner.setSelection(statusSpinnerPosition);

            Date date = new Date(String.valueOf(assessment.getGoalDate()));
            String dateStr = String.valueOf(assessment.getGoalDate());

            Format f = new SimpleDateFormat("MM/dd/YYYY");
            String strDate = f.format(assessment.getGoalDate());
            editTextDate.setText(strDate);

        } else {
            setTitle(getString(R.string.new_assessment));
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int courseId = extras.getInt(COURSE_ID_KEY);
                String assessmentName = editTextAssessmentTitle.getText().toString();
                String assessmentType = assessmentTypeSpinner.getSelectedItem().toString();
                String status = statusSpinner.getSelectedItem().toString();
                int alertSet = 0;
                if (alertSwitch.isChecked()) {
                    alertSet = 1;
                }
                Date date = new Date(editTextDate.getText().toString());
                if (extras.containsKey(ASSESSMENT_ID_KEY)) {
                    assessment = mDb.assessmentDao().getAssessmentDetails(extras.getInt(ASSESSMENT_ID_KEY));
                    assessment.setAssessmentTitle(assessmentName);
                    assessment.setAssessmentType(assessmentType);
                    assessment.setGoalDate(date);
                    assessment.setAlertSet(alertSet);
                    assessment.setStatus(status);
                    mDb.assessmentDao().updateAssessment(assessment);
                    if(assessment.getAlertSet() == 1) {
                        try {
                            AssessmentAlertEntity newAlert = setAlert(assessment.getId(), assessment.getAssessmentTitle(), assessment.getGoalDate());
                            createAlert(newAlert);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else if (assessment.getAlertSet() == 0) {
                        AssessmentAlertEntity alert = mDb.assessmentAlertDao().getAssessmentAlert(assessment.getId());
                        mDb.assessmentAlertDao().deleteAlert(alert);
                       // mDb.assessmentAlertDao().deleteAlert();
                    }
                    Intent intent = new Intent(getApplicationContext(), AssessmentActivity.class);
                    intent.putExtra(COURSE_ID_KEY, courseId);
                    startActivity(intent);
                } else {
                    assessment = new AssessmentEntity(courseId, assessmentType, assessmentName, date, alertSet, status);
                    long id;
                     id = mDb.assessmentDao().getInsertAssessment(assessment);
                     int assessmentId = (int) id;
                     assessment = mDb.assessmentDao().getAssessmentDetails(assessmentId);
                    if(assessment.getAlertSet() == 1) {
                        try {
                           AssessmentAlertEntity newAlert = setAlert(assessment.getId(), assessment.getAssessmentTitle(), assessment.getGoalDate());
                            createAlert(newAlert);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), AssessmentActivity.class);
                    intent.putExtra(COURSE_ID_KEY, courseId);
                    startActivity(intent);
                }
            }
        });

        alertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Log.println(Log.INFO, "TAG", "SWITCH = " + isChecked);
               /* if (isChecked) {
                    assessment.setAlertSet(1);
                } else {
                    assessment.setAlertSet(0);
                } */
            }
        });

        notifyChannel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(ASSESSMENT_ID_KEY)) {
            getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        }
        return true;
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
            Intent intent = new Intent(this, AssessmentActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        } else if (id == R.id.action_delete) {
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mDb.assessmentDao().deleteAssessment(assessment);
            Intent intent = new Intent(getApplicationContext(), AssessmentActivity.class);
            intent.putExtra(COURSE_ID_KEY, assessment.getCourseId());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public AssessmentAlertEntity setAlert(int assessmentId, String message, Date startDate) throws SQLException {
        AssessmentAlertEntity alert = new AssessmentAlertEntity(assessmentId, message, startDate);
        AssessmentAlertEntity newAlert;
        long id;
        id = mDb.assessmentAlertDao().insertAlert(alert);
        int alertId = (int) id;
        Log.println(Log.INFO, "TAG", "ALERT ID = " + alertId);
        newAlert = mDb.assessmentAlertDao().getAlert(alertId);
        return newAlert;
    }

    public void createAlert(AssessmentAlertEntity alert) {
        Calendar calDateNow = Calendar.getInstance();
        Log.println(Log.INFO, "TAG", "ass date = " + alert.getDate().getTime());
        Log.println(Log.INFO, "TAG", "current date = " + calDateNow.getTime().getTime());
        Date alertDate = alert.getDate();
        alertDate.setHours(23);
        alertDate.setMinutes(59);
        alertDate.setSeconds(59);
        if(alertDate.getTime() > calDateNow.getTime().getTime()) {
            Intent outIntent = new Intent(getApplicationContext(), AlertReminder.class);
            outIntent.putExtra("id", alert.getId());
            outIntent.putExtra("assessmentId", alert.getAssessmentId());
            outIntent.putExtra("message", alert.getMessage());
            outIntent.putExtra("date", alert.getDate());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    alert.getId(),
                    outIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            AlarmManager alertManage = (AlarmManager) getSystemService(ALARM_SERVICE);
            alertManage.set(AlarmManager.RTC, alert.getDate().getTime() + 10000, pendingIntent);
            Toast.makeText(getApplicationContext(), "Alert set", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Assessment goal date is past due", Toast.LENGTH_LONG).show();
        }
    }

    //FOR NOTIFICATIONS
    private void notifyChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence dgw_channel_name = "DGW Tracker NAME";
            String dgw_channel_description = "DGW Tracker DESC";
            int dgw_channel_importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifyChannel = new NotificationChannel("dgChanId", dgw_channel_name,
                    dgw_channel_importance);
            notifyChannel.setDescription(dgw_channel_description);

            NotificationManager notifyManager = getSystemService(NotificationManager.class);
            notifyManager.createNotificationChannel(notifyChannel);
            Log.println(Log.INFO, "TAG", "Notification channel set: ");
        }
    }

}