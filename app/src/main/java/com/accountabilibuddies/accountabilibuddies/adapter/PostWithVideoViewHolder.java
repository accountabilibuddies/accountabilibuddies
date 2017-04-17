package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

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
