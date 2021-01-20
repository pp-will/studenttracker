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

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2021);
        Date startTwo = calendar.getTime();

        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2021);
        Date startThree = calendar.getTime();

        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        calendar.set(Calendar.YEAR, 2021);
        Date endThree = calendar.getTime();

        int[] intArray = Arrays.stream(ids).mapToInt(i -> (int) i).toArray();
        courses.add(new CourseEntity(intArray[0], "Java 1", date, getDate(3), "In Progress", "Mike Teacher", "mike@wgu.com", "4025555555", 1));
        courses.add(new CourseEntity(intArray[0], "Data Management", startTwo, date, "In Progress", "Jason Teacher", "jason@wgu.com", "4021111111", 1));
        courses.add(new CourseEntity(intArray[1], "Java 2", startThree, endThree, "Dropped", "Shelly Teacher", "shelly@wgu.com", "4022222222", 0));
        return courses;
    }
}
