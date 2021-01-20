package com.example.c196assessment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196assessment.database.TermEntity;
import com.example.c196assessment.database.TermRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermViewModel extends AndroidViewModel {

    public LiveData<List<TermEntity>> mTerms;
    public MutableLiveData<TermEntity> mLiveTerm = new MutableLiveData<>();
    private TermRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermViewModel(@NonNull Application application) {
        super(application);
        mRepository = TermRepository.getInstance(getApplication());
        mTerms = mRepository.mTerms;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void saveTerm(String name, Date startDate, Date endDate) {
        TermEntity term = new TermEntity(name, startDate, endDate);
        mRepository.insertTerm(term);
    }

    public void loadData(int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = mRepository.getTermById(termId);
                mLiveTerm.postValue(term);
            }
        });
    }


    public void deleteAllTerms() {
        mRepository.deleteAllTerms();
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerm.getValue());
    }
}
