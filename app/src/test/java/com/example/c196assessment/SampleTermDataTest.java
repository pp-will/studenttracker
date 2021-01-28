package com.example.c196assessment;

import com.example.c196assessment.database.TermEntity;
import com.example.c196assessment.utilities.SampleTermData;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SampleTermDataTest {
    @Test
    public void sampleDataSizeCheck() {
        SampleTermData sampleTermData = new SampleTermData();
        List<TermEntity> sampleTerms = new ArrayList<>();
        sampleTerms = sampleTermData.getTerms();
        assertEquals("sampleTerms size should = 2", 2, sampleTerms.size());
    }
}
