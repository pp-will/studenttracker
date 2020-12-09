package com.example.c196assessment.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.c196assessment.AddCourseActivity;

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

    /* public void errorDialog(HashMap<String, String> input) {
        AlertDialog.Builder builder = new AlertDialog.Builder();
        String message = "";
        for(HashMap.Entry<String, String> entry : input.entrySet()) {
            if(entry.getValue() != "OK") {
                message += entry.getValue() + "\n";
            }
        }
        builder.setMessage(message);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    } */
}
