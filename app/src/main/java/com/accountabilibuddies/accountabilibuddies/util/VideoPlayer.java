package com.accountabilibuddies.accountabilibuddies.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;

import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;

public class VideoPlayer {

    /**
     * Plays video within RecyclerView.
     *
     * <p/>
     *
     *      Example XML in layout:
     *     <com.yqritc.scalablevideoview.ScalableVideoView
     *          android:id="@+id/svvVideo"
     *          android:layout_width="400dp"
     *          android:layout_height="200dp"
     *          android:visibility="gone"
     *          app:scalableType="fitCenter" />
     * <p/>
     *
     * <pre>
     *   public void playVideo() {
     *      VideoPlayer.loadVideo(context, holder.svvVideo, "http://techslides.com/demos/sample-videos/small.mp4");
     *   }
     * </pre>
     *
     * @param context
     *          The active {@link Context} for your application.
     * @param view
     *          The ScalableVideoView view.
     * @param url
     *          The url of the video
     */
    public static void loadVideo(Context context, ScalableVideoView view, String url) {

        try {
            view.setVisibility(View.VISIBLE);
            view.setDataSource(context, Uri.parse(url));
            view.prepareAsync(
                (MediaPlayer mp) -> {
                    view.setLooping(true);
                    view.start();
                }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
