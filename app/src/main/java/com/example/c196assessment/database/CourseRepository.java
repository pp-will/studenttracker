package com.example.c196assessment.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseRepository {
    private static CourseRepository ourInstance;

    public LiveData<List<CourseEntity>> mCourses;
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
    }

    private LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

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
}
