// AlarmUtil.java
package org.techtown.Checkfit.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.techtown.Checkfit.activities.MealDataActivity;
import org.techtown.Checkfit.managers.ResetTodayData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmUtil {

    public static void resetAlarm(Context context) {
        AlarmManager resetAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent resetIntent = new Intent(context, ResetTodayData.class);
        PendingIntent resetSender = PendingIntent.getBroadcast(context, 0, resetIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // 자정 시간 설정
        Calendar resetCal = Calendar.getInstance();
        resetCal.setTimeInMillis(System.currentTimeMillis());
        resetCal.set(Calendar.HOUR_OF_DAY, 0);
        resetCal.set(Calendar.MINUTE, 0);
        resetCal.set(Calendar.SECOND, 0);
        resetCal.set(Calendar.MILLISECOND, 0);
        resetCal.add(Calendar.DAY_OF_YEAR, 1);

        // 정확한 시간에 알람 설정
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            resetAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, resetCal.getTimeInMillis(), resetSender);
        } else {
            resetAlarmManager.setExact(AlarmManager.RTC_WAKEUP, resetCal.getTimeInMillis(), resetSender);
        }

        SimpleDateFormat format = new SimpleDateFormat("MM/dd kk:mm:ss");
        String setResetTime = format.format(new Date(resetCal.getTimeInMillis()));
        Log.d("resetAlarm", "ResetHour : " + setResetTime);
    }
}




