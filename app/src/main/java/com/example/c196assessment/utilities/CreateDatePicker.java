package com.example.c196assessment.utilities;

import java.util.ArrayList;
import java.util.List;

public class CreateDatePicker implements DatePickerInt {
    @Override
    public List<String> setMonth() {
        List<String> months = new ArrayList<>();
        months.add(0, "January");
        months.add(1, "February");
        months.add(2, "March");
        months.add(3, "April");
        months.add(4, "May");
        months.add(5, "June");
        months.add(6, "July");
        months.add(7, "August");
        months.add(8, "September");
        months.add(9, "October");
        months.add(10, "November");
        months.add(11, "December");
        return months;
    }

    @Override
    public List<Integer> setYear() {
        List<Integer> years = new ArrayList<>();
        years.add(2021);
        years.add(2022);
        years.add(2023);
        years.add(2024);
        years.add(2025);
        return years;
    }

    public List<Integer> setDay() {
        List<Integer> days = new ArrayList<>(31);
        for(int i = 1; i < 32; i++) {
            days.add(i);
        }
        return days;
    }
}
