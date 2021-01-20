package com.example.c196assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessments",
        foreignKeys = @ForeignKey(entity = CourseEntity.class,
                parentColumns = "id",
                childColumns = "courseId",
                onDelete = CASCADE
        ))
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "assessmentType")
    private String assessmentType;
    @ColumnInfo(name = "assessmentTitle")
    private String assessmentTitle;
    @ColumnInfo(name = "goalDate")
    private Date goalDate;
    @ColumnInfo(name = "alertSet")
    private int alertSet;
    @ColumnInfo(name = "status")
    private String status;

    @Ignore
    public AssessmentEntity() {}

    @Ignore
    public AssessmentEntity(int id, int courseId, String assessmentType, String assessmentTitle, Date goalDate, int alertSet, String status) {
        this.id = id;
        this.courseId = courseId;
        this.assessmentType = assessmentType;
        this.assessmentTitle = assessmentTitle;
        this.goalDate = goalDate;
        this.alertSet = alertSet;
        this.status = status;
    }

    public AssessmentEntity(int courseId, String assessmentType, String assessmentTitle, Date goalDate, int alertSet, String status) {
        this.courseId = courseId;
        this.assessmentType = assessmentType;
        this.assessmentTitle = assessmentTitle;
        this.goalDate = goalDate;
        this.alertSet = alertSet;
        this.status = status;
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

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }

    public int getAlertSet() {
        return alertSet;
    }

    public void setAlertSet(int alertSet) {
        this.alertSet = alertSet;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", assessmentType='" + assessmentType + '\'' +
                ", goalDate=" + goalDate +
                ", alertSet=" + alertSet +
                '}';
    }
}
