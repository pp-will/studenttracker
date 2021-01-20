package com.example.c196assessment.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseRepository {
    private static CourseRepository ourInstance;

    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<CourseEntity>> mAllCourses;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();


    public static CourseRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new CourseRepository(context);
        }
        return ourInstance;
    }

    private CourseRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mCourses = getAllCourses();
        mAllCourses = getAllLiveCourses();
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    public LiveData<List<CourseEntity>> getAllLiveCourses() { return mDb.courseDao().getAllCourses();}

    public void insertCourse(CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().insertCourse(course);
            }
        });
    }

    public LiveData<List<CourseEntity>> getCoursesForTerm(int termId) {
        return mDb.courseDao().getCoursesForTerm(termId);
    }

    public CourseEntity getCourseDetails(int courseId) {
        return mDb.courseDao().getCourseDetails(courseId);
    }

    public void updateCourse(CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().updateCourse(course);
            }
        });
    }

    public void deleteCourse(CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().deleteCourse(course);
            }
        });
    }
}
