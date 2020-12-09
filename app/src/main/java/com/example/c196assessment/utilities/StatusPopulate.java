package com.example.c196assessment.utilities;

import com.example.c196assessment.R;

import java.util.ArrayList;
import java.util.List;

public class StatusPopulate {

    // course status:
    // in progress, completed, dropped, plan to take
    public List<String> setStatus() {
        List<String> status = new ArrayList<>();
        status.add(0, "In Progress");
        status.add(1, "Completed");
        status.add(2, "Dropped");
        status.add(3, "Plan to Take");
        return status;
    }
}
