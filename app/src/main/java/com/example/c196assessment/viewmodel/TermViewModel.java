package com.example.c196assessment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196assessment.database.TermEntity;
import com.example.c196assessment.database.TermRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermViewModel extends AndroidViewModel {

    public LiveData<List<TermEntity>> mTerms;
    private TermRepository mRepository;

    public TermViewModel(@NonNull Application application) {
        super(application);
        mRepository = TermRepository.getInstance(getApplication());
        mTerms = mRepository.mTerms;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllTerms() {
        mRepository.deleteAllTerms();
    }
}
