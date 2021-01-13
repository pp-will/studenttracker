package com.example.c196assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.database.TermEntity;
import com.example.c196assessment.ui.CourseAdapter;
import com.example.c196assessment.viewmodel.CourseViewModel;
import com.example.c196assessment.viewmodel.TermViewModel;
import com.example.c196assessment.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import androidx.lifecycle.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.c196assessment.utilities.Constants.TERM_ID_KEY;

public class TermCourses extends AppCompatActivity {

   private List<CourseEntity> courseData = new ArrayList<>();
   private List<CourseEntity> courseData2 = new ArrayList<>();

    private CourseAdapter mAdapter;
    private CourseViewModel mViewModel;
    private TermViewModel mTermViewModel;

    @BindView(R.id.course_recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.addCourseBtn)
    void addCourseBtnHandler() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        Log.println(Log.INFO, "TAG", "termId from TERMS ACT: " + termId);
        startActivity(intent);
    }

    @OnClick(R.id.deleteTermBtn)
    void deleteTermBtnHandler() {
        if(courseData.size() != 0) {
            Toast.makeText(getApplicationContext(),"You cannot delete a term when courses are assigned", Toast.LENGTH_LONG).show();
        } else {
            mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
            Intent intent = new Intent(this, TermActivity.class);
            mTermViewModel.deleteTerm();
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Term Courses");
        
        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    public int getTermId(int termId) {
        return termId;
    }

    private void initViewModel() {
        //TODO
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        Log.println(Log.INFO, "TERM ID", "Term id = " + termId);

        CourseViewModel cvm = ViewModelProviders.of(this, new ViewModelFactory(getApplication(), termId)).get(CourseViewModel.class);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mTermViewModel.loadData(termId);


        final Observer<List<CourseEntity>> courseObserver = new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                courseData.clear();
                //courseData = mViewModel.getCourses(termId);
                courseData.addAll(courseEntities);

                if(mAdapter == null) {
                    mAdapter = new CourseAdapter(courseData, TermCourses.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

       /* mTermViewModel.mLiveTerm.observe(this, termEntity -> {

        }); */

        /* mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mViewModel.mCourses.observe(this, courseObserver); */
        cvm.mTermCourses.observe(this,courseObserver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView errorText = findViewById(R.id.errorText);

        int id = item.getItemId();
         if (id == android.R.id.home) {
            returnToMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void returnToMenu() {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
        finish();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new CourseAdapter(courseData, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }
}