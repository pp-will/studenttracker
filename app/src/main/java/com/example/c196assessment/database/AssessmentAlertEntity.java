package com.example.c196assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessmentAlerts",
        foreignKeys = @ForeignKey(entity = AssessmentEntity.class,
                parentColumns = "id",
                childColumns = "assessmentId",
                onDelete = CASCADE
        )
)
public class AssessmentAlertEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "assessmentId")
    private int assessmentId;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "date")
    private Date date;

    public AssessmentAlertEntity() {}

    @Ignore
    public AssessmentAlertEntity(int id, int assessmentId, String message, Date date) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.message = message;
        this.date = date;
    }

    public AssessmentAlertEntity(int assessmentId, String message, Date date) {
        this.assessmentId = assessmentId;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AssessmentAlertEntity{" +
                "id=" + id +
                ", assessmentId=" + assessmentId +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
