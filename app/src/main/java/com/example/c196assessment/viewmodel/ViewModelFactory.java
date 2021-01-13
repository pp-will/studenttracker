package com.example.c196assessment.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private Object[] mParams;

    public ViewModelFactory(Application application, Object... params) {
        mApplication = application;
        mParams = params;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == CourseViewModel.class) {
            return (T) new CourseViewModel(mApplication, (int) mParams[0]);
        }  else if (modelClass == NoteViewModel.class) {
            return (T) new NoteViewModel(mApplication, (int) mParams[0]);
        } else {
            return super.create(modelClass);
        }
    }
}