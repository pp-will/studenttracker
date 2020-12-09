package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.utilities.AlertUtils;
import com.example.c196assessment.utilities.CreateDatePicker;
import com.example.c196assessment.utilities.DateUtils;
import com.example.c196assessment.utilities.StatusPopulate;
import com.example.c196assessment.utilities.Validator;
import com.example.c196assessment.viewmodel.CourseViewModel;
import com.example.c196assessment.viewmodel.MentorViewModel;
import com.example.c196assessment.viewmodel.ViewModelFactory;

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
    private int instructorId;

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

    List<String> mStartMonths = new ArrayList<>(12);
    List<Integer> mStartYears = new ArrayList<>();
    List<String> mEndMonths = new ArrayList<>(12);
    List<Integer> mEndYears = new ArrayList<>();
    List<String> mStatus = new ArrayList<>();

    private boolean mEditing;

    @OnClick(R.id.submitBtn)
    void submitBtnClickHandler() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        int courseId = extras.getInt(COURSE_ID_KEY);

        int startYear = (int) startYearSpinner.getSelectedItem();
        int startMonth = startMonthSpinner.getSelectedItemPosition();
        int endYear = (int) endYearSpinner.getSelectedItem();
        int endMonth = endMonthSpinner.getSelectedItemPosition();
        String course = courseName.getText().toString();
        String status = statusSpinner.getSelectedItem().toString();
        String instructorName = mentorName.getText().toString();
        String instructorEmail = mentorEmail.getText().toString();
        String instructorPhone = mentorPhone.getText().toString();

        Date startDate = dateUtils.createDate(startMonth, startYear);
        Date endDate = dateUtils.createDate(endMonth, endYear);

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
                Log.println(Log.INFO, "TAG", "instructorId = " + instructorId);
                long id = mentorViewModel.updateMentor(instructorId, instructorName, instructorEmail, instructorPhone);
                Log.println(Log.INFO, "TAG", "update mentor id = " + id);
                mentorId = (int) id;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

           /* finally {
                Intent intent = new Intent(this, TermCourses.class);
                intent.putExtra(TERM_ID_KEY, termId);
                startActivity(intent);
            } */
        }

        if(mentorId == 0) {
            errorDialog(validationResult);
        } else {
            try {
                Log.println(Log.INFO, "TAG", "SENDING TERM ID = " + termId);
                courseViewModel.updateCourse(courseId, termId, course, startDate, endDate, status, mentorId);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } finally {
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

                int startMonth = calendarStart.get(Calendar.MONTH);
                int startYear = calendarStart.get(Calendar.YEAR);
                int endMonth = calendarEnd.get(Calendar.MONTH);
                int endYear = calendarEnd.get(Calendar.YEAR);

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
                int startYearPosition = startYearAdapter.getPosition(startYear);
                startYearSpinner.setSelection(startYearPosition);

                endMonthSpinner.setSelection(endMonth);
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

                instructorId = courseEntity.getMentorId();
            }
        }));

        if (extras != null) {
            setTitle("View Class");
            courseViewModel.loadCourseData(courseId);
        }


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

}
