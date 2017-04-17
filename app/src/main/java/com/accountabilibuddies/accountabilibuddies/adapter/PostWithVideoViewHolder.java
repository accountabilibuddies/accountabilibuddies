package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.yqritc.scalablevideoview.ScalableVideoView;

import butterknife.BindView;

public class PostWithVideoViewHolder extends PostViewHolder {

    @BindView(R.id.video_view)
    ScalableVideoView videoView;

    public PostWithVideoViewHolder(View itemView) {
        super(itemView);
    }

    public ScalableVideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(ScalableVideoView videoView) {
        this.videoView = videoView;
    }
}
