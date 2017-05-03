package com.accountabilibuddies.accountabilibuddies.util;

import android.app.NotificationManager;
import android.content.Context;

public class NotificationUtils {


    public static void cancelNotification(Context context, int notificationId) {

        if (notificationId != -1) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);
        }
    }

}
