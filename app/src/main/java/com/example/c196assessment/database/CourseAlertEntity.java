package com.example.c196assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courseAlerts",
        foreignKeys = @ForeignKey(entity = CourseEntity.class,
            parentColumns = "id",
            childColumns = "courseId",
            onDelete = CASCADE
    )
)
public class CourseAlertEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "startDate")
    private Date startDate;
    @ColumnInfo(name = "endDate")
    private Date endDate;

    public CourseAlertEntity() {}

    @Ignore
    public CourseAlertEntity(int id, int courseId, String message, Date startDate, Date endDate) {
        this.id = id;
        this.courseId = courseId;
        this.message = message;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CourseAlertEntity(int courseId, String message, Date startDate, Date endDate) {
        this.courseId = courseId;
        this.message = message;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CourseAlertEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", message='" + message + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
