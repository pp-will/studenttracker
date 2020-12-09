package com.example.c196assessment.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

//@Entity(tableName = "course")
@Entity(tableName = "course",
        foreignKeys = @ForeignKey(entity = MentorEntity.class,
        parentColumns = "id",
        childColumns = "mentorId",
        onDelete = ForeignKey.NO_ACTION
))
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int termId;
    private String courseName;
    private Date startDate;
    private Date endDate;
    private String status;
    private int mentorId;
    private String name;
    private String email;
    private String phone;

    @Ignore
    public CourseEntity() {
    }

    @Ignore
    public CourseEntity(int id, int termId, String courseName, Date startDate, Date endDate, String status, int mentorId) {
        this.id = id;
        this.termId = termId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorId = mentorId;
    }

    public CourseEntity(int termId, String courseName, Date startDate, Date endDate, String status, int mentorId) {
        this.termId = termId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorId = mentorId;
    }

    @Ignore
    public CourseEntity(int termId, String courseName, Date startDate, Date endDate, String status, int mentorId, String name, String email, String phone) {
        this.termId = termId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorId = mentorId;
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
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
                ", mentorId=" + mentorId +
                '}';
    }
}
