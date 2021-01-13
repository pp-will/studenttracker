package com.example.c196assessment.utilities;

import com.example.c196assessment.database.AssessmentAlertEntity;
import com.example.c196assessment.database.CourseAlertEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleAssessmentAlertData {

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }

    public static List<AssessmentAlertEntity> getAssessmentAlerts(long[] ids) {
        Date date = new Date();
        List<AssessmentAlertEntity> alerts = new ArrayList<>();
        int[] intArray = Arrays.stream(ids).mapToInt(i -> (int) i).toArray();
        alerts.add(new AssessmentAlertEntity(intArray[0], "Sample Alert 1", date));
        alerts.add(new AssessmentAlertEntity(intArray[1], "Sample Alert 2", getDate(2)));
        return alerts;
    }

    public static List<CourseAlertEntity> getCourseAlerts() {
        Date date = new Date();
        List<CourseAlertEntity> alerts = new ArrayList<>();
        alerts.add(new CourseAlertEntity(1, "Java 1", date, getDate(4)));
        alerts.add(new CourseAlertEntity(2, "Data Management", getDate(1), getDate(3)));
        alerts.add(new CourseAlertEntity(3, "Java 2", getDate(6), getDate(8)));
        return alerts;
    }
}
