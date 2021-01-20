package com.example.c196assessment.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface CourseAlertDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAlert(CourseAlertEntity alert);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseAlertEntity> alerts);

    @Query("SELECT * FROM courseAlerts")
    List<CourseAlertEntity> getAllAlerts();

    @Query("SELECT * FROM courseAlerts WHERE courseId = :courseId")
    CourseAlertEntity getCourseAlert(int courseId);

    @Query("SELECT * FROM courseAlerts WHERE id = :id")
    CourseAlertEntity getAlert(int id);

    @Delete
    void deleteAlert(CourseAlertEntity alert);

    @Query("UPDATE courseAlerts SET message = :message, startDate = :startDate, endDate = :endDate WHERE courseId = :courseId")
    void updateAlert(int courseId, String message, Date startDate, Date endDate);

}
