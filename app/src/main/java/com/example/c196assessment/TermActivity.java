package com.example.c196assessment;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.database.TermEntity;
import com.example.c196assessment.ui.TermsAdapter;
import com.example.c196assessment.utilities.AlertUtils;
import com.example.c196assessment.viewmodel.CourseViewModel;
import com.example.c196assessment.viewmodel.TermViewModel;
import androidx.lifecycle.Observer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class TermActivity extends AppCompatActivity {

    @BindView(R.id.term_recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.createFab)
    void fabClickHandler() {
        Intent intent = new Intent(this, CreateTerm.class);
        startActivity(intent);
    }

    private List<TermEntity> termsData = new ArrayList<>();
    private List<CourseEntity> courseData = new ArrayList<>();
    private TermsAdapter mAdapter;
    private TermViewModel mViewModel;
    public CourseViewModel mCourseViewModel;
    AlertUtils alertUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Terms");
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    public void initViewModel() {

        final Observer<List<TermEntity>> termsObserver = new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(List<TermEntity> termEntities) {
                termsData.clear();
                termsData.addAll(termEntities);

                if(mAdapter == null) {
                    mAdapter = new TermsAdapter(termsData, TermActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        final Observer<List<CourseEntity>> courseObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                courseData.clear();
                courseData.addAll(courseEntities);
            }
        };

        mViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        //mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CourseViewModel.class);
        mViewModel.mTerms.observe(this, termsObserver);
        mCourseViewModel.mAllCourses.observe(this, courseObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new TermsAdapter(termsData, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);

        int id = item.getItemId();

        if(id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            if(courseData.size() > 0) {
                //FIX!!!
                errorText.setText(R.string.terms_error);
            } else {
                deleteAllTerms();
            }
            return true;
        } else if (id == android.R.id.home) {
            returnToMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void returnToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    private void deleteAllTerms() {
        mViewModel.deleteAllTerms();
    }
}