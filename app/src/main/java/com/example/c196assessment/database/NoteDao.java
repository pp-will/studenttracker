package com.example.c196assessment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteEntity noteEntity);

    @Query("SELECT * FROM notes WHERE courseId = :courseId")
    LiveData<List<NoteEntity>> getCourseNotes(int courseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> notes);

    @Query("SELECT * FROM notes WHERE courseId = :courseId")
    List<NoteEntity> getCourseNotesList(int courseId);

    @Query("SELECT * FROM notes WHERE id = :noteId")
    NoteEntity getNoteDetails(int noteId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NoteEntity note);
}
