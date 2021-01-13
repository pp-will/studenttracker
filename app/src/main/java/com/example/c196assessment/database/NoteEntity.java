package com.example.c196assessment.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notes",
        foreignKeys = @ForeignKey(entity = CourseEntity.class,
                parentColumns = "id",
                childColumns = "courseId",
                onDelete = CASCADE
        ))
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "noteText")
    private String noteText;
    @ColumnInfo(name = "date")
    private Date date;

    @Ignore
    public NoteEntity() {

    }

    @Ignore
    public NoteEntity(int id, int courseId, String noteText, Date date) {
        this.id = id;
        this.courseId = courseId;
        this.noteText = noteText;
        this.date = date;
    }

    public NoteEntity(int courseId, String noteText, Date date) {
        this.courseId = courseId;
        this.noteText = noteText;
        this.date = date;
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

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", noteText='" + noteText + '\'' +
                ", date=" + date +
                '}';
    }
}


