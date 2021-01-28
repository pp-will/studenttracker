package com.example.c196assessment;

import com.example.c196assessment.database.AssessmentEntity;
import com.example.c196assessment.utilities.SampleAssessmentData;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleAssessmentDataTest {
    @Test
    public void sampleDataSizeCheck() {
        SampleAssessmentData sampleAssessmentData = new SampleAssessmentData();
        List<AssessmentEntity> sampleAssessments = new ArrayList<>();
        long[] ids = {1,2};
        sampleAssessments = sampleAssessmentData.getAssessments(ids);
        assertEquals("assessments size should be 3", 3, sampleAssessments.size());
    }
}
