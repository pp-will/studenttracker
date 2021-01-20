package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.CourseAlertEntity;
import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.utilities.AlertUtils;
import com.example.c196assessment.utilities.CreateDatePicker;
import com.example.c196assessment.utilities.DateUtils;
import com.example.c196assessment.utilities.StatusPopulate;
import com.example.c196assessment.utilities.Validator;
import com.example.c196assessment.viewmodel.CourseViewModel;
import com.example.c196assessment.viewmodel.MentorViewModel;
import com.example.c196assessment.viewmodel.ViewModelFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.c196assessment.utilities.Constants.COURSE_ID_KEY;
import static com.example.c196assessment.utilities.Constants.EDITING_KEY;
import static com.example.c196assessment.utilities.Constants.TERM_ID_KEY;

public class EditCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CreateDatePicker createDatePicker = new CreateDatePicker();
    StatusPopulate statusPopulate = new StatusPopulate();
    DateUtils dateUtils = new DateUtils();
    Validator validator = new Validator();
    AlertUtils alertUtils = new AlertUtils();
    private MentorViewModel mentorViewModel;
    private CourseViewModel courseViewModel;
    CourseEntity course;
    //private int instructorId;
    AppDatabase mDb;

    @BindView(R.id.startMonthSpinner)
    Spinner startMonthSpinner;

    @BindView(R.id.startYearSpinner)
    Spinner startYearSpinner;

    @BindView(R.id.endYearSpinner)
    Spinner endYearSpinner;

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

    @BindView(R.id.notificationEditSwitch)
    Switch notificationEditSwitch;

    @BindView(R.id.startDaySpinner)
    Spinner startDaySpinner;

    @BindView(R.id.endDaySpinner)
    Spinner endDaySpinner;

    List<String> mStartMonths = new ArrayList<>(12);
    List<Integer> mStartYears = new ArrayList<>();
    List<String> mEndMonths = new ArrayList<>(12);
    List<Integer> mEndYears = new ArrayList<>();
    List<String> mStatus = new ArrayList<>();
    List<Integer> mStartDays = new ArrayList<>();
    List<Integer> mEndDays = new ArrayList<>();

    //Bundle extras = getIntent().getExtras();

    private boolean mEditing;

    @OnClick(R.id.submitBtn)
    void submitBtnClickHandler() {
        Bundle extras = getIntent().getExtras();
        mDb = AppDatabase.getInstance(getApplicationContext());
        int termId = extras.getInt(TERM_ID_KEY);
        int courseId = extras.getInt(COURSE_ID_KEY);

        int startYear = (int) startYearSpinner.getSelectedItem();
        int startMonth = startMonthSpinner.getSelectedItemPosition();
        int endYear = (int) endYearSpinner.getSelectedItem();
        int endMonth = endMonthSpinner.getSelectedItemPosition();
        int startDay = (int) startDaySpinner.getSelectedItem();
        int endDay = (int) endDaySpinner.getSelectedItem();
        int alertSet = 0;

        if (notificationEditSwitch.isChecked()) {
            alertSet = 1;
        }

        String course = courseName.getText().toString();
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
        int mentorId = 0;

        for(HashMap.Entry<String, String> entry : validationResult.entrySet()) {
            if(entry.getValue() != "OK") {
                valid = false;
                Log.println(Log.INFO, "TAG", entry.getKey() + ": " + entry.getValue());
                break;
            }
        }

        if(!valid) {
            errorDialog(validationResult);
        } else {
            try {
                Log.println(Log.INFO, "TAG", "update termId = " + termId);
                Log.println(Log.INFO, "TAG", "update courseId = " + courseId);
                //courseViewModel.updateCourse(courseId, termId, course, startDate, endDate, status, instructorName, instructorEmail, instructorPhone);
                //update course, return id
                CourseEntity courseEntity = new CourseEntity(courseId, termId, course, startDate, endDate, status, instructorName, instructorEmail, instructorPhone, alertSet);
                mDb.courseDao().newUpdateCourse(courseEntity);
                //int newCourseId = (int) returnedCourseId;
                CourseEntity newCourseEntity = mDb.courseDao().getCourseDetails(courseId);
                String message = newCourseEntity.getCourseName();
                startDate = newCourseEntity.getStartDate();
                endDate = newCourseEntity.getEndDate();
                if (newCourseEntity.getAlertSet() == 1) {
                    mDb.courseAlertDao().updateAlert(courseId, message, startDate, endDate);
                }
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
        setContentView(R.layout.activity_edit_course);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);
        Log.println(Log.INFO, "TAG", "course id = " + courseId);

        //Month spinner
        startMonthSpinner = findViewById(R.id.startMonthSpinner);
        startMonthSpinner.setOnItemSelectedListener(this);
        mStartMonths = createDatePicker.setMonth();

        startYearSpinner = findViewById(R.id.startYearSpinner);
        startYearSpinner.setOnItemSelectedListener(this);
        mStartYears = createDatePicker.setYear();

        startDaySpinner.setOnItemSelectedListener(this);
        mStartDays = createDatePicker.setDay();

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

        ArrayAdapter<Integer> startYearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, mStartYears);
        startYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startYearSpinner.setAdapter(startYearAdapter);

        ArrayAdapter<Integer> startDayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStartDays);
        startDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startDaySpinner.setAdapter(startDayAdapter);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initViewModel() {
        Bundle extras = getIntent().getExtras();
        int courseId = extras.getInt(COURSE_ID_KEY);
        mentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        courseViewModel = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), courseId)).get(CourseViewModel.class);

        courseViewModel.mLiveCourse.observe(this, (courseEntity -> {
            if (courseEntity != null && !mEditing) {
                courseName.setText(courseEntity.getCourseName());
                //date
                Date startDate1 = courseEntity.getStartDate();
                Date endDate1 = courseEntity.getEndDate();
                Log.println(Log.INFO, "TAG", startDate1 + " - " + endDate1);
                Calendar calendarStart = Calendar.getInstance();
                Calendar calendarEnd = Calendar.getInstance();
                calendarStart.setTime(startDate1);
                calendarEnd.setTime(endDate1);

                if (courseEntity.getAlertSet() == 1) {
                    notificationEditSwitch.setChecked(true);
                }

                Log.println(Log.INFO, "TAG", "update INIT termId = " + courseEntity.getTermId());
                extras.putInt(TERM_ID_KEY, courseEntity.getTermId());

                int startMonth = calendarStart.get(Calendar.MONTH);
                int startDay = calendarStart.get(Calendar.DAY_OF_MONTH);
                int startYear = calendarStart.get(Calendar.YEAR);
                int endMonth = calendarEnd.get(Calendar.MONTH);
                int endYear = calendarEnd.get(Calendar.YEAR);
                int endDay = calendarEnd.get(Calendar.DAY_OF_MONTH);
                Log.println(Log.INFO, "TAG", "END DAY FROM EDIT ::: " + endDay);

                ArrayAdapter<String> startMonthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mStartMonths);
                startMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                startMonthSpinner.setAdapter(startMonthAdapter);

                ArrayAdapter<Integer> startYearAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, mStartYears);
                startYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                startYearSpinner.setAdapter(startYearAdapter);

                ArrayAdapter<String> endMonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mEndMonths);
                endMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                endMonthSpinner.setAdapter(endMonthAdapter);

                ArrayAdapter<Integer> endYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mEndYears);
                endYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                endYearSpinner.setAdapter(endYearAdapter);

                startMonthSpinner.setSelection(startMonth);
                startDaySpinner.setSelection(startDay - 1);
                int startYearPosition = startYearAdapter.getPosition(startYear);
                startYearSpinner.setSelection(startYearPosition);

                endMonthSpinner.setSelection(endMonth);
                endDaySpinner.setSelection(endDay - 1);
                int endYearPosition = endYearAdapter.getPosition(endYear);
                endYearSpinner.setSelection(endYearPosition);

                //status
                String status = courseEntity.getStatus();
                ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStatus);
                statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statusSpinner.setAdapter(statusAdapter);
                if(status != null) {
                    int position = statusAdapter.getPosition(status);
                    statusSpinner.setSelection(position);
                }

                //mentor
                mentorName.setText(courseEntity.getName());
                mentorEmail.setText(courseEntity.getEmail());
                mentorPhone.setText(courseEntity.getPhone());

               // instructorId = courseEntity.getMentorId();
            }
        }));

        if (extras != null) {
            setTitle("View Class");
            courseViewModel.loadCourseData(courseId);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);

        int id = item.getItemId();

        if(id == R.id.action_notes) {
            int courseId = courseViewModel.mLiveCourse.getValue().getId();
            Log.println(Log.INFO, "TAG", "course id from EDITCOURSE= " + courseId);
            Intent intent = new Intent(this, NotesActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        } else if (id == R.id.action_assessments) {
            int courseId = courseViewModel.mLiveCourse.getValue().getId();
            Intent intent = new Intent(this, AssessmentActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);

        } else if (id == R.id.action_delete) {

            try {
                courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
                int termId = courseViewModel.mLiveCourse.getValue().getTermId();
                Intent intent = new Intent(this, TermCourses.class);
                intent.putExtra(TERM_ID_KEY, termId);
                deleteCourse();
                startActivity(intent);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            finally {
                Toast.makeText(getApplicationContext(),"Successfully deleted!",Toast.LENGTH_SHORT).show();
            }

            return true;
        } else if (id == android.R.id.home) {
            int termId = courseViewModel.mLiveCourse.getValue().getTermId();
            Intent intent = new Intent(this, TermCourses.class);
            intent.putExtra(TERM_ID_KEY, termId);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCourse() {

        courseViewModel.deleteCourse();
    }

    private void addSampleData() {
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

    public void setAlert(int courseId, String message, Date startDate, Date endDate) {
        CourseAlertEntity newAlert;
        long id;
        mDb.courseAlertDao().updateAlert(courseId, message, startDate, endDate);
    }

}
