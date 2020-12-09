package com.example.c196assessment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity courseEntity);

    @Query("SELECT * FROM course ORDER BY startDate DESC")
    LiveData<List<CourseEntity>> getAll();

    @Query("SELECT c.id, c.termId, c.courseName, c.startDate, c.endDate, c.status, c.mentorId, m.name, m.email, m.phone FROM course AS c JOIN mentor AS m ON c.mentorId = m.id WHERE c.termId = :termId")
    LiveData<List<CourseEntity>> getCoursesForTerm(int termId);

    @Query("SELECT c.id, c.termId, c.courseName, c.startDate, c.endDate, c.status, c.mentorId, m.name, m.email, m.phone FROM course AS c JOIN mentor AS m ON c.mentorId = m.id WHERE c.id = :courseId")
    CourseEntity getCourseDetails(int courseId);
}
