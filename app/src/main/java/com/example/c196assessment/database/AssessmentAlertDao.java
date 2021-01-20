package com.example.c196assessment.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentAlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAlert(AssessmentAlertEntity alert);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentAlertEntity> alerts);

    @Query("SELECT * FROM assessmentAlerts")
    List<AssessmentAlertEntity> getAllAlerts();

    @Query("SELECT * FROM assessmentAlerts WHERE assessmentId = :assessmentId")
    List<AssessmentAlertEntity> getAllAssessmentsAlerts(int assessmentId);

    @Query("SELECT * FROM assessmentAlerts WHERE assessmentId = :assessmentId")
    AssessmentAlertEntity getAssessmentAlert(int assessmentId);

    @Query("SELECT * FROM assessmentAlerts WHERE id = :id")
    AssessmentAlertEntity getAlert(int id);

    @Delete
    void deleteAlert(AssessmentAlertEntity alert);

    // @Query("DELETE FROM alerts WHERE ")
}
