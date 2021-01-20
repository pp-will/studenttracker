package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.c196assessment.database.AppDatabase;
import com.example.c196assessment.database.reportEntities.AssessmentReportEntity;
import com.example.c196assessment.database.reportEntities.CourseReportEntity;
import com.example.c196assessment.database.reportEntities.MentorReportEntity;
import com.example.c196assessment.utilities.CreateReportType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    ListView reportList;
    Spinner reportTypeSpinner;
    FloatingActionButton searchFab;
    List<String> searchOptions;
    AppDatabase mDb;
    CreateReportType createReportType;
    EditText searchInput;

    //search lists
    List<MentorReportEntity> mentorSearch;
    List<CourseReportEntity> courseNameSearch;
    List<CourseReportEntity> courseTermSearch;
    List<AssessmentReportEntity> assessmentNameSearch;
    List<AssessmentReportEntity> assessmentTypeSearch;
    List<CourseReportEntity> courseCsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDb = AppDatabase.getInstance(getApplicationContext());
        reportTypeSpinner = findViewById(R.id.reportTypeSpinner);
        reportList = findViewById(R.id.reportList);
        searchFab = findViewById(R.id.searchFab);
        createReportType = new CreateReportType();
        searchOptions = createReportType.setReportType();
        searchInput = findViewById(R.id.queryParam);

        ArrayAdapter<String> searchOptionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, searchOptions);
        searchOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportTypeSpinner.setAdapter(searchOptionAdapter);

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.INFO, "TAG", "Spinner type = " + reportTypeSpinner.getSelectedItemPosition());
                String queryParam = "%" + searchInput.getText().toString() + "%";
                runSearch(reportTypeSpinner.getSelectedItemPosition(), queryParam);
            }
        });

    }

    public void runSearch(int selection, String queryParam) {
        switch(selection) {
            case 0: mentorNameSearch(queryParam);
            break;
            case 1: courseNameSearch(queryParam);
            break;
            case 2: courseTermSearch(queryParam);
            break;
            case 3: assessmentNameSearch(queryParam);
            break;
            case 4: assessmentTypeSearch(queryParam);
            break;
            default: return;
        }
    }

    private void assessmentTypeSearch(String queryParam) {
        mDb = AppDatabase.getInstance(getApplicationContext());
        assessmentTypeSearch = mDb.assessmentDao().getAssessmentTypeReport(queryParam);
        String[] assessmentTypeItems = new String[assessmentTypeSearch.size()];
        if (!assessmentTypeSearch.isEmpty()) {
            for(int i = 0; i < assessmentTypeSearch.size(); i++) {
                assessmentTypeItems[i] = assessmentTypeSearch.get(i).getAssessmentTitle()
                        + " - " + assessmentTypeSearch.get(i).getCourseName()
                        + " - " + assessmentTypeSearch.get(i).getAssessmentType()
                        + " - " + assessmentTypeSearch.get(i).getStatus();
            }
        }
        ArrayAdapter<String> assessmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentTypeItems);
        assessmentTypeAdapter.notifyDataSetChanged();
        reportList.setAdapter(assessmentTypeAdapter);
    }

    private void assessmentNameSearch(String queryParam) {
        mDb = AppDatabase.getInstance(getApplicationContext());
        assessmentNameSearch = mDb.assessmentDao().getAssessmentNameReport(queryParam);
        String[] assessmentNameItems = new String[assessmentNameSearch.size()];
        if (!assessmentNameSearch.isEmpty()) {
            for(int i = 0; i < assessmentNameSearch.size(); i++) {
                assessmentNameItems[i] = assessmentNameSearch.get(i).getAssessmentTitle()
                + " - " + assessmentNameSearch.get(i).getCourseName()
                + " - " + assessmentNameSearch.get(i).getAssessmentType()
                + " - " + assessmentNameSearch.get(i).getStatus();
            }
        }
        ArrayAdapter<String> assessmentNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentNameItems);
        assessmentNameAdapter.notifyDataSetChanged();
        reportList.setAdapter(assessmentNameAdapter);
    }

    private void courseTermSearch(String queryParam) {
        mDb = AppDatabase.getInstance(getApplicationContext());
        courseTermSearch = mDb.courseDao().getCourseTermReport(queryParam);
        String[] courseTermItems = new String[courseTermSearch.size()];
        if (!courseTermSearch.isEmpty()) {
            for(int i = 0; i < courseTermSearch.size(); i++) {
                courseTermItems[i] = courseTermSearch.get(i).getCourseName()
                        + " - " + courseTermSearch.get(i).getTermTitle()
                        + " - " + courseTermSearch.get(i).getStatus();
            }
        }
        ArrayAdapter<String> courseTermAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseTermItems);
        courseTermAdapter.notifyDataSetChanged();
        reportList.setAdapter(courseTermAdapter);
    }

    public void mentorNameSearch(String queryParam) {
        //reportList.
        mDb = AppDatabase.getInstance(getApplicationContext());
        mentorSearch = mDb.courseDao().getMentorReport(queryParam);
        String[] mentorListItems = new String[mentorSearch.size()];
        if (!mentorSearch.isEmpty()) {
            for(int i = 0; i < mentorSearch.size(); i++) {
                mentorListItems[i] = mentorSearch.get(i).getName() + " - " + mentorSearch.get(i).getEmail() + " - " + mentorSearch.get(i).getPhone();
            }
        }
        ArrayAdapter<String> mentorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mentorListItems);
        mentorArrayAdapter.notifyDataSetChanged();
        reportList.setAdapter(mentorArrayAdapter);
    }

    public void courseNameSearch(String queryParam) {
        mDb = AppDatabase.getInstance(getApplicationContext());
        courseNameSearch = mDb.courseDao().getCourseNameReport(queryParam);
        String[] courseListItems = new String[courseNameSearch.size()];
        if (!courseNameSearch.isEmpty()) {
            for(int i = 0; i < courseNameSearch.size(); i++) {
                courseListItems[i] = courseNameSearch.get(i).getCourseName()
                        + " - " + courseNameSearch.get(i).getTermTitle()
                        + " - " + courseNameSearch.get(i).getStatus();
            }
        }
        ArrayAdapter<String> courseNameReportAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseListItems);
        courseNameReportAdapter.notifyDataSetChanged();
        reportList.setAdapter(courseNameReportAdapter);
    }

    public void exportEmailInCSV() throws IOException {
        {
            mDb = AppDatabase.getInstance(getApplicationContext());
            courseCsv = mDb.courseDao().runCourseReport();
            File folder = new File(Environment.getExternalStorageDirectory()
                    + "/Folder");

            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();

            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "courseReport.csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    ReportActivity.this, contentTitle, "Generate Report",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {




                }
            };

            new Thread() {
                public void run() {
                    try {

                        FileWriter fw = new FileWriter(filename);

                        //Cursor cursor = db.selectAll();

                        fw.append("Course Name");
                        fw.append(',');

                        fw.append("Term");
                        fw.append(',');

                        fw.append("Mentor Name");
                        fw.append(',');

                        fw.append("Status");
                        fw.append(',');

                        fw.append("Start Date");
                        fw.append(',');

                        fw.append("End Date");

                        fw.append('\n');

                        if (!courseCsv.isEmpty()) {
                            for(int i = 0; i < courseCsv.size(); i++) {
                                fw.append(courseCsv.get(i).getCourseName() + ",");
                                fw.append(courseCsv.get(i).getTermTitle() + ",");
                                fw.append(courseCsv.get(i).getName() + ",");
                                fw.append(courseCsv.get(i).getStatus() + ",");
                                fw.append(courseCsv.get(i).getStartDate().toString() + ",");
                                fw.append(courseCsv.get(i).getEndDate().toString());
                                fw.append("\n");
                            }
                        }
                        fw.close();
                        Log.println(Log.INFO, "TAG", "csv file created at " + filename);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.println(Log.ERROR, "TAG", "ERROR: " + e.getMessage());
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                }
            }.start();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_generateReport) {
            try {
                Log.println(Log.INFO, "TAG", "start file create ... ");
                exportEmailInCSV();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}