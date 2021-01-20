package com.example.c196assessment.utilities;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.database.NoteEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleNoteData {

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }

    public static List<NoteEntity> getNotes(long[] ids) {
        List<NoteEntity> notes = new ArrayList<>();
        int[] intArray = Arrays.stream(ids).mapToInt(i -> (int) i).toArray();
        notes.add(new NoteEntity(intArray[0], "Sample Note 1", getDate(1)));
        notes.add(new NoteEntity(intArray[0], "Sample Note 2", getDate(2)));
        notes.add(new NoteEntity(intArray[1], "Sample Note 3", getDate(3)));
        return notes;
    }
}
