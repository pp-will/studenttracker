package com.example.c196assessment.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAlert(AlertEntity alert);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AlertEntity> alerts);

    @Query("SELECT * FROM alerts")
    List<AlertEntity> getAllAlerts();

    @Query("SELECT * FROM alerts WHERE courseId = :courseId")
    List<AlertEntity> getAllAlertsForCourse(int courseId);

    @Delete
    void deleteAlert(AlertEntity alert);

   // @Query("DELETE FROM alerts WHERE ")
}
