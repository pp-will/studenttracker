package com.example.c196assessment.utilities;

import com.example.c196assessment.database.CourseEntity;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleCourseDataTest extends TestCase {

    @Test
    public void testGetCourses() {
        SampleCourseData sampleCourseData = new SampleCourseData();
        long[] ids = {1,2};
        List<CourseEntity> courses = sampleCourseData.getCourses(ids);
        assertEquals("courses size should = 3", 3, courses.size());
    }
}