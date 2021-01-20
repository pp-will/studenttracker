package com.example.c196assessment.database.reportEntities;

import androidx.room.Entity;

public class MentorReportEntity {
    private String name;
    private String email;
    private String phone;

    public MentorReportEntity() {}

    public MentorReportEntity(String name, String email, String phone) {
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
}
