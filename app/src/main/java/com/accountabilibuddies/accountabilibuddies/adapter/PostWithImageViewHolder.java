package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostWithImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivPost)
    ImageView imageView;

    @BindView(R.id.ibLike)
    ImageButton postLike;

    @BindView(R.id.ibComment)
    ImageButton postComment;

    public PostWithImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
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
}
