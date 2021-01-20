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
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2021);
        Date date = calendar.getTime();

        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 30);
        calendar.set(Calendar.YEAR, 2021);
        Date term1End = calendar.getTime();

        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 2021);
        Date term2Start = calendar.getTime();

        calendar.clear();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.YEAR, 2021);
        Date term2End = calendar.getTime();

        terms.add(new TermEntity("Spring 2021", date, term1End));
        terms.add(new TermEntity("Winter 2021", term2Start, term2End));
        return terms;
    }
}
