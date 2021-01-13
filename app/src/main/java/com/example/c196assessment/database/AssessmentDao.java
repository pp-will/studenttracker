package com.example.c196assessment.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196assessment.utilities.AssessmentData;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessmentEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long getInsertAssessment(AssessmentEntity assessmentEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);

    @Query("SELECT * FROM assessments WHERE courseId = :courseId")
    List<AssessmentEntity> getAssessmentsForCourse(int courseId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(AssessmentEntity assessmentEntity);

    @Query("SELECT * FROM assessments WHERE id = :assessmentId")
    AssessmentEntity getAssessmentDetails(int assessmentId);

    @Query("SELECT status, count(*) AS count FROM assessments GROUP BY status")
    List<AssessmentData> getAssessmentProgress();

    @Delete
    void deleteAssessment(AssessmentEntity assessmentEntity);

    @Query("SELECT * FROM assessments")
    List<AssessmentEntity> getAssessmentList();
}
