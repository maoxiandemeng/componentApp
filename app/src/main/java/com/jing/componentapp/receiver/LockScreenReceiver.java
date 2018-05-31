package com.jing.componentapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jing.componentapp.activity.LockScreenActivity;

/**
 * Created by liujing on 2018/5/31.
 */

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            Intent screenIntent = new Intent(context, LockScreenActivity.class);
            screenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(screenIntent);
        }
    }
}
