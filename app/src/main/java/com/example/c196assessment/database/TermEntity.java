package com.example.c196assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "termTitle")
    private String termTitle;
    @ColumnInfo(name = "startDate")
    private Date startDate;
    @ColumnInfo(name = "endDate")
    private Date endDate;

    @Ignore
    public TermEntity() {
    }

    @Ignore
    public TermEntity(int id, String termTitle, Date startDate, Date endDate) {
        this.id = id;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TermEntity(String termTitle, Date startDate, Date endDate) {
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
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
        return "TermEntity{" +
                "id=" + id +
                ", termTitle='" + termTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}