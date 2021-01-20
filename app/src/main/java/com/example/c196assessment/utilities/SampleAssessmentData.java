package com.example.c196assessment.utilities;

import com.example.c196assessment.database.AssessmentEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleAssessmentData {
    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }

    public static List<AssessmentEntity> getAssessments(long[] ids) {
        List<AssessmentEntity> assessments = new ArrayList<>();
        Date date = new Date();
        int[] intArray = Arrays.stream(ids).mapToInt(i -> (int) i).toArray();
        assessments.add(new AssessmentEntity(intArray[0], "Objective", "Java 1 Final", getDate(2), 1, "Pending"));
        assessments.add(new AssessmentEntity(intArray[0], "Performance", "Java 1 Project", date, 1, "Pending"));
        assessments.add(new AssessmentEntity(intArray[1], "Objective", "Practical Demonstration", getDate(4), 0, "Pending"));
        return assessments;
    }
}
