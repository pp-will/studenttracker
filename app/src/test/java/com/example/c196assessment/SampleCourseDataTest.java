package com.example.c196assessment;

import com.example.c196assessment.database.CourseEntity;
import com.example.c196assessment.utilities.SampleCourseData;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleCourseDataTest {
    @Test
    public void sampleDataSizeCheck() {
        SampleCourseData sampleCourseData = new SampleCourseData();
        long[] ids = {1,2};
        List<CourseEntity> courses = sampleCourseData.getCourses(ids);
        assertEquals("courses size should = 3", 3, courses.size());
    }
}
