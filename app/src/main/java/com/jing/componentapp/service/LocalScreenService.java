package com.jing.componentapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.jing.componentapp.R;
import com.jing.componentapp.activity.MainActivity;
import com.jing.componentapp.receiver.LockScreenReceiver;

/**
 * Created by liujing on 2018/5/31.
 */

public class LocalScreenService extends Service {
    private LockScreenReceiver lockScreenReceiver;
    private NotificationManager notificationManager;
    private Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        lockScreenReceiver = new LockScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockScreenReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runForeground();
        startForeground(1001, notification);
        return START_STICKY;
    }

    private void runForeground() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification = new NotificationCompat.Builder(this)
                .setTicker(getResources().getString(R.string.app_name))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
//        notificationManager.notify(9999, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(lockScreenReceiver);
    }
}
