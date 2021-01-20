package com.example.c196assessment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMentor(MentorEntity mentor);

    @Query("SELECT * FROM mentor WHERE id = :id")
    MentorEntity getMentorById(int id);

    @Query("SELECT * FROM mentor")
    LiveData<List<MentorEntity>> getAllMentors();
}
