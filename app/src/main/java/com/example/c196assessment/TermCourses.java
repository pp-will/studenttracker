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

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.ui.CourseAdapter;
import com.example.c196assessment.viewmodel.CourseViewModel;
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

    @BindView(R.id.course_recycler_view)
    RecyclerView mRecyclerView;

    @OnClick(R.id.addCourseBtn)
    void addCourseBtnHandler() {
        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        Intent intent = new Intent(this, AddCourseActivity.class);
        intent.putExtra(TERM_ID_KEY, termId);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);
        setTitle(getString(R.string.term_courses_heading));
        
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

        /* mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mViewModel.mCourses.observe(this, courseObserver); */
        cvm.mTermCourses.observe(this,courseObserver);
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