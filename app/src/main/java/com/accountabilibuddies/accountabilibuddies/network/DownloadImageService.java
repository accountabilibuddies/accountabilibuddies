package com.accountabilibuddies.accountabilibuddies.network;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import com.accountabilibuddies.accountabilibuddies.util.NotificationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageService extends IntentService {

    public DownloadImageService() {
        super("Image Download Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String message = intent.getStringExtra("customMessage");
        String challengeType = intent.getStringExtra("challengeType");
        String challengeId = intent.getStringExtra("challengeId");
        String challengeName = intent.getStringExtra("challengeName");
        String challengeImageUrl = intent.getStringExtra("challengeImageUrl");

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(challengeImageUrl).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationUtils.createNotification(this, message, challengeId, challengeType,
                challengeName, bitmap);

    }
}
