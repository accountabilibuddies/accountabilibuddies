package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.like.LikeButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.heart_button)
    LikeButton heartButton;

    //@BindView(R.id.ibComment)
    //ImageButton postComment;

    View itemView;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.itemView = itemView;
    }

    //public ImageButton getPostComment() {
    //    return postComment;
    //}

    //public void setPostComment(ImageButton postComment) {
    //    this.postComment = postComment;
    //}

    public View getItemView() {

        return itemView;
    }

    public LikeButton getHeartButton() {
        return heartButton;
    }

    public void setHeartButton(LikeButton heartButton) {
        this.heartButton = heartButton;
    }
}
