package com.accountabilibuddies.accountabilibuddies.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeDetailsActivity;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeOneOnOneActivity;

import static com.accountabilibuddies.accountabilibuddies.network.NotificationsReceiver.NOTIFICATION_ID;

public class NotificationUtils {


    public static void cancelNotification(Context context, int notificationId) {

        if (notificationId != -1) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);
        }
    }

    public static void createNotification(Context context, String customMessage,
                                          String challengeId, String challengeType,
                                          String challengeName, Bitmap bitmap) {

        Intent intent;
        if(Integer.valueOf(challengeType) == Constants.TYPE_ONE_ON_ONE) {
            intent = new Intent(context, ChallengeOneOnOneActivity.class);
        } else {
            intent = new Intent(context, ChallengeDetailsActivity.class);
        }
        intent.putExtra("challengeId",challengeId);
        intent.putExtra("name", challengeName);
        intent.putExtra("notificationId", NOTIFICATION_ID);

        int requestID = (int) System.currentTimeMillis();
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;

        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

        NotificationCompat.Builder mBuilder = new NotificationCompat
                .Builder(context)
                .setSmallIcon(R.drawable.notification)
                .setColor(ContextCompat.getColor(context,R.color.create_blue))
                .setContentTitle(customMessage)
                .setContentText("Tap for more options.")
                .setAutoCancel(true)
                .addAction(R.drawable.done, "Accept", pIntent)
                .addAction(R.drawable.close,"Decline", pIntent)
                .setPriority(Notification.PRIORITY_LOW)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
