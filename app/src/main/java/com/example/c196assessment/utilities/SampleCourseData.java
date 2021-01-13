package com.example.c196assessment.utilities;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.database.TermEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleCourseData {

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }

    public static List<CourseEntity> getCourses(long[] ids) {
        List<CourseEntity> courses = new ArrayList<>();
        Date date = new Date();
        int[] intArray = Arrays.stream(ids).mapToInt(i -> (int) i).toArray();
        courses.add(new CourseEntity(intArray[0], "Java 1", date, getDate(6), "In Progress", "Mike Teacher", "mike@wgu.com", "4025555555"));
        courses.add(new CourseEntity(intArray[0], "Data Management", getDate(3), getDate(6), "Plan to Take", "Jason Teacher", "jason@wgu.com", "4021111111"));
        courses.add(new CourseEntity(intArray[1], "Java 2", getDate(7), getDate(11), "Dropped", "Shelly Teacher", "shelly@wgu.com", "4022222222"));
        return courses;
    }
}
