package com.example.c196assessment.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteRepository {

    private static NoteRepository ourInstance;
    public LiveData<List<NoteEntity>> mNotes;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static NoteRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new NoteRepository(context);
        }
        return ourInstance;
    }

    private NoteRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
       // mNotes = getCourseNotes(3);
    }

    public LiveData<List<NoteEntity>> getCourseNotes(int courseId) {
        return mDb.noteDao().getCourseNotes(courseId);
    }
}
