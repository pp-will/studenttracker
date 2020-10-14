package com.example.c196assessment.utilities;

import com.example.c196assessment.database.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleTermData {

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MONTH, diff);
        return cal.getTime();
    }

    public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity("Spring 2021", getDate(1), getDate(6)));
        terms.add(new TermEntity("Winter 2021", getDate(7), getDate(13)));
        return terms;
    }
}
