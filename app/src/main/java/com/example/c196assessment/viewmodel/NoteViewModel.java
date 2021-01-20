package com.example.c196assessment.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196assessment.database.NoteEntity;
import com.example.c196assessment.database.NoteRepository;
import com.example.c196assessment.database.TermEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    public LiveData<List<NoteEntity>> mCourseNotes;
    private NoteRepository mRepository;
    public MutableLiveData<NoteEntity> mLiveNote;

    Executor executor = Executors.newSingleThreadExecutor();

    public NoteViewModel(@NonNull Application application, int courseId) {
        super(application);

        mRepository = NoteRepository.getInstance(getApplication());
        mCourseNotes = getCourseNotes(courseId);
    }

    public NoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = NoteRepository.getInstance(application.getApplicationContext());
        //mNotes = mRepository.mNotes;
    }

    public LiveData<List<NoteEntity>> getCourseNotes(int courseId) {
        Log.println(Log.INFO, "TAG", "COURSE ID FROM VM :: " + courseId);
        return mRepository.getCourseNotes(courseId);
    }

    /*public void loadData(int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<List<NoteEntity>> notes = mRepository.getCourseNotes(courseId);
                mLiveNote.postValue(note);
            }
        });
    }*/
}
