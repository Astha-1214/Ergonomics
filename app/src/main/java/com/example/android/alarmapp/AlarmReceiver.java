package com.example.android.alarmapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.example.android.alarmapp.R;

import java.net.URI;

/**
 * @author astha on 11/7/20.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        Log.e("====ASSTHA===: ", "here");

        Bundle b=intent.getExtras();
        long end = b.getLong("end");

        int rc = b.getInt("reqCode");
        if(System.currentTimeMillis()>=end)
        {
            Intent intentstart = new Intent(context, AlarmReceiver.class);
            PendingIntent senderstart = PendingIntent.getBroadcast(context, rc, intentstart, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(senderstart);
        }

        Notification noti =  new Notification.Builder(context).setContentTitle("ALARM is ON")
                .setContentText("You had set up the alarm")
                .setSmallIcon(R.mipmap.ic_launcher).build();

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        manager.notify(0,noti);

        Uri notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone r = RingtoneManager.getRingtone(context,notification);
        r.play();
    }
}
