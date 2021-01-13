package com.example.c196assessment.utilities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.c196assessment.AssessmentEditActivity;
import com.example.c196assessment.MainActivity;
import com.example.c196assessment.R;

import java.util.Calendar;

public class AlertReminder extends BroadcastReceiver {

    int id;

    @Override
    public void onReceive(Context context, Intent intent) {
    Calendar calDate = Calendar.getInstance();
    Intent receiveIntent = new Intent(context, AssessmentEditActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, receiveIntent, 0);

    NotificationCompat.Builder thisBuilder = new NotificationCompat.Builder(context, "dgwChanId")
            .setSmallIcon(R.drawable.ic_course_icon)
            .setContentTitle("Assessment Due:")
            .setContentText(intent.getStringExtra("message"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("message")))
            .setAutoCancel(true);

    id = intent.getExtras().getInt("id");
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(context);
        notifyManager.notify(id, thisBuilder.build());
    }
}
