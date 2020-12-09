package com.example.c196assessment.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMentor(MentorEntity mentor);

    @Query("SELECT * FROM mentor WHERE id = :id")
    MentorEntity getMentorById(int id);
}
