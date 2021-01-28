package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196assessment.utilities.CreateDatePicker;
import com.example.c196assessment.utilities.DateUtils;
import com.example.c196assessment.viewmodel.TermViewModel;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.c196assessment.utilities.Constants.EDITING_KEY;

public class CreateTerm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CreateDatePicker createDatePicker = new CreateDatePicker();
    DateUtils dateUtils = new DateUtils();
    private TermViewModel mTermViewModel;

    @BindView(R.id.termName)
    EditText termName;

    @BindView(R.id.startMonthSpinner)
    Spinner startMonthSpinner;

    @BindView(R.id.startYearSpinner)
    Spinner startYearSpinner;

    @BindView(R.id.endYearSpinner)
    Spinner endYearSpinner;

    @BindView(R.id.endMonthSpinner)
    Spinner endMonthSpinner;

    @OnClick(R.id.termFab)
    void fabClickHandler() {
        int startYear = (int) startYearSpinner.getSelectedItem();
        int startMonth = startMonthSpinner.getSelectedItemPosition();
        int endYear = (int) endYearSpinner.getSelectedItem();
        int endMonth = endMonthSpinner.getSelectedItemPosition();
        String term = termName.getText().toString();
       /* Log.println(Log.INFO, "TAG", "start year is " + startYear);
        Log.println(Log.INFO, "TAG", "start month is " + startMonth);
        Log.println(Log.INFO, "TAG", " end Year is " + endYear);
        Log.println(Log.INFO, "TAG", "end month is " + endMonth);
        Log.println(Log.INFO, "TAG", "term is " + term);
        //Log.println(Log.INFO, "TAG", "TEST POS is " + intTest);*/

        Date startDate = dateUtils.createDate(startMonth, 1, startYear);
        Date endDate = dateUtils.createDate(endMonth, 30, endYear);

        try {
            mTermViewModel.saveTerm(term, startDate, endDate);
        } catch (NullPointerException e) {
            e.printStackTrace();;
        }

        finally {
            Intent intent = new Intent(this, TermActivity.class);
            startActivity(intent);
        }
    }

    List<String> mStartMonths = new ArrayList<>(12);
    List<Integer> mStartYears = new ArrayList<>();
    List<String> mEndMonths = new ArrayList<>(12);
    List<Integer> mEndYears = new ArrayList<>();

    private boolean mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);
        ButterKnife.bind(this);
        setTitle(getString(R.string.create_term));
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

        //Device Orientation
        if(savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_KEY);
        }

        initViewModel();

    }

    private void initViewModel() {
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        String month = parent.getItemAtPosition(position).toString();

    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    // Device orientation handling!!
    // as device changes config, save value
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EDITING_KEY, true);
        super.onSaveInstanceState(outState);
    }

}