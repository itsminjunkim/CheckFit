package org.techtown.Checkfit.managers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.techtown.Checkfit.R;

public class ResetTodayData extends BroadcastReceiver {

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    MealManager mealManager;
    UserManager userManager;
    NotificationManager notiManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        mealManager = new MealManager(context);
        userManager = new UserManager(context);

        mealManager.clearData();
        userManager.clearStep();

        sendNotification(context);
    }

    private void sendNotification(Context context){
        notiManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notiManager.createNotificationChannel(new NotificationChannel(
                    CHANNEL_ID , CHANNEL_NAME , NotificationManager.IMPORTANCE_DEFAULT
                ));
            builder = new NotificationCompat.Builder(context , CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setContentTitle("CheckFit");
        builder.setContentText("하루가 지나 식단 초기화 됬습니다.");
        builder.setSmallIcon(R.drawable.checkfit);
        Notification noti = builder.build();

        notiManager.notify(1 , noti);
    }
}

