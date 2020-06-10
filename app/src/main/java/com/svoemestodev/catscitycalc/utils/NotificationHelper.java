package com.svoemestodev.catscitycalc.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.svoemestodev.catscitycalc.R;

class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String SOUND_OFF = "sound_off";
    public static final String SOUND_ON = "sound_on";

    public NotificationHelper(Context ctx) {
        super(ctx);

        NotificationChannel chan1 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan1 = new NotificationChannel(SOUND_OFF,"chan1", NotificationManager.IMPORTANCE_LOW);
            getManager().createNotificationChannel(chan1);
        }

        NotificationChannel chan2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan2 = new NotificationChannel(SOUND_ON,"chan2", NotificationManager.IMPORTANCE_DEFAULT);
            getManager().createNotificationChannel(chan2);
        }
    }

    public NotificationCompat.Builder getNotification(boolean bSound, String title) {
        NotificationCompat.Builder nb;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (bSound)
                nb = new NotificationCompat.Builder(getApplicationContext(), SOUND_ON);
            else
                nb = new NotificationCompat.Builder(getApplicationContext(), SOUND_OFF);
        }else
            nb = new NotificationCompat.Builder(getApplicationContext());

        nb.setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(false);

        return nb;
    }

    public void notify(boolean bSound, NotificationCompat.Builder notification) {
        Notification nt = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            nt = notification.build();
        }
        if (bSound)
            nt.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        else
            nt.defaults = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getManager().notify(101, nt);
        }
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}