package com.example.c196assessment.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    Calendar calendar = Calendar.getInstance();

    public String formattedDate(Date date) {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String output = outputFormatter.format(date);
        return output;
    }

    public Date createDate(int month, int year) {
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        Date date = calendar.getTime();
        return date;
    }
}
