package com.example.c196assessment.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.c196assessment.AddCourseActivity;
import com.example.c196assessment.database.AlertEntity;
import com.example.c196assessment.database.AppDatabase;

import java.util.Date;
import java.util.HashMap;

import static android.app.PendingIntent.getActivity;

public class AlertUtils {

    Context context;

    public void deleteTermAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure you want to delete all terms?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void setAlert(int courseId, String message, Date startDate, Date endDate) {
        AlertEntity alert = new AlertEntity();
        alert.setCourseId(courseId);
        alert.setMessage(message);
        alert.setStartDate(startDate);
        alert.setEndDate(endDate);

    }
}
