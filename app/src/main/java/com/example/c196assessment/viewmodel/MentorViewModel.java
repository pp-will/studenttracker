package com.example.c196assessment.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.c196assessment.database.MentorEntity;
import com.example.c196assessment.database.MentorRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MentorViewModel extends AndroidViewModel {

    public LiveData<List<MentorEntity>> mMentors;
    private MentorRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MentorViewModel(@NonNull Application application) {
        super(application);
        mRepository = MentorRepository.getInstance(getApplication());
        mMentors = mRepository.mMentors;
    }

    public long saveMentor(String name, String email, String phone) {
        MentorEntity mentor = new MentorEntity(name, email, phone);
        Log.println(Log.INFO, "TAG", "Mentor from VM::: " + mentor.getId());
        Log.println(Log.INFO, "TAG", "Mentor from VM::: " + mentor.getName());
        long id = mRepository.insertMentor(mentor);
        return id;
    }

    public long updateMentor(int mentorId, String name, String email, String phone) {
        MentorEntity mentor = new MentorEntity(mentorId, name, email, phone);
        Log.println(Log.INFO, "TAG", "Mentor from VM::: " + mentor.getId());
        Log.println(Log.INFO, "TAG", "Mentor from VM::: " + mentor.getName());
        long id = mRepository.insertMentor(mentor);
        return id;
    }
}
