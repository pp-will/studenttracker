package com.example.c196assessment.utilities;

import java.util.ArrayList;
import java.util.List;

public class CreateReportType {
    public List<String> setReportType() {
        List<String> reportTypes = new ArrayList<>();
        reportTypes.add(0, "Instructor - name");
        reportTypes.add(1, "Classes - class name");
        reportTypes.add(2, "Classes - term name");
        reportTypes.add(3, "Assessments - assessment name");
        reportTypes.add(4, "Assessments - type");
        return reportTypes;
    }

}
