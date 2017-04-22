package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.yqritc.scalablevideoview.ScalableVideoView;

import butterknife.BindView;

public class PostWithVideoViewHolder extends PostViewHolder {

    @BindView(R.id.video_view)
    ScalableVideoView videoView;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.tvName)
    TextView tvName;

    public PostWithVideoViewHolder(View itemView) {
        super(itemView);
    }

    public ScalableVideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(ScalableVideoView videoView) {
        this.videoView = videoView;
    }

    public ImageView getIvProfileImage() {
        return ivProfileImage;
    }

    public void setIvProfileImage(ImageView ivProfileImage) {
        this.ivProfileImage = ivProfileImage;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }
}
