package com.example.c196assessment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.database.CourseRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseViewModel extends AndroidViewModel {

    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<CourseEntity>> mTermCourses;
    public MutableLiveData<CourseEntity> mLiveCourse = new MutableLiveData<>();
    CourseEntity course;
    private CourseRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseViewModel(@NonNull Application application, int termId) {
        super(application);
        mRepository = CourseRepository.getInstance(getApplication());
        //mCourses = mRepository.mCourses;
        mTermCourses = getCourses(termId);
        //course = getCourseDetails(termId);
    }

    public void saveCourse(int termId, String courseName, Date startDate, Date endDate, String status, int mentorId) {
        CourseEntity course = new CourseEntity(termId, courseName, startDate, endDate, status, mentorId);
        mRepository.insertCourse(course);
    }

    public void updateCourse(int courseId, int termId, String courseName, Date startDate, Date endDate, String status, int mentorId) {
        CourseEntity course = new CourseEntity(courseId, termId, courseName, startDate, endDate, status, mentorId);
        mRepository.insertCourse(course);
    }

    public LiveData<List<CourseEntity>> getCourses(int termId) {
       return mRepository.getCoursesForTerm(termId);
    }

    public CourseEntity getCourseDetails(int courseId) {
        return mRepository.getCourseDetails(courseId);
    }

    public void loadCourseData(int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                CourseEntity course = mRepository.getCourseDetails(courseId);
                mLiveCourse.postValue(course);
            }
        });
    }

}
