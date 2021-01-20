package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.CourseAlertEntity;
import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.database.MentorEntity;
import com.example.c196assessment.utilities.AlertUtils;
import com.example.c196assessment.utilities.CreateDatePicker;
import com.example.c196assessment.utilities.DateUtils;
import com.example.c196assessment.utilities.StatusPopulate;
import com.example.c196assessment.utilities.Validator;
import com.example.c196assessment.viewmodel.CourseViewModel;
import com.example.c196assessment.viewmodel.MentorViewModel;
import com.example.c196assessment.viewmodel.TermViewModel;
import com.example.c196assessment.viewmodel.ViewModelFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.c196assessment.utilities.Constants.EDITING_KEY;
import static com.example.c196assessment.utilities.Constants.TERM_ID_KEY;

public class AddCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Callable<Long> {

    CreateDatePicker createDatePicker = new CreateDatePicker();
    StatusPopulate statusPopulate = new StatusPopulate();
    DateUtils dateUtils = new DateUtils();
    Validator validator = new Validator();
    AlertUtils alertUtils = new AlertUtils();
    private MentorViewModel mentorViewModel;
    private CourseViewModel courseViewModel;
    Executor executor = Executors.newSingleThreadExecutor();
    AppDatabase mDb;
    Long courseId;

    @BindView(R.id.startMonthSpinner)
    Spinner startMonthSpinner;

    @BindView(R.id.startYearSpinner)
    Spinner startYearSpinner;

    @BindView(R.id.startDaySpinner)
    Spinner startDaySpinner;

    @BindView(R.id.endYearSpinner)
    Spinner endYearSpinner;

    @BindView(R.id.endDaySpinner)
    Spinner endDaySpinner;

    @BindView(R.id.endMonthSpinner)
    Spinner endMonthSpinner;

    @BindView(R.id.statusSpinner)
    Spinner statusSpinner;

    @BindView(R.id.courseNameEditText)
    EditText courseName;

    @BindView(R.id.editTextMentorName)
    EditText mentorName;

    @BindView(R.id.editTextMentorEmail)
    EditText mentorEmail;

    @BindView(R.id.editTextMentorPhone)
    EditText mentorPhone;

    @BindView(R.id.notificationSwitch)
    Switch notificationSwitch;

    List<String> mStartMonths = new ArrayList<>(12);
    List<Integer> mStartYears = new ArrayList<>();
    List<Integer> mStartDays = new ArrayList<>(31);
    List<String> mEndMonths = new ArrayList<>(12);
    List<Integer> mEndYears = new ArrayList<>();
    List<Integer> mEndDays = new ArrayList<>(31);
    List<String> mStatus = new ArrayList<>();

    private boolean mEditing;

