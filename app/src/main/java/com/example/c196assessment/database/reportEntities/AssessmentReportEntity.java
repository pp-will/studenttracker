package com.example.c196assessment.database.reportEntities;

public class AssessmentReportEntity {
    private String assessmentTitle;
    private String courseName;
    private String assessmentType;
    private String status;

    public AssessmentReportEntity() {}

    public AssessmentReportEntity(String assessmentTitle, String courseName, String assessmentType, String status) {
        this.assessmentTitle = assessmentTitle;
        this.courseName = courseName;
        this.assessmentType = assessmentType;
        this.status = status;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
