package com.accountabilibuddies.accountabilibuddies.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

            String action = intent.getAction();
            Log.d(TAG, "got action " + action);
            if (action.equals(intentAction)) {
                String channel = intent.getExtras().getString("com.parse.Channel");
                try {
                    JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
                    Log.d(TAG, "got action " + action + " on channel " + channel + " with:");

                    String message = null;
                    String challengeType = null;
                    String challengeId = null;
                    String challengeName = null;
                    String challengeImageUrl = null;

                    Iterator<String> itr = json.keys();
                    while (itr.hasNext()) {
                        String key = itr.next();
                        String value = json.getString(key);

                        if (key.equals("customMessage")) {
                            message = value;
                        } else if (key.equals("challengeId")) {
                            challengeId = value;
                        } else if (key.equals("challengeType")) {
                            challengeType = value;
                        } else if (key.equals("challengeName")) {
                            challengeName = value;
                        } else if (key.equals("challengeImageUrl")) {
                            challengeImageUrl = value;
                        }
                    }

                    Intent dowloadIntent = new Intent(context, DownloadImageService.class);

                    dowloadIntent.putExtra("customMessage", message);
                    dowloadIntent.putExtra("challengeId", challengeId);
                    dowloadIntent.putExtra("challengeType", challengeType);
                    dowloadIntent.putExtra("challengeName", challengeName);
                    dowloadIntent.putExtra("challengeImageUrl", challengeImageUrl);

                    context.startService(dowloadIntent);

                } catch (JSONException ex) {
                    //TODO: Handle error
                    Log.d(TAG, "JSON failed!");
                }
            }
        }
    }
}