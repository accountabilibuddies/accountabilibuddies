package com.accountabilibuddies.accountabilibuddies.network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeDetailsActivity;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeOneOnOneActivity;
import com.accountabilibuddies.accountabilibuddies.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class NotificationsReceiver extends BroadcastReceiver {

    private static final String TAG = "NotificationsReceiver";
    public static final int NOTIFICATION_ID = 100;
    public static final String intentAction = "com.parse.push.intent.RECEIVE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d(TAG, "Receiver intent null");
        } else {
            processPush(context, intent);
        }
    }

    private void processPush(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "got action " + action);
        if (action.equals(intentAction)) {
            String channel = intent.getExtras().getString("com.parse.Channel");
            try {
                JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
                Log.d(TAG, "got action " + action + " on channel " + channel + " with:");

                String message = null;
                String challengeType = null;
                String challengeId= null;
                String challengeName = null;

                Iterator<String> itr = json.keys();
                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    String value = json.getString(key);

                    if (key.equals("customMessage")) {
                        message = value;
                    } else if(key.equals("challengeId")) {
                        challengeId = value;
                    } else if(key.equals("challengeType")) {
                        challengeType = value;
                    } else if(key.equals("challengeName")) {
                        challengeName = value;
                    }
                }
                createNotification(context, message,challengeId,challengeType,challengeName);

            } catch (JSONException ex) {
                //TODO: Handle error
                Log.d(TAG, "JSON failed!");
            }
        }
    }


    private void createNotification(Context context, String customMessage,
                                    String challengeId, String challengeType,
                                    String challengeName) {

        Intent intent = null;
        if(Integer.valueOf(challengeType) == Constants.TYPE_ONE_ON_ONE) {
            intent = new Intent(context, ChallengeOneOnOneActivity.class);
        } else {
            intent = new Intent(context, ChallengeDetailsActivity.class);
        }
        intent.putExtra("challengeId",challengeId);
        intent.putExtra("name", challengeName);

        int requestID = (int) System.currentTimeMillis();
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;

        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

        NotificationCompat.Builder mBuilder = new NotificationCompat
                .Builder(context).setContentIntent(pIntent)
                //TODO: Update the icon to the launcher style notification icon
                .setSmallIcon(R.drawable.clock).setContentTitle("Accountabilibudies")
                .setContentText(customMessage);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}