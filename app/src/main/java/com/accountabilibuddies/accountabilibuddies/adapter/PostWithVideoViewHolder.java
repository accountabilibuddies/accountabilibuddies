package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;
import android.widget.VideoView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;

public class PostWithVideoViewHolder extends PostViewHolder {

    @BindView(R.id.video_view)
    VideoView videoView;

    public PostWithVideoViewHolder(View itemView) {
        super(itemView);
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }
}
