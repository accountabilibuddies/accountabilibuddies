package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostWithVideoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.video_view)
    VideoView videoView;

    @BindView(R.id.ibLike)
    ImageButton postLike;

    @BindView(R.id.ibComment)
    ImageButton postComment;

    @BindView(R.id.tvLikes)
    TextView likesCount;

    public PostWithVideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }

    public ImageButton getPostLike() {
        return postLike;
    }

    public void setPostLike(ImageButton postLike) {
        this.postLike = postLike;
    }

    public ImageButton getPostComment() {
        return postComment;
    }

    public void setPostComment(ImageButton postComment) {
        this.postComment = postComment;
    }

    public TextView getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(TextView likesCount) {
        this.likesCount = likesCount;
    }
}
