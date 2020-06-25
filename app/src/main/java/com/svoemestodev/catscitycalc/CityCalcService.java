package com.svoemestodev.catscitycalc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.svoemestodev.catscitycalc.activities.GameActivity;
import com.svoemestodev.catscitycalc.classes.Car;

public class CityCalcService extends Service {

    private final IBinder mBinder = new LocalBinder();
    public static boolean isWorking;
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;                     // айди нотификатора
    private static final String CHANNEL_ID = "C.A.T.S. City Calculator Channel #1";   // канал нотификатора

    public void createNotification(String message, int notifyID, Bitmap largeIcon) {

        Intent intent = new Intent(this, GameActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_catscalciconsmall)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        if (largeIcon != null) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(largeIcon));
        } else {
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        }
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID + notifyID, notificationBuilder.build());

    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        return mBinder;
    }

    public class LocalBinder extends Binder {
        CityCalcService getService() {
            return CityCalcService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String message = intent.getStringExtra("message");
        int notifyID = intent.getIntExtra("car_number", 0);

        Bitmap largeIcon = null;
        largeIcon = Car.loadCar(notifyID).getCarPicture();

//        Toast.makeText(GlobalApplication.getAppContext(),message, Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(GlobalApplication.getAppContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView carImageView = new ImageView(GlobalApplication.getAppContext());
        carImageView.setImageBitmap(largeIcon);
        toastContainer.addView(carImageView, 0);
        toast.show();

        createNotification(message, notifyID, largeIcon);

        return super.onStartCommand(intent, flags, startId);
    }
}
