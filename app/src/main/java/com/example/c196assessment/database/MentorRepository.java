package com.example.c196assessment.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MentorRepository {
    public static MentorRepository ourInstance;

    public LiveData<List<MentorEntity>> mMentors;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();
    public long id;

    public static MentorRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new MentorRepository(context);
        }
        return ourInstance;
    }

    private MentorRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mMentors = getAllMentors();
        // mMentors = getAllTerms();
    }

    private LiveData<List<MentorEntity>> getAllMentors() {
        return mDb.mentorDao().getAllMentors();
    }

    public long insertMentor(MentorEntity mentor) {
        Log.println(Log.INFO, "TAG", "FROM REPO: " + mentor.getId());
        executor.execute(new Runnable() {
            @Override
            public void run() {
               id = mDb.mentorDao().insertMentor(mentor);
            }
        });
        Log.println(Log.INFO, "TAG", "FROM REPO RETURN :: " + id);
        return id;
    }
}
