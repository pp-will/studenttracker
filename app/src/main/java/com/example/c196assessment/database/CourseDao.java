package com.example.c196assessment.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196assessment.database.reportEntities.CourseReportEntity;
import com.example.c196assessment.database.reportEntities.MentorReportEntity;
import com.example.c196assessment.utilities.CourseData;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity courseEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CourseEntity> terms);

    @Query("SELECT * FROM course ORDER BY startDate DESC")
    LiveData<List<CourseEntity>> getAll();

    @Query("SELECT * FROM course")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("SELECT c.id, c.termId, c.courseName, c.startDate, c.endDate, c.status, c.name, c.email, c.phone, c.alertSet FROM course AS c WHERE c.termId = :termId")
    LiveData<List<CourseEntity>> getCoursesForTerm(int termId);

    @Query("SELECT c.id, c.termId, c.courseName, c.startDate, c.endDate, c.status, c.name, c.email, c.phone, c.alertSet FROM course AS c WHERE c.id = :courseId")
    CourseEntity getCourseDetails(int courseId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse(CourseEntity course);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void newUpdateCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("SELECT status, count(*) AS count FROM course GROUP BY status")
    List<CourseData> getCourseProgress();

    @Query("DELETE FROM course")
    void wipeDb();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addCourse(CourseEntity course);

    @Query("SELECT * FROM course")
    List<CourseEntity> getListOfCourses();

    // Report Queries
    @Query("SELECT name, email, phone FROM course WHERE name LIKE :name")
    List<MentorReportEntity> getMentorReport(String name);

    @Query("SELECT c.courseName, t.termTitle, c.name, c.status FROM course AS c JOIN terms AS t ON c.termId = t.id WHERE c.courseName LIKE :courseName")
    List<CourseReportEntity> getCourseNameReport(String courseName);

    @Query("SELECT c.courseName, t.termTitle, c.name, c.status FROM course AS c JOIN terms AS t ON c.termId = t.id WHERE t.termTitle LIKE :termName")
    List<CourseReportEntity> getCourseTermReport(String termName);

    @Query("Select c.courseName, t.termTitle, c.name, c.status, c.startDate, c.endDate FROM course AS c JOIN terms AS t ON c.termId = t.id")
    List<CourseReportEntity> runCourseReport();
}