    @OnClick(R.id.submitBtn)
    void submitBtnClickHandler() {
        Bundle extras = getIntent().getExtras();
        mDb = AppDatabase.getInstance(getApplicationContext());
        int termId = extras.getInt(TERM_ID_KEY);

        int startYear = (int) startYearSpinner.getSelectedItem();
        int startMonth = startMonthSpinner.getSelectedItemPosition();
        int startDay = (int) startDaySpinner.getSelectedItem();
        Log.println(Log.INFO, "TAG", "DAY SELECTED = " + startDay);
        int endYear = (int) endYearSpinner.getSelectedItem();
        int endMonth = endMonthSpinner.getSelectedItemPosition();
        int endDay = (int) endDaySpinner.getSelectedItem();
        String course = courseName.getText().toString();
        int alertSet = 0;

        if (notificationSwitch.isChecked()) {
            alertSet = 1;
        }

        String status = statusSpinner.getSelectedItem().toString();
        String instructorName = mentorName.getText().toString();
        String instructorEmail = mentorEmail.getText().toString();
        String instructorPhone = mentorPhone.getText().toString();

        Date startDate = dateUtils.createDate(startMonth, startDay, startYear);
        Date endDate = dateUtils.createDate(endMonth, endDay, endYear);

        HashMap<String, String> checkValidation = new HashMap<>();
        HashMap<String, String> validationResult = new HashMap<>();

        checkValidation.put("phone", instructorPhone);
        checkValidation.put("email", instructorEmail);
        checkValidation.put("mentorName", instructorName);
        checkValidation.put("courseName", course);

        validationResult = validator.validateInput(checkValidation);

        Boolean valid = true;

        for(HashMap.Entry<String, String> entry : validationResult.entrySet()) {
            if(entry.getValue() != "OK") {
                valid = false;
                Log.println(Log.INFO, "TAG", entry.getKey() + ": " + entry.getValue());
                break;
            }
        }

        if(!valid) {
            Log.println(Log.INFO, "TAG", "INVALID::::");
            errorDialog(validationResult);
        } else {
            Log.println(Log.INFO, "TAG", "VALID::::");
            try {
               //courseViewModel.saveCourse(termId, course, startDate, endDate, status, instructorName, instructorEmail, instructorPhone);
                CourseEntity courseEntity = new CourseEntity(termId, course, startDate, endDate, status, instructorName, instructorEmail, instructorPhone, alertSet);
                long courseId = mDb.courseDao().addCourse(courseEntity);
                //alert
                int newCourseId = (int) courseId;
                CourseEntity newCourseEntity = mDb.courseDao().getCourseDetails(newCourseId);
                String message = newCourseEntity.getCourseName();
                startDate = newCourseEntity.getStartDate();
                endDate = newCourseEntity.getEndDate();
                if(newCourseEntity.getAlertSet() == 1) {
                    CourseAlertEntity alert = setAlert(newCourseId, message, startDate, endDate);
                    Log.println(Log.INFO, "TAG", "Alertset = " + newCourseEntity.getAlertSet());
                }
                Log.println(Log.INFO, "TAG", "NO ALERT  = " + newCourseEntity.getAlertSet());
                //end alert
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

           finally {

                Intent intent = new Intent(this, TermCourses.class);
                intent.putExtra(TERM_ID_KEY, termId);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);

        //Month spinner
        startMonthSpinner = findViewById(R.id.startMonthSpinner);
        startMonthSpinner.setOnItemSelectedListener(this);
        mStartMonths = createDatePicker.setMonth();

        startDaySpinner.setOnItemSelectedListener(this);
        mStartDays = createDatePicker.setDay();

        startYearSpinner = findViewById(R.id.startYearSpinner);
        startYearSpinner.setOnItemSelectedListener(this);
        mStartYears = createDatePicker.setYear();

        endDaySpinner.setOnItemSelectedListener(this);
        mEndDays = createDatePicker.setDay();

        endMonthSpinner.setOnItemSelectedListener(this);
        mEndMonths = createDatePicker.setMonth();

        endYearSpinner.setOnItemSelectedListener(this);
        mEndYears = createDatePicker.setYear();

        //status spinner
        statusSpinner.setOnItemSelectedListener(this);
        mStatus = statusPopulate.setStatus();

        //Create adapter for spinner
        ArrayAdapter<String> startMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mStartMonths);
        startMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMonthSpinner.setAdapter(startMonthAdapter);

        ArrayAdapter<Integer> startDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStartDays);
        startDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startDaySpinner.setAdapter(startDayAdapter);

        ArrayAdapter<Integer> startYearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, mStartYears);
        startYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startYearSpinner.setAdapter(startYearAdapter);

        ArrayAdapter<Integer> endDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mEndDays);
        endDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endDaySpinner.setAdapter(endDayAdapter);

        ArrayAdapter<String> endMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mEndMonths);
        endMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMonthSpinner.setAdapter(endMonthAdapter);

        ArrayAdapter<Integer> endYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mEndYears);
        endYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endYearSpinner.setAdapter(endYearAdapter);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        //Device Orientation
        if(savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();
    }

    private void initViewModel() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        courseViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), termId)).get(CourseViewModel.class);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void errorDialog(HashMap<String, String> input) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = "";
        for(HashMap.Entry<String, String> entry : input.entrySet()) {
            if(entry.getValue() != "OK") {
                message += entry.getValue() + "\n";
            }
        }
        builder.setMessage(message);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    // Device orientation handling!!
    // as device changes config, save value
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

    public CourseAlertEntity setAlert(int courseId, String message, Date startDate, Date endDate) {
        CourseAlertEntity alert = new CourseAlertEntity(courseId, message, startDate, endDate);
        CourseAlertEntity newAlert;
        long id;
        id = mDb.courseAlertDao().insertAlert(alert);
        int alertId = (int) id;
        newAlert = mDb.courseAlertDao().getAlert(alertId);
        return newAlert;
    }

    @Override
    public Long call() throws Exception {
        return null;
    }
}
