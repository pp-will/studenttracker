package com.example.c196assessment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {
        TermEntity.class,
        CourseEntity.class,
        MentorEntity.class,
        NoteEntity.class,
        AssessmentEntity.class,
        AlertEntity.class,
        AssessmentAlertEntity.class,
        CourseAlertEntity.class
}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase1.db";
    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract MentorDao mentorDao();
    public abstract NoteDao noteDao();
    public abstract AssessmentDao assessmentDao();
    public abstract AlertDao alertDao();
    public abstract AssessmentAlertDao assessmentAlertDao();
    public abstract CourseAlertDao courseAlertDao();

    public static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
