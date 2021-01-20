package com.example.c196assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

//@Entity(tableName = "course")
@Entity(tableName = "course",
        foreignKeys = @ForeignKey(entity = TermEntity.class,
        parentColumns = "id",
        childColumns = "termId",
        onDelete = CASCADE
))
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "termId")
    private int termId;
    @ColumnInfo(name = "courseName")
    private String courseName;
    @ColumnInfo(name = "startDate")
    private Date startDate;
    @ColumnInfo(name = "endDate")
    private Date endDate;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "alertSet")
    private int alertSet;

    @Ignore
    public CourseEntity() {
    }

    @Ignore
    public CourseEntity(int id, int termId, String courseName, Date startDate, Date endDate, String status, String name, String email, String phone, int alertSet) {
        this.id = id;
        this.termId = termId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.alertSet = alertSet;
    }

    public CourseEntity(int termId, String courseName, Date startDate, Date endDate, String status, String name, String email, String phone, int alertSet) {
        this.termId = termId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.alertSet = alertSet;
    }

    public int getAlertSet() {
        return alertSet;
    }

    public void setAlertSet(int alertSet) {
        this.alertSet = alertSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int id) {
        this.termId = termId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", termId=" + termId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
