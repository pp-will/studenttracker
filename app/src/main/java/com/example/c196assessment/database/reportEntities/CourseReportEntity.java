package com.example.c196assessment.database.reportEntities;

import java.util.Date;

public class CourseReportEntity {
    private String courseName;
    private String termTitle;
    private String status;
    private String name;
    private Date startDate;
    private Date endDate;

    public CourseReportEntity() {}

    public CourseReportEntity(String courseName, String termTitle, String status) {
        this.courseName = courseName;
        this.termTitle = termTitle;
        this.status = status;
    }

    public CourseReportEntity(String courseName, String termTitle, String name, String status, Date startDate, Date endDate) {
        this.courseName = courseName;
        this.termTitle = termTitle;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
