package com.accountabilibuddies.accountabilibuddies.util;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;

public class VideoUtils {

    private static final int VIDEO_CAPTURE = 101;
    Uri videoUri;
    AppCompatActivity context;

    public void startRecording(AppCompatActivity context) {

        this.context = context;

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            File mediaFile = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/post_video.mp4"
            );

            videoUri = Uri.fromFile(mediaFile);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

            context.startActivityForResult(intent, VIDEO_CAPTURE);
        } else {
            Toast.makeText(context, "No camera on this device", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VIDEO_CAPTURE) {

            if (resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(context, "Video has been saved to: " + data.getData(), Toast.LENGTH_SHORT).show();
                playRecordedVideo();
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(context, "Video recording canceled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to record video.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void playRecordedVideo() {
        //TODO: get video from Parse and play using VideoPlayer functionality
    }
}
