package com.example.c196assessment.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.c196assessment.utilities.SampleAssessmentAlertData;
import com.example.c196assessment.utilities.SampleAssessmentData;
import com.example.c196assessment.utilities.SampleCourseData;
import com.example.c196assessment.utilities.SampleNoteData;
import com.example.c196assessment.utilities.SampleTermData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermRepository {
    private static TermRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();
    long[] ids = new long[2];

    public static TermRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new TermRepository(context);
        }
        return ourInstance;
    }

    private TermRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ids = mDb.termDao().insertAll(SampleTermData.getTerms());
                Log.println(Log.INFO, "TAG", "IDS inserted: " + ids[0] + " :: " + ids[1]);
            }
        });
        executor.execute(() -> mDb.courseDao().insertAll(SampleCourseData.getCourses(ids)));
        executor.execute(() -> mDb.noteDao().insertAll(SampleNoteData.getNotes(ids)));
        executor.execute(() -> mDb.assessmentDao().insertAll(SampleAssessmentData.getAssessments(ids)));
        executor.execute(() -> mDb.assessmentAlertDao().insertAll(SampleAssessmentAlertData.getAssessmentAlerts(ids)));
        executor.execute(() -> mDb.courseAlertDao().insertAll(SampleAssessmentAlertData.getCourseAlerts()));
    }

    private LiveData<List<TermEntity>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    public void deleteAllTerms() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteAll();
            }
        });
    }

    public TermEntity getTermById(int id) {
        return mDb.termDao().getTermById(id);
    }

    public void insertTerm(TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertTerm(term);
            }
        });
    }

    public void deleteTerm(TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteTerm(term);
            }
        });
    }

}
