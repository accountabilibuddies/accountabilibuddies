package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cvPost)
    CardView cvPost;

    @BindView(R.id.ibLike)
    ImageButton postLike;

    @BindView(R.id.ibComment)
    ImageButton postComment;

    @BindView(R.id.tvLikes)
    TextView likesCount;

    View itemView;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.itemView = itemView;
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

    public CardView getCardView() {
        return cvPost;
    }

    public View getItemView() {

        return itemView;
    }
}
