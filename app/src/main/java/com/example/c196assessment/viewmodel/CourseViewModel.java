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
    public LiveData<List<CourseEntity>> mAllCourses;
    CourseEntity course;
    private CourseRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseViewModel(@NonNull Application application, int termId) {
        super(application);
        mRepository = CourseRepository.getInstance(getApplication());
        mCourses = mRepository.mCourses;
        mTermCourses = getCourses(termId);
        //mAllCourses = mRepository.mAllCourses;
        //course = getCourseDetails(termId);
    }

    public CourseViewModel(@NonNull Application application) {
        super(application);
        mRepository = CourseRepository.getInstance(getApplication());
        mCourses = mRepository.mCourses;
        mAllCourses = mRepository.mAllCourses;
    }

    public void saveCourse(int termId, String courseName, Date startDate, Date endDate, String status, String name, String email, String phone) {
        CourseEntity course = new CourseEntity(termId, courseName, startDate, endDate, status, name, email, phone);
        mRepository.insertCourse(course);

    }

    public void updateCourse(int courseId, int termId, String courseName, Date startDate, Date endDate, String status, String name, String email, String phone) {
        CourseEntity course = new CourseEntity(courseId, termId, courseName, startDate, endDate, status, name, email, phone);
        mRepository.updateCourse(course);
    }

    public LiveData<List<CourseEntity>> getCourses(int termId) {
       return mRepository.getCoursesForTerm(termId);
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mRepository.getAllLiveCourses();
    }

    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourse.getValue());
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
